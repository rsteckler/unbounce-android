package com.ryansteckler.nlpunbounce;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

public class MaterialSettingsActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        WakelockFragment.OnWakelockFragmentInteractionListener,
        WakelockDetailFragment.OnFragmentInteractionListener {

//    private boolean mShowingDetail = false;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event)  {
//        if (keyCode == KeyEvent.KEYCODE_BACK ) {
//            if (mShowingDetail) {
//
//                return true;
//            }
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }
//
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    public static String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private int mCurrentSection = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_settings);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                drawerLayout);

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (position == 0) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance(position + 1))
                    .commit();
        } else if (position == 1) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, WakelockFragment.newInstance(position + 1))
                    .commit();
        } else if (position == 2) {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, HomeFragment.newInstance(position + 1))
                    .commit();
        }
    }

    public void onSectionAttached(int number) {
        mCurrentSection = number;
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_home);
                break;
            case 2:
                mTitle = getString(R.string.title_wakelocks);
                break;
            case 3:
                mTitle = getString(R.string.title_alarms);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            if (mCurrentSection == 1) {
                getMenuInflater().inflate(R.menu.home, menu);
                restoreActionBar();
                return true;
            } else if (mCurrentSection == 3) {
                getMenuInflater().inflate(R.menu.home, menu);
                restoreActionBar();
                return true;
            }

        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetailInteraction(String id) {
        View enableSwitch = findViewById(R.id.wakelock_switch);
        enableSwitch.setVisibility(View.VISIBLE);

        View sortItem = findViewById(R.id.action_sort);
        sortItem.setVisibility(View.GONE);

        invalidateOptionsMenu();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
