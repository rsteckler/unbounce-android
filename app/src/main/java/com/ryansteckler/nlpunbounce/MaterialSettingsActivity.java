package com.ryansteckler.nlpunbounce;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ryansteckler.inappbilling.IabHelper;
import com.ryansteckler.inappbilling.IabResult;
import com.ryansteckler.inappbilling.Inventory;
import com.ryansteckler.inappbilling.Purchase;

import java.util.ArrayList;
import java.util.List;

public class MaterialSettingsActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        WakelocksFragment.OnFragmentInteractionListener,
        WakelockDetailFragment.FragmentInteractionListener,
        HomeFragment.OnFragmentInteractionListener,
        AlarmsFragment.OnFragmentInteractionListener,
        AlarmDetailFragment.FragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    IabHelper mHelper;

    private boolean mIsPremium = false;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int mCurrentSection = 1;
    int mLastActionbarColor = 0;

    private static final String TAG = "NlpUnbounceSettings: ";

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

        mLastActionbarColor = getResources().getColor(R.color.background_primary);

        //Setup donations

        final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    // update UI accordingly
                    updateDonationUi();
                }
                else {
                    // does the user have the premium upgrade?
                    if (inventory != null) {
                        mIsPremium = inventory.hasPurchase("donate_1") ||
                                inventory.hasPurchase("donate_5") ||
                                inventory.hasPurchase("donate_10");
                    }
                    // update UI accordingly
                    if (isPremium()) {
                        updateDonationUi();
                    }
                }
            }
        };

        //Normally we would secure this key, but we're not licensing this app.
        String base64billing = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxwicOx54j03qBil36upqYab0uBWnf+WjoSRNOaTD9mkqj9bLM465gZlDXhutMZ+n5RlHUqmxl7jwH9KyYGTbwFqCxbLMCwR4oDhXVhX4fS6iggoHY7Ek6EzMT79x2XwCDg1pdQmX9d9TYRp32Sw2E+yg2uZKSPW29ikfdcmfkHcdCWrjFSuMJpC14R3d9McWQ7sg42eQq2spIuSWtP8ARGtj1M8eLVxgkQpXWrk9ijPgVcAbNZYWT9ndIZoKPg7VJVvzzAUNK/YOb+BzRurqJ42vCZy1+K+E4EUtmg/fxawHfXLZ3F/gNwictZO9fv1PYHPMa0sezSNVFAcm0yP1BwIDAQAB";
        mHelper = new IabHelper(MaterialSettingsActivity.this, base64billing);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result)
            {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                }
                mHelper.queryInventoryAsync(false, mGotInventoryListener);

            }
        });


        GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
        Tracker tracker = ga.newTracker("UA-11575064-3");
        tracker.setScreenName("MaterialSettingsActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
    }

    private void updateDonationUi() {
        if (isPremium()) {
            TextView textview = (TextView) findViewById(R.id.textviewKarma);
            if (textview != null)
                textview.setVisibility(View.VISIBLE);
            View donateView = (View) findViewById(R.id.layoutDonate);
            if (donateView != null)
                donateView.setVisibility(View.GONE);
        }
    }

    public boolean isPremium() {
        return mIsPremium;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase)
        {
            if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_USER_CANCELED ||
                    result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE ||
                    result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_DEVELOPER_ERROR ||
                    result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ERROR ||
                    result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED ||
                    result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE)
            {
                Toast.makeText(MaterialSettingsActivity.this, "Thank you for the thought, but the donation failed.  -Ryan", Toast.LENGTH_LONG).show();
            }
            else if (result.getResponse() == IabHelper.BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED) {
                Toast.makeText(MaterialSettingsActivity.this, "Thank you for the thought, but you've already donated!  -Ryan", Toast.LENGTH_LONG).show();
            }
            else if (result.isSuccess()) {
                Toast.makeText(MaterialSettingsActivity.this, "Thank you SO much for donating!  -Ryan", Toast.LENGTH_LONG).show();
                mIsPremium = true;
                updateDonationUi();
            }
            else
            {
                Toast.makeText(MaterialSettingsActivity.this, "Thank you for the thought, but the donation failed.  -Ryan", Toast.LENGTH_LONG).show();
            }

        }

    };

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        if (position == 0) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.container, HomeFragment.newInstance(position + 1))
                    .commit();
        } else if (position == 1) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.container, WakelocksFragment.newInstance(position + 1))
                    .addToBackStack("wakelocks")
                    .commit();
        } else if (position == 2) {
            fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .replace(R.id.container, AlarmsFragment.newInstance(position + 1))
                    .addToBackStack("alarms")
                    .commit();
        } else if (position == 3) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
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
            }
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onWakelocksSetTitle(String title) {
        mTitle = title;
        restoreActionBar();
        animateActionbarBackground(getResources().getColor(R.color.background_secondary), 400);
    }

    @Override
    public void onHomeSetTitle(String title) {
        mTitle = title;
        restoreActionBar();
        animateActionbarBackground(getResources().getColor(R.color.background_primary), 400);
    }

    @Override
    public void onWakelockDetailSetTitle(String title) {
        mTitle = title;
        restoreActionBar();
    }

    private void animateActionbarBackground(final int colorTo, final int durationInMs) {
        //Not great form, but the animation to show the details view takes 400ms.  We'll set our background
        //color back to normal once the animation finishes.  I wish there was a more elegant way to avoid
        //a timer.
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), mLastActionbarColor, colorTo);
        final ColorDrawable colorDrawable = new ColorDrawable(mLastActionbarColor);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
                colorDrawable.setColor((Integer) animator.getAnimatedValue());
                getActionBar().setBackgroundDrawable(colorDrawable);
            }
        });
        if (durationInMs >= 0)
            colorAnimation.setDuration(durationInMs);
        colorAnimation.start();
        mLastActionbarColor = colorTo;

    }


    @Override
    public void onAlarmDetailSetTitle(String title) {
        mTitle = title;
        restoreActionBar();
    }

    @Override
    public void onAlarmsSetTitle(String title) {
        mTitle = title;
        restoreActionBar();
        animateActionbarBackground(getResources().getColor(R.color.background_four), 400);
    }
}
