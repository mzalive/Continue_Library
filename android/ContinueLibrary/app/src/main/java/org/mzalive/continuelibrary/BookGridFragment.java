package org.mzalive.continuelibrary;

/**
 * Created by mzalive on 7/2/15.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        mRecyclerView.setAdapter(new BookGridRecyclerViewAdapter(getActivity()));


        return rootView;
    }


}