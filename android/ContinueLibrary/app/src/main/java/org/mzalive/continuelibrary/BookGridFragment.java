package org.mzalive.continuelibrary;

/**
 * Created by mzalive on 7/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;
import org.mzalive.continuelibrary.communication.BookManage;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A fragment class holds a book grid view for all conditions generated with args.
 */
public class BookGridFragment extends LibraryFragment {

    private int mFragmentCategory;
    private int mSectionCategory;

    private RecyclerView mRecyclerView;

    public BookGridFragment() {
    }

    public static Fragment newInstance(int fragmentCategory, int sectionCategory) {
        BookGridFragment fragment = new BookGridFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_FRAGMENT_CATEGORY, fragmentCategory);
        bundle.putInt(ARG_SECTION_CATEGORY, sectionCategory);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        mFragmentCategory = args.getInt(ARG_FRAGMENT_CATEGORY, 0);
        mSectionCategory = args.getInt(ARG_SECTION_CATEGORY, 0);

        View rootView = inflater.inflate(R.layout.fragment_book_grid, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_book_grid);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new MyAsyncTask(getActivity(), mRecyclerView).execute();
        Log.d("test","excuted");
    }

    public class MyAsyncTask extends AsyncTask<String, Integer, BookList>
    {
        Activity mContext;
        RecyclerView mRecyclerView;

        public MyAsyncTask(Activity context, RecyclerView recyclerView) {
            mContext = context;
            mRecyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d("MAT","PreExceute");

        }
        @Override
        protected BookList doInBackground(String... params)
        {

            return BookManage.getBooklist(0, 20);
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(BookList result)
        {
            super.onPostExecute(result);
            Log.d("MAT","Done");
            ArrayList<Book> mBooks = result.getBooks();
            BookGridRecyclerViewAdapter mAdapter = new BookGridRecyclerViewAdapter(getActivity(), mBooks);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

}