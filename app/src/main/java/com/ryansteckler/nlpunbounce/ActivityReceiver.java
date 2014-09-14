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
            //Why do we save from this side of a BlockReceiver?  So the file gets created
            //reliably with the privileges that allow the other activities to read it.
            UnbounceStatsCollection collection = UnbounceStatsCollection.getInstance();
            collection.populateSerializableStats(stats);
            collection.saveNow(context);
        }
    }
}
