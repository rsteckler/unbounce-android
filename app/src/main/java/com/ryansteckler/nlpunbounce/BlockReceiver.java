package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ryansteckler.nlpunbounce.models.InterimWakelock;
import com.ryansteckler.nlpunbounce.models.WakelockStats;
import com.ryansteckler.nlpunbounce.models.WakelockStatsCollection;

import java.util.HashMap;

public class BlockReceiver extends BroadcastReceiver {

    public BlockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.ryansteckler.nlpunbounce.INCREMENT_BLOCK_COUNT")) {
        }
        else if (action.equals("com.ryansteckler.nlpunbounce.SEND_STATS")) {
            HashMap<String, WakelockStats> stats = (HashMap<String, WakelockStats>)intent.getSerializableExtra("stats");
            //Why do we save from this side of a BlockReceiver?  So the file gets created
            //reliably with the privileges that allow the other activities to read it.
            WakelockStatsCollection collection = WakelockStatsCollection.getInstance();
            collection.populateSerializableStats(stats);
            collection.saveNow(context);
        }
    }
}
