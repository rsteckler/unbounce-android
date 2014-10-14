package com.ryansteckler.nlpunbounce.helpers;

import android.content.SharedPreferences;

/**
 * Created by rsteckler on 9/28/14.
 */
public class SettingsHelper {
    public static void resetToDefaults(SharedPreferences prefs)
    {
        final SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.putBoolean("first_launch", false);
        editor.putBoolean("wakelock_NlpWakeLock_enabled", true);
        editor.putBoolean("wakelock_NlpCollectorWakeLock_enabled", true);
        editor.putBoolean("alarm_com.google.android.gms.nlp.ALARM_WAKEUP_LOCATOR_enabled", true);
        editor.putBoolean("alarm_com.google.android.gms.nlp.ALARM_WAKEUP_ACTIVITY_DETECTION_enabled", true);
        editor.apply();

    }
}
