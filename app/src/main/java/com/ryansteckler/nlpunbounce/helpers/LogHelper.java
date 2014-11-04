package com.ryansteckler.nlpunbounce.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by rsteckler on 11/1/14.
 */
public class LogHelper {
    private static String TAG = "Amplify";

    public static void debugLog(Context c, String log) {
        if (getLogLevel(c).equals("verbose")) {
            Log.d(TAG, log);
        }
    }

    public static void defaultLog(Context c, String log) {
        String logLevel = getLogLevel(c);
        if (logLevel.equals("default") || logLevel.equals("verbose")) {
            Log.d(TAG,log);
        }
    }

    private static String getLogLevel(Context c) {
        SharedPreferences prefs = c.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        return prefs.getString("logging_level", "default");
    }
}
