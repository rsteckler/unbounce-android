package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

public class ActivityReceiver extends BroadcastReceiver {

    public static final String STATS_REFRESHED_ACTION = "com.ryansteckler.nlpunbounce.STATS_REFRESHED_ACTION";
    public static final String CREATE_FILES_ACTION = "com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION";
    private static final String RESET_FILES_ACTION = "com.ryansteckler.nlpunbounce.RESET_FILES_ACTION";
    public static final String PUSH_NETWORK_STATS = "com.ryansteckler.nlpunbounce.PUSH_NETWORK_STATS";

    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        switch (action) {
            case CREATE_FILES_ACTION:
                UnbounceStatsCollection.getInstance().createFiles(context);
                break;
            case RESET_FILES_ACTION:
                UnbounceStatsCollection.getInstance().recreateFiles(context);
                break;
            case PUSH_NETWORK_STATS:
                UnbounceStatsCollection.getInstance().pushStatsToNetworkInternal(context);
                break;
        }

    }
}
