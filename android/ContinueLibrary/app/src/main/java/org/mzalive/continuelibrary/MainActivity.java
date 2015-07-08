package org.mzalive.continuelibrary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
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
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;
import com.squareup.picasso.Picasso;

import org.apache.http.conn.routing.RouteInfo;
import org.mzalive.continuelibrary.Base.Book;
import org.mzalive.continuelibrary.Base.BookList;
import org.mzalive.continuelibrary.communication.BaseFunctions;
import org.mzalive.continuelibrary.communication.BookManage;
import org.mzalive.continuelibrary.communication.Search;
import org.mzalive.continuelibrary.communication.UserInfo;

public class MainActivity extends AppCompatActivity {

    private SearchBox search;
    private FloatingActionButton fab;
    private Fragment mContent;
    private Fragment continueFragment;
    private Fragment userFragment;
    private Fragment searchResultListFragment;

    private AccountHeader headerResult;
    private Drawer naviDrawer;

    private SharedPreferences sp;

    private int currentDrawerItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //测试信息缓存
        sp = getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);
        Log.d("isLogin_print", String.valueOf(sp.getBoolean("isLogin", false)));
        Log.d("userId_print", sp.getString("userId", "-1"));
        Log.d("username_print", sp.getString("username", ""));
        sp.edit().clear().commit(); //Debug only, reset sp every time


        fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageAlpha(0xB3);


        /**
         * Initializing Fragments
         */
        continueFragment = LibraryFragment.newInstance(LibraryFragment.CONTINUE_FRAGMENT_CATEGORY);
        userFragment = LibraryFragment.newInstance(LibraryFragment.USER_FRAGMENT_CATEGORY);
        //searchResultListFragment = SearchResultListFragment.newInstance();

        /**
         * Toolbar Configuration
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBarMain);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        setSupportActionBar(toolbar);

        /**
         * Navigation Drawer Configuration
         */

        // Create the AccountHeader
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withSelectionListEnabled(false)
                .withProfileImagesClickable(false)
//                .withHeaderBackground(R.drawable.background_profile_default)
//                .addProfiles(
//                        new ProfileDrawerItem().withName("Matthew Mi").withEmail("mzalive@gmail.com").withIcon(getResources().getDrawable(R.drawable.ayanami))
//                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        naviDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                .withActionBarDrawerToggle(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_continue).withIcon(FontAwesome.Icon.faw_university),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_user).withIcon(FontAwesome.Icon.faw_user),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_guide).withIcon(FontAwesome.Icon.faw_info_circle),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_info_circle),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_paper_plane),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_passwd),
                        new SecondaryDrawerItem()
                        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        String TAG = "DrawerItemClickListener";
                        Log.d(TAG, "Position: " + position + " Clicked");
                        switch (position) {
                            case 0: //Continue
                                currentDrawerItem = 0;
                                switchFragment(continueFragment);
                                break;

                            case 1: //Mine
                                currentDrawerItem = 1;
                                SharedPreferences sp = getSharedPreferences("UserInfo", LoginActivity.MODE_PRIVATE);
                                boolean isLogin = sp.getBoolean("isLogin", false);
                                Log.d(TAG, "IsLogin: " + String.valueOf(isLogin));

                                if (!isLogin) {
                                    callForLogin();
                                } else
                                    switchFragment(userFragment);

                                break;

                            case 3: //Test only

                                break;

                            case 4: //About
                                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                                startActivity(intent);
                                break;

                            case 5: //Test Only
                                callForContact();
                                break;

                            case 7: //Passwd
                                callForPasswd();
                                break;

                            case 8: //Login & Logout
                                SharedPreferences _sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
                                if (_sp.getBoolean("isLogin", false))
                                    callForLogout();
                                else
                                    callForLogin();
                                break;

                            default:
                                Toast.makeText(view.getContext(), "" + position, Toast.LENGTH_SHORT).show();
                        }
                        parent.setSelection(currentDrawerItem);
                        return false;
                    }
                })
                .build();
        updateLoginInfo();



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
                //Toast.makeText(MainActivity.this, searchTerm +" Searched", Toast.LENGTH_LONG).show();
                searchResultListFragment = SearchResultListFragment.newInstance(searchTerm);
                switchFragment(searchResultListFragment);

            }

            @Override
            public void onSearchCleared() {

            }

        });

        switchFragment(continueFragment);

    }

    @Override
    public void onResume() {
        super.onResume();
        updateLoginInfo();
        naviDrawer.setSelection(currentDrawerItem, false);
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


    public void StartScanActivity(View view) {
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivity(intent);

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

    private void callForContact() {
        Intent intent=new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:mzalive+continuelibrary@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact_mail_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.contact_mail_content));
        try {
            startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
            Toast.makeText(this,getString(R.string.no_available_activity_error_hint),Toast.LENGTH_LONG).show();
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("Email error:",e.toString());
        }
    }

    private void callForLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    protected void callForLogout() {
        sp.edit().clear().commit();
        updateLoginInfo();
    }

    protected void callForPasswd() {
        Intent intent = new Intent(this, SetPasswordActivity.class);
        startActivity(intent);
    }

    public void updateLoginInfo() {
        //Retrieve login info
        boolean isLogin = sp.getBoolean("isLogin", false);
        String username = sp.getString("username","");
        String uid = sp.getString("userId", "-1");
        String avatarUrl = "https://cdn3.mindmeister.com/images/avatars/personal_avatar.png";
        String backgroundUrl = "https://m2.behance.net/rendition/pm/18526001/disp/6bc3dbcbf00ce6fa41d9349020f63d4f.png";

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }
        });

        headerResult.clear();
        if (isLogin) {
            //Picasso.with(this).load(backgroundUrl).into(headerResult.getHeaderBackgroundView());
            int[] src = {R.drawable.profile_background_0, R.drawable.profile_background_1, R.drawable.profile_background_2,
                        R.drawable.profile_background_3, R.drawable.profile_background_4};
            int random = ((int) (Math.random() * 4)) + 1;

            Log.d("Logint", "random = "+random);

            headerResult.setBackgroundRes(src[random]);

            headerResult.addProfiles(new ProfileDrawerItem()
                    .withName(username)
                    .withEmail(username+"@continue.com")
                    .withIcon(Uri.parse(avatarUrl)));
            naviDrawer.updateItem(new SecondaryDrawerItem().withName(R.string.drawer_item_passwd).setEnabled(true), 7);
            naviDrawer.updateItem(new SecondaryDrawerItem().withName(R.string.drawer_item_logout), 8);

        }
        else {
            headerResult.setBackgroundRes(R.drawable.profile_background_0);
            headerResult.addProfiles(new ProfileDrawerItem()
                    .withName(getString(R.string.username_placeholder))
                    .withIcon(getResources().getDrawable(R.drawable.profile_avatar_default)));
            naviDrawer.updateItem(new SecondaryDrawerItem().withName(R.string.drawer_item_passwd).setEnabled(false), 7);
            naviDrawer.updateItem(new SecondaryDrawerItem().withName(R.string.drawer_item_login), 8);

        }

    }


}
