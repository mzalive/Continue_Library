package org.mzalive.continuelibrary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enrique.stackblur.StackBlurManager;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.communication.UserInfo;
import org.mzalive.continuelibrary.communication.WishlistManage;

/**
 * Created by Trigger on 2015/7/3.
 */
public class BookDedatilActivity extends Activity{
    private ImageView toolbar;
    private ImageView ivBookBlur;
    private ImageView ivBookImage;
    private TextView  tvBookTitle;
    private TextView  tvBookPubInfo;
    private TextView  tvBookContentIndex;
    private Button    btnAdd;
    private ExpandableTextView expTv1;
    private Button   btnDaye;

    private  boolean playDaye;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        Bundle bundle = getIntent().getExtras();
        book = (Book) bundle.getSerializable("content");
        Log.d("BookDetail: onCreate","Get Book From Bundle: " + book.toString());

        toolbar = (ImageView) findViewById(R.id.toolBar);
        ivBookBlur = (ImageView) findViewById(R.id.image_book_blur);
        ivBookImage = (ImageView) findViewById(R.id.image_book);
        tvBookTitle = (TextView) findViewById(R.id.text_title);
        tvBookPubInfo = (TextView) findViewById(R.id.text_publish_info);
        tvBookContentIndex = (TextView) findViewById(R.id.text_content_index);
        btnAdd = (Button) findViewById(R.id.button_add);
        expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);
        btnDaye = (Button)findViewById(R.id.button_daye);

        //动态设置背景的大小
//      ivBookBlur
        ivBookImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this)
                .load(book.getImage())
                .into(ivBookImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Drawable drawable = ivBookImage.getDrawable();

                        // check if height too large there
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) ivBookImage.getLayoutParams();
                        ViewGroup.LayoutParams vlp = ivBookBlur.getLayoutParams();
                        int targetWidthBook = (int) (dm.widthPixels * 0.45);
                        int targetWidthBlur = dm.widthPixels;
                        int targetHeightBook = (int) ((float) targetWidthBook / drawable.getMinimumWidth() * drawable.getMinimumHeight());

                        int blurMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (24+56+8), getResources().getDisplayMetrics());
                        int targetHeightBlur = targetHeightBook + blurMarginTop;

                        rlp.width =targetWidthBook;
                        rlp.height = targetHeightBook;

                        vlp.width = targetWidthBlur;
                        vlp.height = targetHeightBlur;

                        ivBookImage.setLayoutParams(rlp);
                        ivBookBlur.setLayoutParams(vlp);

                        Bitmap ivBookBitmap = ((BitmapDrawable) (ivBookImage.getDrawable())).getBitmap();
                        StackBlurManager _stackBlurManager = new StackBlurManager(ivBookBitmap);
                        _stackBlurManager.process(200);
                        Drawable blurDrawable = new BitmapDrawable(_stackBlurManager.returnBlurredImage());
                        ivBookBlur.setImageDrawable(blurDrawable);
                    }
                });
        //设置书籍title
        String title = book.getTitle();
        if(book.getSubTitle().equals("")){
            title = title + ":" + book.getSubTitle();
        }
        tvBookTitle.setText(title);

        //设置出版信息（出版社，作者，出版日期放到了一起）
        String publisher = book.getPublisher();
        String author = book.getAuthor().get(0);
        for(int i = 1; i < book.getAuthor().size(); i++){
            author = author + "/" + book.getAuthor().get(i);
        }
        String pubDate = book.getPublishDate();
        String pubInfo = publisher + "/" + author + "/" + pubDate;
        tvBookPubInfo.setText(pubInfo);

        //设置按钮文字

        //设置简介
        String summary = book.getSummary();
        if(summary.equals("")){
            summary = getString(R.string.detail_book_content);
        }
        expTv1.setText(summary);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddHeat().execute("1", "9787508652849", "false");
                playDaye = !playDaye;
                if(playDaye)
                    btnDaye.setVisibility(View.GONE);
                else
                    btnDaye.setVisibility(View.VISIBLE);
            }
        });
    }

    class AddHeat extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            String userId = params[0];
            String isbn   = params[1];
            Boolean isFromDouban = Boolean.valueOf(params[2]);
            String result = WishlistManage.addHeat(userId, isbn, isFromDouban);
            return result;
        }

        @Override
        protected void onPostExecute(String result){
            int errorCode = -1;
            try{
                JSONTokener jsonTokener = new JSONTokener(result);
                JSONObject object = (JSONObject)jsonTokener.nextValue();
                errorCode = object.getInt("error_code");
            }catch (JSONException e){
                e.printStackTrace();
            }
            switch (errorCode){
                case -1:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    break;
                case 1000:
                    Toast.makeText(BookDedatilActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 9999:
                    Toast.makeText(BookDedatilActivity.this, "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
                    break;
                case 5001:
                    Toast.makeText(BookDedatilActivity.this, "您已经添加过了!", Toast.LENGTH_SHORT).show();
                    break;
                case 4001:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }
}
