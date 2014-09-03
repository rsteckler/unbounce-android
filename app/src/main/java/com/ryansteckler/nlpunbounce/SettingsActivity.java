package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

import java.sql.Time;

public class SettingsActivity extends Activity {
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
            onSharedPreferenceChanged(sharedPref, "alarm_locator_enabled");
            onSharedPreferenceChanged(sharedPref, "alarm_detection_enabled");
            onSharedPreferenceChanged(sharedPref, "wakelock_nlp_enabled");
            onSharedPreferenceChanged(sharedPref, "wakelock_collector_enabled");
            onSharedPreferenceChanged(sharedPref, "alarm_locator_seconds");
            onSharedPreferenceChanged(sharedPref, "alarm_detection_seconds");
            onSharedPreferenceChanged(sharedPref, "wakelock_nlp_seconds");
            onSharedPreferenceChanged(sharedPref, "wakelock_collector_seconds");
            onSharedPreferenceChanged(sharedPref, "ALARM_WAKEUP_LOCATOR_blocked");
            onSharedPreferenceChanged(sharedPref, "ALARM_WAKEUP_ACTIVITY_DETECTION_blocked");
            onSharedPreferenceChanged(sharedPref, "NlpWakeLock_blocked");
            onSharedPreferenceChanged(sharedPref, "NlpCollectorWakeLock_blocked");
            onSharedPreferenceChanged(sharedPref, "debug_logging");

            //Hook up the custom clicks
            Preference pref = (Preference) findPreference("ALARM_WAKEUP_LOCATOR_blocked");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong("ALARM_WAKEUP_LOCATOR_blocked", 0);
                    edit.commit();
                    return true;
                }
            });

            pref = (Preference) findPreference("ALARM_WAKEUP_ACTIVITY_DETECTION_blocked");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong("ALARM_WAKEUP_ACTIVITY_DETECTION_blocked", 0);
                    edit.commit();
                    return true;
                }
            });

            pref = (Preference) findPreference("NlpWakeLock_blocked");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong("NlpWakeLock_blocked", 0);
                    edit.commit();
                    return true;
                }
            });

            pref = (Preference) findPreference("NlpCollectorWakeLock_blocked");
            pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    SharedPreferences.Editor edit = sharedPref.edit();
                    edit.putLong("NlpCollectorWakeLock_blocked", 0);
                    edit.commit();
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
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            //ALARM LOCATOR
            if (key.equals("alarm_locator_enabled") ||
                key.equals("alarm_detection_enabled") ||
                key.equals("wakelock_nlp_enabled") ||
                key.equals("wakelock_collector_enabled"))
            {
                //Handle checking the box
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);
                //Handle enabling/disabling dependent items
                if (key.equals("alarm_locator_enabled")) {
                    enableDependent("alarm_locator_seconds", value);
                    enableDependent("ALARM_WAKEUP_LOCATOR_blocked", value);
                }
                if (key.equals("alarm_detection_enabled")) {
                    enableDependent("alarm_detection_seconds", value);
                    enableDependent("ALARM_WAKEUP_ACTIVITY_DETECTION_blocked", value);
                }
                if (key.equals("wakelock_nlp_enabled")) {
                    enableDependent("wakelock_nlp_seconds", value);
                    enableDependent("NlpWakeLock_blocked", value);
                }
                if (key.equals("wakelock_collector_enabled")) {
                    enableDependent("wakelock_collector_seconds", value);
                    enableDependent("NlpCollectorWakeLock_blocked", value);
                }
            }
            else if (key.equals("alarm_locator_seconds") ||
                    key.equals("alarm_detection_seconds") ||
                    key.equals("wakelock_nlp_seconds") ||
                    key.equals("wakelock_collector_seconds"))
            {
                EditTextPreference pref = (EditTextPreference) findPreference(key);
                String strValue = sharedPreferences.getString(key, "");
                long value = 0;
                try {
                    value = Long.parseLong(strValue);
                } catch (NumberFormatException nfe) {}

                if (value < 1) {
                    Toast.makeText(getActivity(), "Invalid frequency", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isAdded()) {
                    long minutes = value / 60;
                    long seconds = value % 60;
                    pref.setSummary("Every " + minutes + " minutes, " + seconds + " seconds");
                }
            }
            else if (key.equals("ALARM_WAKEUP_LOCATOR_blocked") ||
                    key.equals("ALARM_WAKEUP_ACTIVITY_DETECTION_blocked") ||
                    key.equals("NlpWakeLock_blocked") ||
                    key.equals("NlpCollectorWakeLock_blocked"))
            {
                Preference pref = (Preference) findPreference(key);
                long value = sharedPreferences.getLong(key, 0);
                pref.setSummary("Blocked " + value + " times");
            }
            else if (key.equals("debug_logging"))
            {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);
                pref.setSummary("Currently: " + value);
            }
        }
    }
}