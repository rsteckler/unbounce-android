package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import com.ryansteckler.nlpunbounce.helpers.LocaleHelper;
import com.ryansteckler.nlpunbounce.helpers.SettingsHelper;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;


public class SettingsActivity extends Activity {

    private static final String TAG = "Anmplify: ";

    int mCurTheme = ThemeHelper.THEME_DEFAULT;
    int mCurForceEnglish = -1;

    static int mClicksOnDebug = 0;
    static Preference mExtendedDebugCategory = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurTheme = ThemeHelper.onActivityCreateSetTheme(this);
        mCurForceEnglish = LocaleHelper.onActivityCreateSetLocale(this);
        mClicksOnDebug = 0;

        // Display the fragment as the main content.
        if (savedInstanceState == null)
            getFragmentManager().beginTransaction().replace(android.R.id.content,
                    new PrefsFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update theme
        mCurTheme = ThemeHelper.onActivityResumeVerifyTheme(this, mCurTheme);
        mCurForceEnglish = LocaleHelper.onActivityResumeVerifyLocale(this, mCurForceEnglish);
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

            onSharedPreferenceChanged(sharedPref, "logging_level");
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

            pref = (Preference) findPreference("about_author");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (sharedPref.getBoolean("show_extended_debug_options", false)) {
                        mClicksOnDebug++;
                        Toast.makeText(getActivity(), "You are " + (10 - mClicksOnDebug) + " from enabling extended debug options.", Toast.LENGTH_SHORT).show();
                        if (mClicksOnDebug == 10) {
                            SharedPreferences.Editor edit = sharedPref.edit();
                            edit.putBoolean("show_extended_debug_options", true);
                            edit.apply();
                            getPreferenceScreen().addPreference(mExtendedDebugCategory);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "You've already enabled extended debug options.", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });


//            Hide extended debug options
            mExtendedDebugCategory = findPreference("extended_debug_options");
            if (!sharedPref.getBoolean("show_extended_debug_options", false)) {
                getPreferenceScreen().removePreference(mExtendedDebugCategory);
            }

        }

        private void enableDependent(String control, boolean enable)
        {
            Preference controlToAffect = (Preference)findPreference(control);
            controlToAffect.setEnabled(enable);
        }

        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, String key) {

            if (key.equals("logging_level"))
            {
                ListPreference pref = (ListPreference) findPreference(key);
                String entry = sharedPreferences.getString(key, "default");
                if (entry.equals("quiet")) {
                    pref.setSummary("Quiet");
                } else if (entry.equals("verbose")) {
                    pref.setSummary("Verbose");
                } else {
                    pref.setSummary("Default");
                }
            }
            else if (key.equals("show_launcher_icon")) {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);

                PackageManager packageManager = getActivity().getPackageManager();
                int state = value ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                ComponentName aliasName = new ComponentName(getActivity(), "com.ryansteckler.nlpunbounce.Settings-Alias");
                packageManager.setComponentEnabledSetting(aliasName, state, PackageManager.DONT_KILL_APP);
            }
            else if (key.equals("theme")) {
                ListPreference pref = (ListPreference) findPreference(key);
                if (sharedPreferences.getString(key, "default").equals("dark")) {
                    ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DARK);
                } else {
                    ThemeHelper.changeToTheme(this.getActivity(), ThemeHelper.THEME_DEFAULT);
                }
            } else if (key.equals("force_english")) {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);

                if (value) {
                    LocaleHelper.forceEnglish(this.getActivity());
                } else {
                    LocaleHelper.revertToSystem(getActivity());
                }
            }else if (key.equals("enable_service_block")) {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);
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