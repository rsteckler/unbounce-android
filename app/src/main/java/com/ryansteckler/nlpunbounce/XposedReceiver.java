package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.InterimWakelock;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.HashMap;

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
            Intent refreshIntent = new Intent(ActivityReceiver.SEND_STATS_ACTION);
            //TODO:  add FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT to the intent to avoid needing to catch
            //      the IllegalStateException.  The flag value changed between 4.3 and 4.4  :/
            refreshIntent.putExtra("stats", UnbounceStatsCollection.getInstance().getSerializableStats(UnbounceStatsCollection.STAT_CURRENT));
            refreshIntent.putExtra("stat_type", UnbounceStatsCollection.STAT_CURRENT);
            refreshIntent.putExtra("running_since", UnbounceStatsCollection.getInstance().getRunningSince());

            try {
                context.sendBroadcast(refreshIntent);
            } catch (IllegalStateException ise) {
                //Ignore.  This is because boot hasn't completed yet.
            }

        }
    }
}
