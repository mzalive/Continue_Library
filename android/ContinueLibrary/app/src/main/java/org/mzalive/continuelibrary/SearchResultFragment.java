package org.mzalive.continuelibrary;

/**
 * Created by mzalive on 7/2/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment class holds the list of search results
 */
public class SearchResultFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public SearchResultFragment() {  }

    public static Fragment newInstance(int fragmentCategory, int sectionCategory) {
        SearchResultFragment fragment = new SearchResultFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search_result_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_search_result);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new SearchResultListRecyclerViewAdapter(getActivity()));

        return rootView;
    }


}