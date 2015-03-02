package com.ryansteckler.nlpunbounce;

import android.app.IntentService;
import android.content.Intent;

import com.ryansteckler.nlpunbounce.helpers.RootHelper;

/**
 * Created by rsteckler on 3/2/15.
 */
public class SELinuxService extends IntentService {

    public SELinuxService() {
        super("SeLinuxService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public SELinuxService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        RootHelper.handleSELinux();
    }
}
