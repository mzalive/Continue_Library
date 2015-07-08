package org.mzalive.continuelibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.mzalive.continuelibrary.Base.Book;

import java.util.ArrayList;

/**
 * Created by mzalive on 7/3/15.
 */
public class SearchResultListRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultListRecyclerViewAdapter.SearchResultItemViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<Book> mBookList;
    private int[] mOffset;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class SearchResultItemViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView mTextViewTitle;
        public TextView mTextViewSubtext;
        public ImageView mImageView;
        public SearchResultItemViewHolder(final View view) {
            super(view);

//            ViewGroup.LayoutParams lp = view.getLayoutParams();
//            lp.height = dm.widthPixels / 2;
//            view.setLayoutParams(lp);

            cardView = (CardView) view.findViewById(R.id.card_in_search);
            mTextViewTitle = (TextView) view.findViewById(R.id.title_in_list);
            mTextViewSubtext = (TextView) view.findViewById(R.id.subtext_in_list);
            mImageView = (ImageView) view.findViewById(R.id.card_book_image_in_search_list);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition() - 1;
                    if (position > mOffset[2])
                        position -= 2;
                    else if (position > mOffset[1])
                        position --;
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getLayoutPosition());
                    Intent intent = new Intent(view.getContext(),BookDedatilActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("content",mBookList.get(position));
                    intent.putExtras(bundle);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }



    public SearchResultListRecyclerViewAdapter(Context context, ArrayList<Book> bookList, int[] offset) {
        mBookList = bookList;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        mOffset = offset;
    }

    @Override
    public SearchResultItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItemViewHolder(mLayoutInflater.inflate(R.layout.book_card_in_search_result_list, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchResultItemViewHolder holder, int position) {
        Book item = mBookList.get(position);

        //title
        String title = item.getTitle();
        if(!item.getSubTitle().equals("")){
            title = title + ": " + item.getSubTitle();
        }
        holder.mTextViewTitle.setText(title);

        //设置出版信息（出版社，作者，出版日期放到了一起）
        String publisher = item.getPublisher();
        String author = item.getAuthor().get(0);
        for(int i = 1; i < item.getAuthor().size(); i++){
            author = author + ", " + item.getAuthor().get(i);
        }
        String pubDate = item.getPublishDate();
        String pubInfo = author + " / " + publisher + " / " + pubDate;
        holder.mTextViewSubtext.setText(pubInfo);

        Picasso.with(mContext)
                .load(mBookList.get(position).getImage())
                .into(holder.mImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        //Reset the size of ImageView
                        Drawable drawable = holder.mImageView.getDrawable();
                        int targetWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, mContext.getResources().getDisplayMetrics());
                        int targetHeight = (int) ((float) targetWidth / drawable.getMinimumWidth() * drawable.getMinimumHeight());
                        // check if height too large there
                        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.mImageView.getLayoutParams();
                        lp.height = targetHeight;
                        lp.width = targetWidth;
                        holder.mImageView.setLayoutParams(lp);

                        //Get palette to produce card's background color
                        Bitmap source = ((BitmapDrawable) drawable).getBitmap();
                        Palette.from(source)
                                .maximumColorCount(32)
                                .generate(new Palette.PaletteAsyncListener() {
                                    public void onGenerated(Palette p) {
                                        Palette.Swatch swatch = p.getDarkVibrantSwatch();
                                        if (swatch != null)
                                            holder.cardView.setCardBackgroundColor(swatch.getRgb());
                                    }
                                });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.size();
    }


}

