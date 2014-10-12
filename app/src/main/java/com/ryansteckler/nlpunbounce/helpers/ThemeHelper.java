package com.ryansteckler.nlpunbounce.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ryansteckler.nlpunbounce.R;

/**
 * Created by rsteckler on 10/11/14.
 */
public class ThemeHelper {

    private static int sTheme = -1;
    public final static int THEME_DEFAULT = 0;
    public final static int THEME_DARK = 1;
    /**
     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
     */
    public static void changeToTheme(Activity activity, int theme)
    {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }
    /** Set the theme of the activity, according to the configuration. */
    public static int onActivityCreateSetTheme(Activity activity)
    {
        if (sTheme == -1) {
            // Load from prefs
            SharedPreferences prefs = activity.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            sTheme = prefs.getString("theme", "default").equals("default") ? THEME_DEFAULT : THEME_DARK;
        }

        switch (sTheme)
        {
            default:
            case THEME_DEFAULT:
                activity.setTheme(R.style.UnbounceThemeLight);
                break;
            case THEME_DARK:
                activity.setTheme(R.style.UnbounceThemeDark);
                break;
        }
        return sTheme;
    }
    public static int onActivityResumeVerifyTheme(Activity activity, int curTheme)
    {
        if (curTheme != sTheme) {
            changeToTheme(activity, sTheme);
        }

        return sTheme;
    }

    public static int getTheme() {
        return sTheme;
    }

}
