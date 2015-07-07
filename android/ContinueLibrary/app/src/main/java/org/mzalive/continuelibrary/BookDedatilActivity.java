package org.mzalive.continuelibrary;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enrique.stackblur.StackBlurManager;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.mzalive.continuelibrary.Base.Book;

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
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        ViewGroup.LayoutParams p = ivBookBlur.getLayoutParams();
        p.width = dm.widthPixels;
        p.height = dm.widthPixels;
        ivBookBlur.setLayoutParams(p);

        p = ivBookImage.getLayoutParams();
        p.width = (int) (dm.widthPixels * 0.45);

//      ivBookBlur
        ivBookImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Picasso.with(this)
                .load(book.getImage())
                .into(ivBookImage, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        Drawable drawable = ivBookImage.getDrawable();
                        int targetHeight = (int) ((float) ivBookImage.getWidth() / drawable.getMinimumWidth() * drawable.getMinimumHeight());
                        // check if height too large there
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivBookImage.getLayoutParams();
                        lp.height = targetHeight;
                        lp.width = (int) (dm.widthPixels * 0.45);
                        ivBookImage.setLayoutParams(lp);

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
        };
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

        //设置简介
        tvBookContent.setText(book.getSummary());


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playDaye = !playDaye;
                if(playDaye)
                    btnDaye.setVisibility(View.GONE);
                else
                    btnDaye.setVisibility(View.VISIBLE);
            }
        });
    }
}
