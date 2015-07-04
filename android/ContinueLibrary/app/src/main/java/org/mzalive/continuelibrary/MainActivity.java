package org.mzalive.continuelibrary;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import org.mzalive.continuelibrary.Base.*;
import org.mzalive.continuelibrary.communication.*;

public class MainActivity extends AppCompatActivity {

    private SearchBox search;
    private Fragment mContent;
    private Fragment continueFragment;
    private Fragment userFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initializing Fragments
         */
        continueFragment = LibraryFragment.newInstance(LibraryFragment.CONTINUE_FRAGMENT_CATEGORY);
        userFragment = LibraryFragment.newInstance(LibraryFragment.USER_FRAGMENT_CATEGORY);

        /**
         * Toolbar Configuration
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        /**
         * Navigation Drawer Configuration
         */
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.background_profile_default)
                .addProfiles(
                        new ProfileDrawerItem().withName("Matthew Mi").withEmail("mzalive@gmail.com").withIcon(getResources().getDrawable(R.drawable.ayanami))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        final Drawer naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_continue).withIcon(FontAwesome.Icon.faw_university),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_user).withIcon(FontAwesome.Icon.faw_book),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_guide).withIcon(FontAwesome.Icon.faw_info_circle),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_question),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_envelope_o)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        switch (position) {
                            case 0:
                                switchFragment(continueFragment);
                                break;
                            case 1:
                                switchFragment(userFragment);
                                break;
                            default :
                                Toast.makeText(view.getContext(), ""+position,Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                })
                .build();

        /**
         * Inset Search Box Configuration
         */
        search = (SearchBox) findViewById(R.id.searchbox);
        Drawable recentIconDrawable = getResources().getDrawable(R.mipmap.ic_restore_black_24dp);
        recentIconDrawable.mutate().setAlpha(0x42);
        for(int x = 0; x < 5; x++){
            SearchResult option = new SearchResult("历史记录 " + Integer.toString(x), recentIconDrawable);
            search.addSearchable(option);
        }

        search.setLogoText((String) getResources().getText(R.string.app_name));
        //search.setDrawerLogo(getResources().getDrawable(R.mipmap.ic_launcher));
        search.setMenuListener(new SearchBox.MenuListener(){

            @Override
            public void onMenuClick() {
                //Hamburger has been clicked
                naviDrawer.openDrawer();
            }

        });
        search.setSearchListener(new SearchBox.SearchListener(){

            @Override
            public void onSearchOpened() {
                //Use this to tint the screen
            }

            @Override
            public void onSearchClosed() {
                //Use this to un-tint the screen
            }

            @Override
            public void onSearchTermChanged() {
                //React to the search term changing
                //Called after it has updated results
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(MainActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onSearchCleared() {

            }

        });

//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.content, continueFragment)
//                .commit();
        switchFragment(continueFragment);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            return true;
        //}

        //return super.onOptionsItemSelected(item);
    }
    public void login(View view) {
        Intent intent = new Intent(this, BookDedatilActivity.class);
        startActivity(intent);
        //mContent = userFragment;
        //getSupportFragmentManager().beginTransaction().hide(continueFragment).commit();
//        Thread thread=new Thread(new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                Log.d("click me", "click me");
//                BookList list = new BookList();
//                list = UserInfo.getMyBorrowlist("1", 1, 10);
//                Log.d("result", list.toString());
//                list = UserInfo.getMyWishlist("1", 1, 10);
//                list = BookManage.getBooklist(1, 10);
//                Log.d("result", list.toString());
//                Log.d("click me", "click me finish");
//            }
//        });
//        thread.start();

    }

    public void switchFragment(Fragment fragment) {
        String LOG_TAG = "switchFragment method";
        Log.d(LOG_TAG, "Accessed!");
        if (mContent == null) {
            Log.d(LOG_TAG, "mContent is NULL!");
            getSupportFragmentManager().beginTransaction().add(R.id.content, fragment).commit();
            mContent = fragment;
            return;
        }
        if (mContent != fragment) {
            Log.d(LOG_TAG, "mContent differs");
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!fragment.isAdded()) {
                Log.d(LOG_TAG, "Added");
                transaction.hide(mContent).add(R.id.content, fragment).commit();
            } else {
                Log.d(LOG_TAG, "Non-Added");
                transaction.hide(mContent).show(fragment).commit();
            }
            mContent = fragment;
        }
    }
}
