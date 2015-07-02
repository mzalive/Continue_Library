package org.mzalive.continuelibrary;

/**
 * Created by mzalive on 7/2/15.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A dummy fragment representing a section of the app, but that simply displays dummy text.
 */
public class ListSectionFragment extends Fragment {

    private int mFragmentCategory;
    private int mSectionCategory;

    public ListSectionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_section, container, false);
        Bundle args = getArguments();
        mFragmentCategory = args.getInt(MainActivityFragment.ARG_FRAGMENT_CATEGORY, 0);
        mSectionCategory = args.getInt(MainActivityFragment.ARG_SECTION_CATEGORY, 0);
        TextView textview = (TextView) rootView.findViewById(R.id.text1);
        textview.setText("Fragment Category" + mFragmentCategory + "\nSection Category" + mSectionCategory);
        return rootView;
    }
}
