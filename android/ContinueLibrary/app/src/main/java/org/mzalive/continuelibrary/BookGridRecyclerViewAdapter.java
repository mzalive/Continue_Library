package org.mzalive.continuelibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
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

import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;
import org.mzalive.continuelibrary.communication.BookManage;

import java.util.ArrayList;

/**
 * Created by mzalive on 7/3/15.
 */
public class BookGridRecyclerViewAdapter extends RecyclerView.Adapter<BookGridRecyclerViewAdapter.BookGridViewHolder> {

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private ArrayList<Book> mBooks;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BookGridViewHolder extends RecyclerView.ViewHolder {

        private final double CARD_HEIGHT_SCALE_RATIO = 0.48;

        public CardView cardView;
        public TextView mTextView;
        public ImageView mImageView;
        public BookGridViewHolder(View view) {
            super(view);

            DisplayMetrics dm = view.getResources().getDisplayMetrics();
            ViewGroup.LayoutParams lp = view.getLayoutParams();
//            lp.height = (int) (lp.width * 1.2);
            lp.height = (int) (CARD_HEIGHT_SCALE_RATIO * dm.widthPixels);
            view.setLayoutParams(lp);

            cardView = (CardView) view.findViewById(R.id.card_in_grid);
            mTextView = (TextView) view.findViewById(R.id.title_in_grid);
            mImageView = (ImageView) view.findViewById(R.id.card_book_image_in_grid);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }



    public BookGridRecyclerViewAdapter(Context context, ArrayList<Book> books) {
        mBooks = books;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    @Override
    public BookGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookGridViewHolder(mLayoutInflater.inflate(R.layout.book_card_in_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(final BookGridViewHolder holder, int position) {
        holder.mTextView.setText(mBooks.get(position).getTitle());
        Picasso.with(mContext)
                .load(mBooks.get(position).getImage())
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
        return mBooks == null ? 0 : mBooks.size();
    }





}

