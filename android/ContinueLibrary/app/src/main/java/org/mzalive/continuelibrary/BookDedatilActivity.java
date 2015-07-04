package org.mzalive.continuelibrary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
    private TextView  tvBookContent;
    private Button    btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        toolbar             = (ImageView)findViewById(R.id.toolBar);
        ivBookBlur          = (ImageView)findViewById(R.id.image_book_blur);
        ivBookImage         = (ImageView)findViewById(R.id.image_book);
        tvBookTitle         = (TextView)findViewById(R.id.text_title);
        tvBookPubInfo       = (TextView)findViewById(R.id.text_publish_info);
        tvBookContentIndex  = (TextView)findViewById(R.id.text_content_index);
        tvBookContent       = (TextView)findViewById(R.id.text_content);
        btnAdd              = (Button)findViewById(R.id.button_add);

        //动态设置背景的大小
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ViewGroup.LayoutParams p = ivBookBlur.getLayoutParams();
        p.width = dm.widthPixels;
        p.height = dm.widthPixels;
        ivBookBlur.setLayoutParams(p);

        p = ivBookImage.getLayoutParams();
        p.width = (int)(dm.widthPixels*0.45);

//        ivBookBlur
        ivBookImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this)
                .load("http://img3.douban.com/lpic/s28120622.jpg")
                .into(ivBookImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Drawable drawable = ivBookImage.getDrawable();
                        int targetHeight = (int)((float) ivBookImage.getWidth()/drawable.getMinimumWidth() * drawable.getMinimumHeight());
                        // check if height too large there
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivBookImage.getLayoutParams();
                        lp.height = targetHeight;
                        lp.width = (int) (dm.widthPixels*0.45);
                        ivBookImage.setLayoutParams(lp);
                    }
                });
        Drawable blurDraw = new BitmapDrawable(getResources(), blur(ivBookImage.getDrawingCache(), p.width));
        ivBookBlur.setImageDrawable(blurDraw);

    }

    private Bitmap blur(Bitmap bitmap, int size) {
        long startMs = System.currentTimeMillis();
        float radius = 20;

        Bitmap overlay = Bitmap.createBitmap( size, size, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(overlay);

        canvas.translate(-view.getLeft(), -view.getTop());
        canvas.drawBitmap(bitmap, 0, 0, null);

        RenderScript rs = RenderScript.create(this);

        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());

        blur.setInput(overlayAlloc);

        blur.setRadius(radius);

        blur.forEach(overlayAlloc);

        overlayAlloc.copyTo(overlay);

        rs.destroy();

        return overlay;
    }

}
