package com.ryansteckler.nlpunbounce.tasker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ryansteckler.nlpunbounce.XposedReceiver;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

public class TaskerReceiver extends BroadcastReceiver {
    public TaskerReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Unpack the bundle.
        if (intent != null) {
            Bundle savedBundle = intent.getBundleExtra(TaskerActivity.EXTRA_BUNDLE);
            if (savedBundle != null) {
                String type = savedBundle.getString(TaskerActivity.BUNDLE_TYPE);
                if (type.equals("reset")) {
                    Intent resetIntent = new Intent(XposedReceiver.RESET_ACTION);
                    resetIntent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);
                    try {
                        context.sendBroadcast(resetIntent);
                    } catch (IllegalStateException ise) {

                    }

                } else {
                    long seconds = savedBundle.getLong(TaskerActivity.BUNDLE_SECONDS);
                    boolean enabled = savedBundle.getBoolean(TaskerActivity.BUNDLE_ENABLED);
                    String name = savedBundle.getString(TaskerActivity.BUNDLE_NAME);

                    //set the prefs appropriately.
                    SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
                    String enabledName = type + "_" + name + "_enabled";
                    String secondsName = type + "_" + name + "_seconds";
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(enabledName, enabled);
                    editor.putLong(secondsName, seconds);
                    editor.apply();
                }
            }

        }
    }
}
