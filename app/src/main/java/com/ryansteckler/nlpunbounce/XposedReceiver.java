package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

public class XposedReceiver extends BroadcastReceiver {

    public XposedReceiver() {
    }

    public final static String RESET_ACTION = "com.ryansteckler.nlpunbounce.RESET_STATS";
    public final static String REFRESH_ACTION = "com.ryansteckler.nlpunbounce.REFRESH_STATS";
    public final static String STAT_NAME = "stat_name";
    public final static String STAT_TYPE = "stat_type";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(RESET_ACTION)) {
            String statName = intent.getStringExtra(STAT_NAME);
            int statType = intent.getIntExtra(STAT_TYPE, -1);
            UnbounceStatsCollection collection = UnbounceStatsCollection.getInstance();
            if (statName == null) {
                collection.resetLocalStats(statType);
            } else {
                collection.resetLocalStats(statName);
            }
        } else if (action.equals(REFRESH_ACTION)) {
            if (!UnbounceStatsCollection.getInstance().saveNow(context)) {
                //Request the activity to create the files.
                Intent createIntent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);
                try {
                    context.sendBroadcast(createIntent);
                } catch (IllegalStateException ise) {
                }
            }

            Intent refreshIntent = new Intent(ActivityReceiver.STATS_REFRESHED_ACTION);
            try {
                context.sendBroadcast(refreshIntent);
            } catch (IllegalStateException ise) {
                //Ignore.  This is because boot hasn't completed yet.
            }

        }
    }
}
