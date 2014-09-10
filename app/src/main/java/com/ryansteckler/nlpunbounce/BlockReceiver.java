package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ryansteckler.nlpunbounce.models.InterimWakelock;
import com.ryansteckler.nlpunbounce.models.WakelockStatsCollection;

public class BlockReceiver extends BroadcastReceiver {

    public BlockReceiver() {
    }

    public static WakelockStatsCollection mWakelockStatsCollection = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.ryansteckler.nlpunbounce.INCREMENT_BLOCK_COUNT")) {
            String blockName = intent.getStringExtra("name");
            mWakelockStatsCollection.getInstance().incrementWakelockBlock(context, blockName);
        }
        else if (action.equals("com.ryansteckler.nlpunbounce.SEND_STATS")) {
            InterimWakelock curStat = (InterimWakelock)intent.getSerializableExtra("stat");
            mWakelockStatsCollection.getInstance().addInterimWakelock(context, curStat);
        }
    }
}
