package org.mzalive.continuelibrary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
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

import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.communication.BookManage;
import org.mzalive.continuelibrary.communication.WishlistManage;
import org.mzalive.continuelibrary.communication.GlobalSettings;

/**
 * Created by Trigger on 2015/7/3.
 */
public class BookDedatilActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ImageView ivBookBlur;
    private ImageView ivBookImage;
    private TextView tvBookTitle;
    private TextView tvBookStatus;
    private TextView tvBookSubStatus;
    private ExpandableTextView expTv1;
    private TextView  tvBookInfo;
    private Button    btnWantToRead;
    private Button   btnRent;
    private ImageView ivBtnRentWrapper;

    private boolean isLogin;
    private String uid;

    private boolean isCalledByScan = false;

    private Book book;
    private boolean isUserWanted = false;
    private int amount_available = -1;
    private int book_location = 3;
    private int heat = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);

        /**
         * Extract Bundle
         */
        Bundle bundle = getIntent().getExtras();
        book = (Book) bundle.getSerializable("content");
        isCalledByScan = bundle.getBoolean("called_by_scan", false);

        /**
         * Exam user login status
         */
        refreshLoginStatus();

        /**
         * Set Toolbar
         */
        toolbar = (Toolbar) findViewById(R.id.toolBarDetail);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.getBackground().setAlpha(80);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);


        /**
         * Bind views
         */
        ivBookBlur = (ImageView) findViewById(R.id.image_book_blur);
        ivBookImage = (ImageView) findViewById(R.id.image_book);

        tvBookTitle = (TextView) findViewById(R.id.text_title);
        tvBookInfo = (TextView) findViewById(R.id.text_publish_info);
        expTv1 = (ExpandableTextView)findViewById(R.id.expand_text_view);

        tvBookStatus = (TextView) findViewById(R.id.book_status);
        tvBookSubStatus = (TextView) findViewById(R.id.book_sub_status);

        btnWantToRead = (Button) findViewById(R.id.button_want_to_read);
        btnRent = (Button)findViewById(R.id.button_borrow);
        ivBtnRentWrapper = (ImageView) findViewById(R.id.button_borrow_wrapper);

        btnWantToRead.setVisibility(View.GONE);
        btnWantToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLoginStatus();
                if (!isLogin) {
                    callForLogin();
                    return;
                }
                new AddHeat().execute(uid, book.getIsbn(), (book_location == 3) ? "true" : "false");
            }
        });
        btnRent.setVisibility(View.GONE);
        btnRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BorrowBookAsyncTask().execute(book.getIsbn(), uid);

            }
        });
        ivBtnRentWrapper.setVisibility(View.GONE);


        /**
         * Additional Info retrieve
         */
        //Location
        book_location = book.getLocation();


        switch (book_location){
            case Book.LOCATION_CONTINUE:
                //Main Status
                tvBookStatus.setText(getResources().getString(R.string.detail_status_in_stock_true));

                //Sub Status & Rent Button
                //Book is in Continue, retrieve amount_available
                if (isCalledByScan) {
                    //Called by scan, amount_available should exist.
                    // -1 stands for scan-activity not retrieve that data,
                    //should retrieve manually.
                    amount_available = bundle.getInt("amount_available", -1);
                    if (amount_available > 0) {
                        ivBtnRentWrapper.setVisibility(View.VISIBLE);
                        btnRent.setVisibility(View.VISIBLE);
                        updateSubStatusInContinue();
                    }
                    else if (amount_available == 0) {
                        ivBtnRentWrapper.setVisibility(View.VISIBLE);
                        btnRent.setVisibility(View.VISIBLE);
                        btnRent.setEnabled(false);
                        updateSubStatusInContinue();
                    }
                    else
                        new GetBookAmountAsyncTask().execute(book.getIsbn());
                }
                else new GetBookAmountAsyncTask().execute(book.getIsbn());

                break;

            case Book.LOCATION_WISHLIST:
                Log.d("Book Status","Location: Wishlist");
                updateSubStatusInWishlist();

                break;

            case  Book.LOCATION_DOUBAN:
                String mainStatus;
                String subString = "";
                btnWantToRead.setText(getString(R.string.detail_status_heat_button_first_time));
                btnWantToRead.setVisibility(View.VISIBLE);

                mainStatus = getResources().getString(R.string.detail_status_in_stock_false) + " "
                        + getResources().getString(R.string.detail_status_suffix_user_want_false);

                tvBookStatus.setText(mainStatus);
                tvBookSubStatus.setText(subString);

                break;

        }



        /**
         * Inflate data into views
         */
        //Book image & background blur
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

                        int blurMarginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (24+56+16), getResources().getDisplayMetrics());
                        int targetHeightBlur = targetHeightBook + blurMarginTop;

                        rlp.width =targetWidthBook;
                        rlp.height = targetHeightBook;

                        vlp.width = targetWidthBlur;
                        vlp.height = targetHeightBlur;

                        ivBookImage.setLayoutParams(rlp);
                        ivBookBlur.setLayoutParams(vlp);

                        Bitmap ivBookBitmap = ((BitmapDrawable) (ivBookImage.getDrawable())).getBitmap();
                        StackBlurManager _stackBlurManager = new StackBlurManager(ivBookBitmap);
                        _stackBlurManager.process(35);
                        Drawable blurDrawable = new BitmapDrawable(_stackBlurManager.returnBlurredImage());
                        ivBookBlur.setImageDrawable(blurDrawable);
                    }
                });

        //title
        String title = book.getTitle();
        if(book.getSubTitle().equals("")){
            title = title + ":" + book.getSubTitle();
        }
        tvBookTitle.setText(title);

        //设置出版信息（出版社，作者，出版日期放到了一起）
        String publisher = book.getPublisher();
        String author = book.getAuthor().get(0);
        for(int i = 1; i < book.getAuthor().size(); i++){
            author = author + ", " + book.getAuthor().get(i);
        }
        String pubDate = book.getPublishDate();

        String pubInfo = author + " / " + publisher + " / " + pubDate;
        tvBookInfo.setText(pubInfo);

        //Book Status

        //设置简介
        String summary = book.getSummary();
        if(summary.equals("")){
            summary = getString(R.string.detail_book_content);
        }
        expTv1.setText(summary);


    }


    @Override
    protected void onStop() {
        super.onStop();
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class AddHeat extends AsyncTask<String, Void, Integer>{
        @Override
        protected  void onPreExecute() {
            btnWantToRead.setEnabled(false);
        }

        @Override
        protected Integer doInBackground(String... params) {
            String userId = params[0];
            String isbn   = params[1];
            Boolean isFromDouban = Boolean.valueOf(params[2]);
            Integer result;
            result = WishlistManage.addHeat(userId, isbn, isFromDouban);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result){
            switch (result){
                case 1000:
                    Toast.makeText(BookDedatilActivity.this, "添加成功！", Toast.LENGTH_SHORT).show();
                    btnWantToRead.setVisibility(View.GONE);
                    isUserWanted = true;
                    heat++;
                    updateSubStatusInWishlist();
                    break;
                case -1:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    btnWantToRead.setEnabled(true);
                    break;
                case 9999:
                    Toast.makeText(BookDedatilActivity.this, "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
                    btnWantToRead.setEnabled(true);
                    break;
                case 5001:
                    Toast.makeText(BookDedatilActivity.this, "您已经添加过了!", Toast.LENGTH_SHORT).show();
                    btnWantToRead.setVisibility(View.GONE);
                    isUserWanted = true;
                    heat++;
                    updateSubStatusInWishlist();
                    break;
                case 4001:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    btnWantToRead.setEnabled(true);
                    break;
            }
        }

    }

    class BorrowBookAsyncTask extends AsyncTask<String, Integer, Integer>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            btnRent.setEnabled(false);

        }
        @Override
        protected Integer doInBackground(String... params)
        {
            int result;
            String isbn = params[0];
            String uid = params[1];
            result= BookManage.borrowBook(isbn, uid);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            switch (result){
                case GlobalSettings.RESULT_OK:
                    Toast.makeText(BookDedatilActivity.this, "借阅成功！", Toast.LENGTH_SHORT).show();
                    btnRent.setVisibility(View.GONE);
                    amount_available--;
                    updateSubStatusInContinue();
                    break;
                case GlobalSettings.JSON_EXCEPTION_ERROR:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    btnRent.setEnabled(true);
                    break;
                case GlobalSettings.NETWORK_ERROR:
                    Toast.makeText(BookDedatilActivity.this, "网络连接失败，请检查网络！", Toast.LENGTH_SHORT).show();
                    btnRent.setEnabled(true);
                    break;
                case GlobalSettings.BOOK_ALL_BORROWED:
                    Toast.makeText(BookDedatilActivity.this, "您已经借过了!", Toast.LENGTH_SHORT).show();
                    btnRent.setVisibility(View.GONE);
                    amount_available--;
                    updateSubStatusInContinue();
                    break;
                case GlobalSettings.DATABASE_OPERATION_ERROR:
                    Toast.makeText(BookDedatilActivity.this, "服务器遇到错误，请重试!", Toast.LENGTH_SHORT).show();
                    btnRent.setEnabled(true);
                    break;
            }

        }
    }

    class GetBookAmountAsyncTask extends AsyncTask<String, Integer, Integer>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
//            BookDedatilActivity.this.tvBookSubStatus

        }
        @Override
        protected Integer doInBackground(String... params)
        {
            int result;
            result= BookManage.getBookCount(params[0]);
            return result;
        }

        @Override
        protected void onPostExecute(Integer result)
        {
            super.onPostExecute(result);
            if (result != -1) {
                BookDedatilActivity.this.amount_available = result;
                BookDedatilActivity.this.updateSubStatusInContinue();
            }

        }
    }

    private void updateSubStatusInContinue() {
        tvBookSubStatus.setText(getResources().getString(R.string.detail_sub_status_stock, book.getAmountTotal(), amount_available));
    }

    private void updateSubStatusInWishlist() {
        String mainStatus;
        String subString = "";
        isUserWanted = book.isWanted();

        mainStatus = getResources().getString(R.string.detail_status_in_stock_false) + " ";
        heat = book.getHeat();

        if (!isUserWanted) {
            btnWantToRead.setText(getString(R.string.detail_status_heat_button));
            btnWantToRead.setVisibility(View.VISIBLE);
            mainStatus += getResources().getString(R.string.detail_status_suffix_user_want_false);
            if (heat > 1)
                subString = getResources().getString(R.string.detail_sub_status_heat_wo_me, heat, "s", "");
            else
                subString = getResources().getString(R.string.detail_sub_status_heat_wo_me, heat, "", "s");
        }
        else {
            mainStatus += getResources().getString(R.string.detail_status_suffix_user_want_true);
            if (heat > 2)
                subString = getResources().getString(R.string.detail_sub_status_heat_w_me, heat-1, "s");
            else if (heat > 1)
                subString = getResources().getString(R.string.detail_sub_status_heat_w_me, heat-1, "");
            else
                subString = getResources().getString(R.string.detail_sub_status_heat_only_me);
        }
        tvBookStatus.setText(mainStatus);
        tvBookSubStatus.setText(subString);
    }

    private void callForLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void refreshLoginStatus() {
        SharedPreferences sp = getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin", false);
        uid = sp.getString("userId", "-1");
    }
}
