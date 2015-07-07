package org.mzalive.continuelibrary;

/**
 * Created by mzalive on 7/2/15.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;
import org.mzalive.continuelibrary.communication.BookManage;
import org.mzalive.continuelibrary.communication.UserInfo;
import org.mzalive.continuelibrary.communication.WishlistManage;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment class holds a book grid view for all conditions generated with args.
 */
public class SearchResultListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private SearchResultListRecyclerViewAdapter mAdapter;

    public SearchResultListFragment() { }

    public static Fragment newInstance() {
        SearchResultListFragment fragment = new SearchResultListFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();

        View rootView = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_search_result_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new SearchResultListRecyclerViewAdapter(getActivity());

        //This is the code to provide a sectioned list
        List<SectionedRecyclerViewAdapter.Section> sections = new ArrayList<SectionedRecyclerViewAdapter.Section>();
        //Sections
        sections.add(new SectionedRecyclerViewAdapter.Section(0,"Section 1"));
        sections.add(new SectionedRecyclerViewAdapter.Section(3,"Section 2"));
        sections.add(new SectionedRecyclerViewAdapter.Section(6,"Section 3"));

        //Add your adapter to the sectionAdapter
        SectionedRecyclerViewAdapter.Section[] dummy = new SectionedRecyclerViewAdapter.Section[sections.size()];
        SectionedRecyclerViewAdapter mSectionedAdapter = new SectionedRecyclerViewAdapter(getActivity(),R.layout.search_result_section_header,R.id.text,mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        mRecyclerView.setAdapter(mSectionedAdapter);

        return rootView;
    }

    /*public class MyAsyncTask extends AsyncTask<String, Integer, BookList>
    {
        int mFragmentCategory = 0;
        int mSectionCategory = 0;
        Activity mContext;
        RecyclerView mRecyclerView;

        public MyAsyncTask(Activity context, RecyclerView recyclerView, int fragmentCategory, int sectionCategory) {
            mFragmentCategory = fragmentCategory;
            mSectionCategory = sectionCategory;
            mContext = context;
            mRecyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            Log.d("MAT", "PreExceute");

        }
        @Override
        protected BookList doInBackground(String... params)
        {
            BookList bookList = new BookList();
            SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            String uid = preferences.getString("userId","-1");

            return bookList;
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
    }*/


}