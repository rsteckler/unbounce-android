package com.ryansteckler.nlpunbounce.tasker;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.ryansteckler.inappbilling.IabHelper;
import com.ryansteckler.inappbilling.IabResult;
import com.ryansteckler.inappbilling.Inventory;
import com.ryansteckler.nlpunbounce.AlarmDetailFragment;
import com.ryansteckler.nlpunbounce.AlarmsFragment;
import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.WakelockDetailFragment;
import com.ryansteckler.nlpunbounce.WakelocksFragment;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

public class TaskerActivity extends Activity
        implements
        WakelocksFragment.OnFragmentInteractionListener,
        WakelockDetailFragment.FragmentInteractionListener,
        AlarmsFragment.OnFragmentInteractionListener,
        AlarmDetailFragment.FragmentInteractionListener,
        TaskerWhichFragment.OnFragmentInteractionListener {

    IabHelper mHelper;

    private boolean mIsPremium = false;

    Fragment mCurrentFragment = null;

    private static final String TAG = "NlpUnbounceTasker: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_tasker);

        //Setup donations
        final IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {

                if (result.isFailure()) {
                    // update UI accordingly
                    Log.d("NlpUnbounce", "IAP result failed with code: " + result.getMessage());
                }
                else {
                    // does the user have the premium upgrade?
                    Log.d("NlpUnbounce", "IAP result succeeded");
                    if (inventory != null) {
                        Log.d("NlpUnbounce", "IAP inventory exists");

                        if (inventory.hasPurchase("donate_1") ||
                                inventory.hasPurchase("donate_5") ||
                                inventory.hasPurchase("donate_10")) {
                            Log.d("NlpUnbounce", "IAP inventory contains a donation");

                            mIsPremium = true;
                        };
                    }
                }
            }
        };

        //Normally we would secure this key, but we're not licensing this app.
        String base64billing = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxwicOx54j03qBil36upqYab0uBWnf+WjoSRNOaTD9mkqj9bLM465gZlDXhutMZ+n5RlHUqmxl7jwH9KyYGTbwFqCxbLMCwR4oDhXVhX4fS6iggoHY7Ek6EzMT79x2XwCDg1pdQmX9d9TYRp32Sw2E+yg2uZKSPW29ikfdcmfkHcdCWrjFSuMJpC14R3d9McWQ7sg42eQq2spIuSWtP8ARGtj1M8eLVxgkQpXWrk9ijPgVcAbNZYWT9ndIZoKPg7VJVvzzAUNK/YOb+BzRurqJ42vCZy1+K+E4EUtmg/fxawHfXLZ3F/gNwictZO9fv1PYHPMa0sezSNVFAcm0yP1BwIDAQAB";
        mHelper = new IabHelper(TaskerActivity.this, base64billing);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result)
            {
                if (!result.isSuccess()) {
                    Log.d(TAG, "In-app Billing setup failed: " + result);
                    new AlertDialog.Builder(TaskerActivity.this)
                            .setTitle("Pro features unavailable.")
                            .setMessage("Your device doesn't support In App Billing.  You won't be able to purchase the Pro features of Unbounce.  This could be because you need to update your Google Play Store application, or because you live in a country where In App Billing is disabled.")
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
                else {
                    mHelper.queryInventoryAsync(false, mGotInventoryListener);
                }

            }
        });


        GoogleAnalytics ga = GoogleAnalytics.getInstance(this);
        Tracker tracker = ga.newTracker("UA-11575064-3");
        tracker.setScreenName("TaskerActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());
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

    @Override
    protected void onStart() {
        super.onStart();
        mCurrentFragment = TaskerWhichFragment.newInstance();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, mCurrentFragment)
                .commit();

    }

    // update the main content by replacing fragments
//        FragmentManager fragmentManager = getFragmentManager();
//        if (position == 0) {
//            fragmentManager.beginTransaction()
//                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
//                    .replace(R.id.container, HomeFragment.newInstance(position + 1))
//                    .commit();
//        } else if (position == 1) {
//            fragmentManager.beginTransaction()
//                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
//                    .replace(R.id.container, WakelocksFragment.newInstance(position + 1))
//                    .addToBackStack("wakelocks")
//                    .commit();
//        } else if (position == 2) {
//            fragmentManager.beginTransaction()
//                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
//                    .replace(R.id.container, AlarmsFragment.newInstance(position + 1))
//                    .addToBackStack("alarms")
//                    .commit();
//        } else if (position == 3) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//        }

    @Override
    public void onWakelocksSetTitle(String title) {
    }

    @Override
    public void onWakelocksSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
    }

    @Override
    public void onWakelockDetailSetTitle(String title) {
    }

    @Override
    public void onWakelockDetailSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
    }

    @Override
    public void onAlarmDetailSetTitle(String title) {
    }

    @Override
    public void onAlarmDetailSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
    }

    @Override
    public void onAlarmsSetTitle(String title) {
    }

    @Override
    public void onAlarmsSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
    }

    @Override
    public void onTaskerWakelockSelected() {
    }

    @Override
    public void onTaskerAlarmSelected() {
    }

    @Override
    public void onTaskerWhichSetTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
    }
}
