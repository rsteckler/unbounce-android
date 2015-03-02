package com.ryansteckler.nlpunbounce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ryansteckler.nlpunbounce.helpers.RootHelper;

/**
 * Created by rsteckler on 1/2/15.
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Amplify", "Received boot message.");
        amplifyBoot(context);
        Log.d("Amplify", "Finished boot tasks.");
    }

    private void amplifyBoot(Context context) {
        context.startService(new Intent(context, SELinuxService.class));
    }
}
