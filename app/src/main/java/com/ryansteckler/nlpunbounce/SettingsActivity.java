package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.ryansteckler.inappbilling.IabHelper;
import com.ryansteckler.nlpunbounce.helpers.SettingsHelper;


public class SettingsActivity extends Activity {

    private static final String TAG = "UnbounceSettings";

    IabHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().replace(android.R.id.content,
                    new PrefsFragment()).commit();

    }

    public static class PrefsFragment extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // this is important because although the handler classes that read these settings
            // are in the same package, they are executed in the context of the hooked package
            getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
            addPreferencesFromResource(R.xml.preferences);

            final SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
            sharedPref.registerOnSharedPreferenceChangeListener(this);

            onSharedPreferenceChanged(sharedPref, "debug_logging");
            onSharedPreferenceChanged(sharedPref, "show_launcher_icon");

            //Hook up the custom clicks
            Preference pref = (Preference) findPreference("reset_defaults");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    //Make sure
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_reset_settings_title)
                            .setMessage(R.string.alert_reset_settings_content)
                            .setPositiveButton(R.string.dialog_reset, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SettingsHelper.resetToDefaults(sharedPref);
                                    Toast.makeText(getActivity(), R.string.reset_settings_confirmation, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                    return true;
                }
            });
        }

        private void enableDependent(String control, boolean enable)
        {
            Preference controlToAffect = (Preference)findPreference(control);
            controlToAffect.setEnabled(enable);
        }

        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {

            if (key.equals("debug_logging"))
            {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);
            }
            else if (key.equals("show_launcher_icon"))
            {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);

                PackageManager packageManager = getActivity().getPackageManager();
                int state = value ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                ComponentName aliasName = new ComponentName(getActivity(), "com.ryansteckler.nlpunbounce.Settings-Alias");
                packageManager.setComponentEnabledSetting(aliasName, state, PackageManager.DONT_KILL_APP);
            }
        }


        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences()
                    .registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}