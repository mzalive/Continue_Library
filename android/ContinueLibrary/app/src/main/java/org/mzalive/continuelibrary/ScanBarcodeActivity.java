package org.mzalive.continuelibrary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.content.DialogInterface;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.communication.GlobalSettings;
import org.mzalive.continuelibrary.communication.Search;

public class ScanBarcodeActivity extends Activity implements SensorEventListener {

    private SvCamera svCamera;
    private View centerView;
    private SurfaceView sfView;
    private ImageView imgView;
    private TextView txtView;
    private Timer timer;
    private TimerTask autoFocusTask;
    private TimerTask timeCountTask;

    private AlertDialog resultDialog;
    private AlertDialog.Builder builder;
    private TextView resultBookTitle;
    private TextView resultBookAuthor;
    private TextView resultBookPublisher;
    private ImageView resultBookCover;

    private String resultBarCode;

    private BarCodeReader bcReader;

    private SensorManager mSensorManager;
    private Sensor mAccel;

    private boolean mInitialized = false;
    private float mLastX = 0;
    private float mLastY = 0;
    private float mLastZ = 0;
    private boolean mAutoFocus = true;

    private final String scanUnsuccessful = "扫描中…";

    private int noMovementTimeCounter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        bcReader = new BarCodeReader();

        centerView = (View) this.findViewById(R.id.sbCenterView);
        sfView = (SurfaceView) this.findViewById(R.id.svCamera);
        txtView = (TextView) this.findViewById(R.id.txtScanResult);
        imgView = (ImageView) this.findViewById(R.id.sbReviewImage);

        svCamera = new SvCamera(sfView.getHolder(),myAutoFocusCallBack);

        mSensorManager = (SensorManager) getSystemService(Context.
                SENSOR_SERVICE);
        mAccel = mSensorManager.getDefaultSensor(Sensor.
                TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this,mAccel,SensorManager.SENSOR_DELAY_UI);

        timer = new Timer();
        autoFocusTask = new AutoFocusTimerTask();
        timeCountTask = new TimeCounterTask();
        timer.schedule(timeCountTask, 0, 1000);
    }

    class AutoFocusTimerTask extends TimerTask {
        @Override
        public void run() {
            try {
                if (!mAutoFocus) {
                    svCamera.AutoFocusAndPreviewCallback();
                }
            }catch(Exception e){
                Log.e("autofocus","error");
                e.printStackTrace();
            }
        }
    }

    class TimeCounterTask extends  TimerTask{
        @Override
        public void run() {
            noMovementTimeCounter+=1;
            if(noMovementTimeCounter == 3){
                noMovementTimeCounter = 0;
                if(mAutoFocus) {
                    mAutoFocus = false;
                    autoFocusTask.run();
                }
            }
        }
    }

    public void createAndShowDialog(){
        new ShowDialog().execute();
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback(){
        @Override
        public void onPreviewFrame(byte[] data, Camera camera){
            Size previewSize = camera.getParameters().getPreviewSize();
            data = rotateYUV420Degree90(data, previewSize.width, previewSize.height);
            int rotated_width = previewSize.height;
            int rotated_height = previewSize.width;
            YuvImage yuvimage=new YuvImage(data, ImageFormat.NV21, rotated_width,rotated_height, null);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0, rotated_width,rotated_height), 100, baos);
            byte[] jdata = baos.toByteArray();

            Bitmap bmp = BitmapFactory.decodeByteArray(jdata, 0, jdata.length);

            if(bmp != null)
                imgView.setImageBitmap(bmp);
            else
                Log.e("bitmap","not able to show");

            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
                    data, rotated_width,rotated_height,0,0,rotated_width,rotated_height,false);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            String scanResult = bcReader.decode(bitmap);
            resultBarCode = scanResult=="-1"?scanUnsuccessful:scanResult;
            if(!resultBarCode.equals(scanUnsuccessful))
                createAndShowDialog();
           else
                mAutoFocus = true;
        }
    };

    private Camera.AutoFocusCallback myAutoFocusCallBack = new Camera.AutoFocusCallback() {

        @Override
        public void onAutoFocus(boolean success, Camera mCamera) {
            mInitialized = false;
            if (success) {  //对焦成功，回调Camera.PreviewCallback
                mCamera.setOneShotPreviewCallback(previewCallback);
                Log.e("autofocus", "success");
            } else {
                mAutoFocus = true;
                Log.e("autofocus", "failed");
            }
        }
    };

    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized){
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            mInitialized = true;
            return;
        }
        float deltaX  = Math.abs(mLastX - x);
        float deltaY = Math.abs(mLastY - y);
        float deltaZ = Math.abs(mLastZ - z);

        if ((deltaX > 0.5||deltaY > 0.5||deltaZ > 0.5) && mAutoFocus){ //AUTOFOCUS (while it is not autofocusing)
            noMovementTimeCounter = 0;
            if(mAutoFocus) {
                mAutoFocus = false;
                if (!mAutoFocus)
                    autoFocusTask.run();
            }
        }

        mLastX = x;
        mLastY = y;
        mLastZ = z;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected  void onResume(){
        super.onResume();
        mSensorManager.registerListener(this, mAccel, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
    }

    //旋转照片
    private byte[] rotateYUV420Degree90(byte[] data, int imageWidth, int imageHeight)
    {
        byte [] yuv = new byte[imageWidth*imageHeight*3/2];
        int i = 0;
        for(int x = 0;x < imageWidth;x++)
        {
            for(int y = imageHeight-1;y >= 0;y--)
            {
                yuv[i] = data[y*imageWidth+x];
                i++;
            }
        }
        i = imageWidth*imageHeight*3/2-1;
        for(int x = imageWidth-1;x > 0;x=x-2)
        {
            for(int y = 0;y < imageHeight/2;y++)
            {
                yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+x];
                i--;
                yuv[i] = data[(imageWidth*imageHeight)+(y*imageWidth)+(x-1)];
                i--;
            }
        }
        return yuv;
    }

    class ShowDialog extends AsyncTask<Integer,Integer,Boolean>{
        private String title;
        private String author = "";
        private String publisher;
        private String image;
        private Context mContext;
        View layout;
        @Override
        protected  void onPreExecute(){
            //建对话框
            mContext = ScanBarcodeActivity.this;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.scan_result_dialog,(ViewGroup)findViewById(R.id.dialogUI));
            //
            resultBookTitle = (TextView) layout.findViewById(R.id.BookTitle);
            resultBookAuthor = (TextView) layout.findViewById(R.id.BookAuthor);
            resultBookPublisher = (TextView) layout.findViewById(R.id.BookPublisher);
            resultBookCover = (ImageView) layout.findViewById(R.id.BookImage);
            //
        }

        @Override
        protected  Boolean doInBackground(Integer... params){
            try {
                String jsonStr = Search.search("-1",resultBarCode,0,1,0,0);
                JSONTokener jsonTokener = new JSONTokener(jsonStr);
                JSONObject object = (JSONObject) jsonTokener.nextValue();
                int errorCode = Integer.parseInt(object.getString("error_code"));
                if(errorCode == GlobalSettings.RESULT_OK)
                {
                    JSONArray books = object.getJSONArray("books");
                    JSONObject book = (JSONObject) books.get(0);
                    title = book.getString("title");
                    JSONArray authors = book.getJSONArray("author");
                    for(int j = 0; j < authors.length(); j++){
                        author += authors.get(j).toString();
                        author += "\n";
                    }
                    author = author.substring(0,author.length()-1);
                    publisher = book.getString("publisher");
                    image = book.getString("image");

                    //Picasso.with(resultDialog.getContext()).load(image);
                }
                return true;
            }catch (JSONException e){
                mAutoFocus = true;
                e.printStackTrace();

                return false;
            }catch(Exception e){
                mAutoFocus = true;
                e.printStackTrace();

                return false;
            }
        }
        @Override
        protected void onPostExecute(Boolean result) {

            resultBookTitle.setText(title);
            resultBookAuthor.setText(author);
            resultBookPublisher.setText(publisher);
            Picasso.with(ScanBarcodeActivity.this).load(image).into(resultBookCover);

            builder = new AlertDialog.Builder(mContext);
            builder.setView(layout);
            builder.setPositiveButton("借",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "你选择了借", Toast.LENGTH_SHORT);
                    toast.show();
                    mAutoFocus = true;
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "你选择了取消", Toast.LENGTH_SHORT);
                    toast.show();
                    mAutoFocus = true;
                }
            });
            builder.setNeutralButton("查看详情", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    Toast toast = Toast.makeText(getApplicationContext(), "你选择了查看详情", Toast.LENGTH_SHORT);
                    toast.show();
                    mAutoFocus = true;
                }
            });

            resultDialog = builder.create();
            resultDialog.show();
        }
    };
}

class SvCamera implements SurfaceHolder.Callback {
    private SurfaceHolder holder;
    private Camera mCamera;
    private Camera.AutoFocusCallback myAutoFocusCallback;

    public SvCamera(SurfaceHolder holder, Camera.AutoFocusCallback myAutoFocusCallback) {
        this.holder = holder;
        this.holder.addCallback(this);
        this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        this.myAutoFocusCallback = myAutoFocusCallback;
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        mCamera = Camera.open();//启动服务
        Camera.Parameters parameters = mCamera.getParameters();
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewDisplay(holder);//设置预览
            Log.e("Camera", "surfaceCreated");
        } catch (IOException e) {
            mCamera.release();//释放
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(width, height);//设置尺寸
        mCamera.setDisplayOrientation(90);
        mCamera.setParameters(parameters);
        mCamera.startPreview();//开始预览
        mCamera.autoFocus(myAutoFocusCallback);
        Log.e("Camera", "surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        Log.e("Camera", "surfaceDestroyed");
        mCamera.stopPreview();//停止预览
        mCamera.setPreviewCallback(null);
        mCamera.release();
        mCamera = null;
    }

    public void AutoFocusAndPreviewCallback() {
        if (mCamera != null)
            mCamera.autoFocus(myAutoFocusCallback);
    }
}

class BarCodeReader{
    private Result result;
    private MultiFormatReader reader = new MultiFormatReader();
    private boolean init = false;
    public BarCodeReader(){
        Hashtable<DecodeHintType,Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

        Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
        decodeFormats.add(BarcodeFormat.CODABAR);

        hints.put(DecodeHintType.POSSIBLE_FORMATS,decodeFormats);
        reader.setHints(hints);
    }

    public String decode(BinaryBitmap bitmap){
        try{
            result = reader.decode(bitmap);
            return result.getText();
        }catch (Exception e)
        {
            return "-1";
        }
    }
}