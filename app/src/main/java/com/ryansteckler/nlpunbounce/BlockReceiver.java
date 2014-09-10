package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ryansteckler.nlpunbounce.models.WakeLockStats;
import com.ryansteckler.nlpunbounce.models.WakeLockStatsCombined;

import java.util.HashMap;

public class BlockReceiver extends BroadcastReceiver {
    
    public BlockReceiver() {
    }

    public static HashMap<String, WakeLockStatsCombined> mWakeLockStats = new HashMap<String, WakeLockStatsCombined>();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("com.ryansteckler.nlpunbounce.INCREMENT_BLOCK_COUNT")) {
            SharedPreferences prefs = context.getSharedPreferences(BlockReceiver.class.getPackage().getName() + "_blockpreferences",  Context.MODE_WORLD_READABLE);

            String blockName = intent.getStringExtra("name") + "_blocked";
            long count = prefs.getLong(blockName, 0);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(blockName, ++count);
            editor.commit();
        }
        else if (action.equals("com.ryansteckler.nlpunbounce.SEND_STATS")) {
            WakeLockStats curStat = (WakeLockStats)intent.getSerializableExtra("stat");
            WakeLockStatsCombined combined = mWakeLockStats.get(curStat.getName());
            if (combined == null)
            {
                combined = new WakeLockStatsCombined();
                combined.setName(curStat.getName());
            }
            combined.addDuration(curStat.getTimeStopped() - curStat.getTimeStarted());
            combined.incrementCount();
            mWakeLockStats.put(curStat.getName(), combined);
        }
    }

}
