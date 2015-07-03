package org.mzalive.continuelibrary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.karim.MaterialTabs;

/**
 * A placeholder fragment containing a simple view.
 */
public class LibraryFragment extends Fragment {

    public final static int CONTINUE_FRAGMENT_CATEGORY = 0;
    public final static int USER_FRAGMENT_CATEGORY = 1;
    public final static int BOOKLIST_SECTION_CATEGORY = 0;
    public final static int WISHLIST_SECTION_CATEGORY = 1;

    public static final String ARG_FRAGMENT_CATEGORY = "fragment_category";
    public static final String ARG_SECTION_CATEGORY = "section_category";

    private int mFragmentCategory = 0;
    private ViewPager pager;
    private MaterialTabs tabs;

    public LibraryFragment() {}

    public static Fragment newInstance(int fragmentCategory){
        LibraryFragment fragment = new LibraryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_FRAGMENT_CATEGORY, fragmentCategory);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFragmentCategory = getArguments().getInt(ARG_FRAGMENT_CATEGORY, 0);

        View rootView = inflater.inflate(R.layout.fragment_library, container, false);

        // Initialize the ViewPager and set an adapter
        pager = (ViewPager) rootView.findViewById(R.id.continue_container);
        pager.setAdapter(new AppSectionsPagerAdapter(getChildFragmentManager(), getActivity(), mFragmentCategory));
        int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        // Bind the tabs to the ViewPager
        tabs = (MaterialTabs) rootView.findViewById(R.id.material_tabs);
        tabs.setViewPager(pager);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public class AppSectionsPagerAdapter extends FragmentPagerAdapter {
        Context mContext;
        int mFragmentCategory;

        public AppSectionsPagerAdapter(FragmentManager fm, Context context, int fragmentCategory) {
            super(fm);
            mContext = context;
            mFragmentCategory = fragmentCategory;
        }


        @Override
        public Fragment getItem(int i) {
            return BookGridFragment.newInstance(mFragmentCategory, i);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (mFragmentCategory) {
                case CONTINUE_FRAGMENT_CATEGORY:
                    switch (position) {
                        case BOOKLIST_SECTION_CATEGORY:
                            return mContext.getString(R.string.continue_booklist_section);
                        case WISHLIST_SECTION_CATEGORY:
                            return mContext.getString(R.string.continue_wishlist_section);
                    }
                case USER_FRAGMENT_CATEGORY:
                    switch (position) {
                        case BOOKLIST_SECTION_CATEGORY:
                            return mContext.getString(R.string.user_booklist_section);
                        case WISHLIST_SECTION_CATEGORY:
                            return mContext.getString(R.string.user_wishlist_section);
                    }
            }
            return "Shouldn't be here";
        }
    }


}
