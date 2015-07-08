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

import com.melnykov.fab.FloatingActionButton;

import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;
import org.mzalive.continuelibrary.communication.BookManage;
import org.mzalive.continuelibrary.communication.Search;
import org.mzalive.continuelibrary.communication.UserInfo;
import org.mzalive.continuelibrary.communication.WishlistManage;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment class holds a book grid view for all conditions generated with args.
 */
public class SearchResultListFragment extends Fragment {

    private String query;

    private RecyclerView mRecyclerView;
    private SearchResultListRecyclerViewAdapter mAdapter;

    public SearchResultListFragment() { }

    public static Fragment newInstance(String query) {
        SearchResultListFragment fragment = new SearchResultListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        query = args.getString("query");

        View rootView = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_search_result_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //This is the code to provide a sectioned list
        List<SectionedRecyclerViewAdapter.Section> sections = new ArrayList<>();

        new SearchAsyncTask(getActivity(), mRecyclerView, sections).execute(query);

        return rootView;
    }

    public class SearchAsyncTask extends AsyncTask<String, Integer, ArrayList<Book>>
    {
        Activity mContext;
        RecyclerView mRecyclerView;
        List<SectionedRecyclerViewAdapter.Section> mSections;

        int offset_continue;
        int offset_wishlist;
        int offset_internet;

        public SearchAsyncTask (Activity context, RecyclerView recyclerView, List<SectionedRecyclerViewAdapter.Section> sections) {
            mContext = context;
            mRecyclerView = recyclerView;
            mSections = sections;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();

        }
        @Override
        protected ArrayList<Book> doInBackground(String... params)
        {
            String query = params[0];
            BookList continueBookList;
            BookList doubanBookList;
            ArrayList<Book> bookList = new ArrayList<>();

            SharedPreferences preferences = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
            String uid = preferences.getString("userId","-1");

            continueBookList = Search.search(uid, query, 0, 10, 0, 10);
            doubanBookList = Search.searchNet(query, 0, 10);

            offset_continue = 0;
            offset_wishlist = continueBookList.getBookCount();
            offset_internet = offset_wishlist + continueBookList.getWishCount();

            int cursor_continue = 0;
            int cursor_wishlist = 0;

            if (continueBookList.getBooks() != null) {
                for (int i = 0; i < continueBookList.getBooks().size(); ++i) {
                    Book book = continueBookList.getBooks().get(i);
                    switch (book.getLocation()) {
                        case Book.LOCATION_CONTINUE:
                            bookList.add(cursor_continue, book);
                            cursor_continue++;
                            cursor_wishlist++;
                            break;
                        case Book.LOCATION_WISHLIST:
                            bookList.add(cursor_wishlist, book);
                            cursor_wishlist++;
                            break;
                    }
                }
                if (offset_wishlist != cursor_continue)
                    Log.d("Search result composer", "Continue Book count conflict!");
                if (continueBookList.getWishCount() != cursor_wishlist - cursor_continue)
                    Log.d("Search result composer", "Wishlist Book count conflict!");
            }

            if (doubanBookList.getBooks() != null) {
                bookList.addAll(doubanBookList.getBooks());
                if (doubanBookList.getBookCount() != doubanBookList.getBooks().size())
                    Log.d("Search result composer", "Douban Book count conflict!");
            }

                return bookList;
        }
        @Override
        protected void onProgressUpdate(Integer... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(ArrayList<Book> result)
        {
            super.onPostExecute(result);

            int[] offset = new int[3];

            offset[0] = offset_continue;
            offset[1] = offset_wishlist;
            offset[2] = offset_internet;

            mAdapter = new SearchResultListRecyclerViewAdapter(getActivity(), result, offset);

            //Sections
            mSections.add(new SectionedRecyclerViewAdapter.Section(offset_continue, getString(R.string.search_section_continue)));
            mSections.add(new SectionedRecyclerViewAdapter.Section(offset_wishlist, getString(R.string.search_section_wishlist)));
            mSections.add(new SectionedRecyclerViewAdapter.Section(offset_internet, getString(R.string.search_section_internet)));

            //Add your adapter to the sectionAdapter
            SectionedRecyclerViewAdapter.Section[] dummy = new SectionedRecyclerViewAdapter.Section[mSections.size()];
            SectionedRecyclerViewAdapter mSectionedAdapter = new SectionedRecyclerViewAdapter(getActivity(),R.layout.search_result_section_header,R.id.text,mAdapter);
            mSectionedAdapter.setSections(mSections.toArray(dummy));

            mRecyclerView.setAdapter(mSectionedAdapter);

            FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
            fab.show();
            fab.attachToRecyclerView(mRecyclerView);


        }
    }


}