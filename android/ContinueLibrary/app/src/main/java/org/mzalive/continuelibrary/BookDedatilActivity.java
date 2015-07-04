package org.mzalive.continuelibrary;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private float     screenDp;

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

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenDp = dm.widthPixels/dm.density;
    }

    private class LoadImage extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(Integer...params){
            return "";
        }

        @Override
        protected void onPostExecute(String result){

        }
    }
}
