package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

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

            SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
            sharedPref.registerOnSharedPreferenceChangeListener(this);
            onSharedPreferenceChanged(sharedPref, "seconds_nlp_wakelock");
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("seconds_nlp_wakelock")) {
                EditTextPreference pref = (EditTextPreference) findPreference(key);
                String value = sharedPreferences.getString(key, "");
                if (value.isEmpty()) {
                    value = "(unchanged)";
                } else if (!value.matches("\\d{1,5}")) {
                    pref.setText("");
                    Toast.makeText(getActivity(), "Invalid wakelock frequency", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isAdded()) {
                    pref.setSummary("Currently: " + value);
                }
            }
        }
    }
}