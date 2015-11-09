package com.ryansteckler.nlpunbounce.tasker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ryansteckler.inappbilling.IabHelper;
import com.ryansteckler.inappbilling.IabResult;
import com.ryansteckler.inappbilling.Inventory;
import com.ryansteckler.nlpunbounce.AlarmDetailFragment;
import com.ryansteckler.nlpunbounce.AlarmsFragment;
import com.ryansteckler.nlpunbounce.BaseDetailFragment;
import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.WakelockDetailFragment;
import com.ryansteckler.nlpunbounce.WakelocksFragment;

public class TaskerActivity extends Activity
        implements
        WakelocksFragment.OnFragmentInteractionListener,
        BaseDetailFragment.FragmentInteractionListener,
        AlarmsFragment.OnFragmentInteractionListener,
        TaskerWhichFragment.OnFragmentInteractionListener {

    public static final String EXTRA_BUNDLE = "com.twofortyfouram.locale.intent.extra.BUNDLE";
    public static final String EXTRA_BLURB = "com.twofortyfouram.locale.intent.extra.BLURB";
    public static final String BUNDLE_TYPE = "type";
    public static final String BUNDLE_NAME = "name";
    public static final String BUNDLE_SECONDS = "seconds";
    public static final String BUNDLE_ENABLED = "enabled";
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
                    Log.d("Unbounce", "IAP result failed with code: " + result.getMessage());
                }
                else {
                    // does the user have the premium upgrade?
                    Log.d("Unbounce", "IAP result succeeded");
                    if (inventory != null) {
                        Log.d("Unbounce", "IAP inventory exists");

                        if (inventory.hasPurchase("donate_1") ||
                                inventory.hasPurchase("donate_5") ||
                                inventory.hasPurchase("donate_10")) {
                            Log.d("Unbounce", "IAP inventory contains a donation");

                            mIsPremium = true;
                        }
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
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
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

        Button save = (Button)findViewById(R.id.buttonTaskerSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Bundle taskerBundle = null;

                //Set the default tasker values
                taskerBundle = new Bundle();
                String blurb = "";

                WakelockDetailFragment wlFragment = (WakelockDetailFragment)fragmentManager.findFragmentByTag("wakelock_detail");
                if (wlFragment != null) {
                    taskerBundle.putString(BUNDLE_TYPE, "wakelock");
                    taskerBundle.putString(BUNDLE_NAME, wlFragment.getName());
                    taskerBundle.putLong(BUNDLE_SECONDS, wlFragment.getSeconds());
                    taskerBundle.putBoolean(BUNDLE_ENABLED, wlFragment.getEnabled());
                    blurb = wlFragment.getName() + " - " + (wlFragment.getEnabled() ?
                            getResources().getString(R.string.tasker_on) :
                            getResources().getString(R.string.tasker_off)) +
                            " - " + wlFragment.getSeconds();
                } else {
                    AlarmDetailFragment alarmFragment = (AlarmDetailFragment)fragmentManager.findFragmentByTag("alarm_detail");
                    if (alarmFragment != null) {
                        taskerBundle.putString(BUNDLE_TYPE, "alarm");
                        taskerBundle.putString(BUNDLE_NAME, alarmFragment.getName());
                        taskerBundle.putLong(BUNDLE_SECONDS, alarmFragment.getSeconds());
                        taskerBundle.putBoolean(BUNDLE_ENABLED, alarmFragment.getEnabled());
                        blurb = alarmFragment.getName() + " - " + (alarmFragment.getEnabled() ?
                                getResources().getString(R.string.tasker_on) :
                                getResources().getString(R.string.tasker_off)) +
                                " - " + alarmFragment.getSeconds();
                    }
                }

                final Intent resultIntent = new Intent();
                resultIntent.putExtra(EXTRA_BUNDLE, taskerBundle);
                resultIntent.putExtra(EXTRA_BLURB, blurb);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        //If we have a bundle passed to us, we need to pre-populate that state in the detail page
        if (savedInstanceState != null) {
            Bundle savedBundle = getIntent().getBundleExtra(EXTRA_BUNDLE);
            if (savedBundle != null) {
                String type = savedBundle.getString(BUNDLE_TYPE);
                long seconds = savedBundle.getLong(BUNDLE_SECONDS);
                boolean enabled = savedBundle.getBoolean(BUNDLE_ENABLED);
                String name = savedBundle.getString(BUNDLE_NAME);

                //TODO:  Pass to detail fragment, and launch detail immediately (don't show "which" or "list")
            }

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

    @Override
    public void onWakelocksSetTitle(String title) {
    }

    @Override
    public void onWakelocksSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
        Button save = (Button)findViewById(R.id.buttonTaskerSave);
        save.setVisibility(View.GONE);
    }

    @Override
    public void onDetailSetTitle(String title) {
    }

    @Override
    public void onDetailSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
        Button save = (Button)findViewById(R.id.buttonTaskerSave);
        save.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAlarmsSetTitle(String title) {
    }

    @Override
    public void onAlarmsSetTaskerTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
        Button save = (Button)findViewById(R.id.buttonTaskerSave);
        save.setVisibility(View.GONE);
    }

    @Override
    public void onTaskerResetSelected() {
        Bundle taskerBundle = new Bundle();
        taskerBundle.putString(BUNDLE_TYPE, "reset");
        String blurb = getResources().getString(R.string.tasker_reset_stats);

        final Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_BUNDLE, taskerBundle);
        resultIntent.putExtra(EXTRA_BLURB, blurb);
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    @Override
    public void onTaskerWhichSetTitle(String title) {
        TextView text = (TextView)findViewById(R.id.textTaskerHeader);
        text.setText(title);
        Button save = (Button)findViewById(R.id.buttonTaskerSave);
        save.setVisibility(View.GONE);
    }
}
