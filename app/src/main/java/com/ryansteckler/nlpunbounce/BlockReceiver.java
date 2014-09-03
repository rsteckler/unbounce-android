package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import de.robv.android.xposed.XposedBridge;

public class BlockReceiver extends BroadcastReceiver {
    public BlockReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = context.getSharedPreferences(BlockReceiver.class.getPackage().getName() + "_preferences",  Context.MODE_WORLD_READABLE);

        String blockName = intent.getStringExtra("name") + "_blocked";
        long count = prefs.getLong(blockName, 0);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(blockName, ++count);
        editor.commit();
    }
}
