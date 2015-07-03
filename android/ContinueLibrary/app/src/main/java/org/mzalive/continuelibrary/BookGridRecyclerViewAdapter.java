package org.mzalive.continuelibrary;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by mzalive on 7/3/15.
 */
public class BookGridRecyclerViewAdapter extends RecyclerView.Adapter<BookGridRecyclerViewAdapter.BookGridViewHolder> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private String[] mTitles;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class BookGridViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public BookGridViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.info_text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("NormalTextViewHolder", "onClick--> position = " + getPosition());
                }
            });
        }
    }



    public BookGridRecyclerViewAdapter(Context context) {
        mTitles = context.getResources().getStringArray(R.array.test_items);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BookGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookGridViewHolder(mLayoutInflater.inflate(R.layout.book_card_in_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(BookGridViewHolder holder, int position) {
        holder.mTextView.setText(mTitles[position]);
    }

    @Override
    public int getItemCount() {
        return mTitles == null ? 0 : mTitles.length;
    }


}

