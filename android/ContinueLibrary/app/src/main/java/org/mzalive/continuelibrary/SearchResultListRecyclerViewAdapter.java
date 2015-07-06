package org.mzalive.continuelibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by mzalive on 7/3/15.
 */
public class SearchResultListRecyclerViewAdapter extends RecyclerView.Adapter<SearchResultListRecyclerViewAdapter.SearchResultItemViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] mTitles;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class SearchResultItemViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView mTextView;
        public ImageView mImageView;
        public SearchResultItemViewHolder(View view) {
            super(view);

//            ViewGroup.LayoutParams lp = view.getLayoutParams();
//            lp.height = dm.widthPixels / 2;
//            view.setLayoutParams(lp);

            cardView = (CardView) view.findViewById(R.id.card_in_search);
            mTextView = (TextView) view.findViewById(R.id.info_text_in_search_list);
            mImageView = (ImageView) view.findViewById(R.id.card_book_image_in_search_list);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }



    public SearchResultListRecyclerViewAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.test_items);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public SearchResultItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchResultItemViewHolder(mLayoutInflater.inflate(R.layout.book_card_in_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final SearchResultItemViewHolder holder, int position) {
        holder.mTextView.setText(mTitles[position]);
        Picasso.with(mContext)
                .load(mTitles[position])
                .into(holder.mImageView, new Callback.EmptyCallback() {
                    @Override
                    public void onSuccess() {
                        //Get palette to produce card's background color
                        Bitmap source = ((BitmapDrawable) holder.mImageView.getDrawable()).getBitmap();
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
        return mTitles == null ? 0 : mTitles.length;
    }


}

