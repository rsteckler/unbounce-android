package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.ryansteckler.inappbilling.IabHelper;


public class SettingsActivity extends Activity {

    private static final String TAG = "NlpUnbounceSettings";

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

        }

        private void enableDependent(String control, boolean enable)
        {
            Preference controlToAffect = (Preference)findPreference(control);
            controlToAffect.setEnabled(enable);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            if (key.equals("debug_logging"))
            {
                CheckBoxPreference pref = (CheckBoxPreference) findPreference(key);
                boolean value = sharedPreferences.getBoolean(key, false);
                pref.setChecked(value);
            }
        }
    }
}