package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.HashMap;

public class ActivityReceiver extends BroadcastReceiver {

    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.ryansteckler.nlpunbounce.INCREMENT_BLOCK_COUNT")) {
        }
        else if (action.equals("com.ryansteckler.nlpunbounce.SEND_STATS")) {
            HashMap<String, BaseStats> stats = (HashMap<String, BaseStats>)intent.getSerializableExtra("stats");
            int statType = intent.getIntExtra("stat_type", -1);
            long runningSince = intent.getLongExtra("running_since", -1);
            //Why do we save from this side of a BlockReceiver?  So the file gets created
            //reliably with the privileges that allow the other activities to read it.
            UnbounceStatsCollection collection = UnbounceStatsCollection.getInstance();
            collection.populateSerializableStats(stats, statType, runningSince);
            collection.saveIfNeeded(context);
        }
        else if (action.equals("com.ryansteckler.nlpunbounce.PUSH_STATS")) {
            HashMap<String, BaseStats> stats = (HashMap<String, BaseStats>)intent.getSerializableExtra("stats");
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
