package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.util.HashMap;

public class ActivityReceiver extends BroadcastReceiver {

    public static final String SEND_STATS_ACTION = "com.ryansteckler.nlpunbounce.SEND_STATS";

    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(SEND_STATS_ACTION)) {
            HashMap<String, BaseStats> stats = null;
            try {
                stats = (HashMap<String, BaseStats>) intent.getSerializableExtra("stats");
                for (BaseStats testStat : stats.values()) {
                    if (!(testStat instanceof BaseStats)) {
                        throw new ClassCastException();
                    }
                }
            } catch (RuntimeException rte) {
                //From upgrading the stats classes.  Just reset the stats.
                stats = new HashMap<String, BaseStats>();
            }

            int statType = intent.getIntExtra("stat_type", -1);
            long runningSince = intent.getLongExtra("running_since", -1);
            //Why do we save from this side of a BlockReceiver?  So the file gets created
            //reliably with the privileges that allow the other activities to read it.
            UnbounceStatsCollection collection = UnbounceStatsCollection.getInstance();
            collection.populateSerializableStats(stats, statType, runningSince);
            collection.saveIfNeeded(context);
        }
    }
}
