package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.BaseStatsWrapper;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.HashMap;

public class ActivityReceiver extends BroadcastReceiver {

    public static final String STATS_REFRESHED_ACTION = "com.ryansteckler.nlpunbounce.STATS_REFRESHED_ACTION";
    public static final String CREATE_FILES_ACTION = "com.ryansteckler.nlpunbounce.CREATE_FILES_ACTION";

    public ActivityReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(CREATE_FILES_ACTION)) {
            UnbounceStatsCollection.getInstance().createFiles(context);
        }

    }
}
