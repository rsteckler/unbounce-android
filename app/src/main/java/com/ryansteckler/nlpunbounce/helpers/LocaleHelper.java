package com.ryansteckler.nlpunbounce.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

/**
 * Created by rsteckler on 10/13/14.
 */
public class LocaleHelper {
    public static int sForceEnglish = -1;
    private static String sLocale = null;

    /**
     * Set the locale of the Activity, and restart it by creating a new Activity of the same type.
     */

    private LocaleHelper() {
    }

    public static void forceEnglish(Activity activity) {
        sForceEnglish = 1;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void revertToSystem(Activity activity) {
        sForceEnglish = 0;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    /**
     * Set the theme of the activity, according to the configuration.
     */
    public static int onActivityCreateSetLocale(Activity activity) {
        if (sForceEnglish == -1) {
            // Load from prefs
            SharedPreferences prefs = activity.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            sForceEnglish = prefs.getBoolean("force_english", false) ? 1 : 0;
        }

        if (sForceEnglish == 1) {
            Locale locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        } else {
            Locale defaultLocale = activity.getResources().getSystem().getConfiguration().locale;
            Locale.setDefault(defaultLocale);
            Configuration config = new Configuration();
            config.locale = defaultLocale;
            activity.getBaseContext().getResources().updateConfiguration(config, activity.getBaseContext().getResources().getDisplayMetrics());
        }
        return sForceEnglish;
    }

    public static int onActivityResumeVerifyLocale(Activity activity, int curForceEnglish) {
        if (curForceEnglish != sForceEnglish) {
            if (sForceEnglish == 1) {
                forceEnglish(activity);
            } else {
                revertToSystem(activity);
            }
        }

        return sForceEnglish;
    }

    public static int getForceEnglish() {
        return sForceEnglish;
    }

    public static String getFormattedTime(java.lang.Object... paramTimes) {
        StringBuilder sbuf = new StringBuilder();
        int offset = 0;
        if (5 == paramTimes.length) {
            offset = 2;
        } else if (4 == paramTimes.length) {
            offset = 1;
        }
        for (int i = 0; i < paramTimes.length; i++) {
            if (i < (paramTimes.length - offset)) {
                if ((long) paramTimes[i] > 0) {
                    sbuf.append(String.format("%02d", paramTimes[i]));
                    sbuf.append(":");
                } else {
                    continue;
                }
            } else {
                sbuf.append(String.format("%02d", paramTimes[i]));
                sbuf.append(":");
            }
        }
        sbuf.deleteCharAt(sbuf.lastIndexOf(":"));
        return sbuf.toString();
    }
}

