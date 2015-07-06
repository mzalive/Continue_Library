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


}