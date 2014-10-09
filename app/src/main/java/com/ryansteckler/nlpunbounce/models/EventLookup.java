package com.ryansteckler.nlpunbounce.models;

import android.content.Context;

import com.ryansteckler.nlpunbounce.R;

/**
 * Created by rsteckler on 9/28/14.
 */
public class EventLookup {

    public static final int UNSAFE = 0;
    public static final int UNKNOWN = 1;
    public static final int SAFE = 2;

    public static String getDescription(Context context, String eventName) {
        String lowerTrimmed = eventName.toLowerCase();
        lowerTrimmed.replace("[", "");
        lowerTrimmed.replace("]", "");
        lowerTrimmed.replace(":", "");
        lowerTrimmed.replace("-", "");
        lowerTrimmed.replace(".", "");

        //Unknown
        String toReturn = context.getResources().getString(R.string.desc_unknown);

        String resourceName = "desc_" + lowerTrimmed;
        int resId = context.getResources().getIdentifier(resourceName, "string", "com.ryansteckler.nlpunbounce");
        if (resId != 0) {
            toReturn = context.getString(resId);
        }
        return toReturn;
    }

    public static int isSafe(String eventName) {
        String lower = eventName.toLowerCase();
        int toReturn = UNKNOWN;

        //Wakelocks - safe
        if (lower.equals("nlpwakelock") ||
                lower.equals("syncloopwakelock") ||
                lower.equals("icing") ||
                lower.equals("startingalertservice") ||
                lower.equals("audiomix") ||
                lower.equals("locationmanagerservice") ||
                lower.equals("nfcservice:mroutingwakelock") ||
                lower.equals("wakefulintentservice[gcoreulr-locationreportingservice]") ||
                lower.equals("vzwgpslocationprovider") ||
                lower.equals("rilj") ||
                lower.equals("*net_scheduler*") ||
                lower.equals("gcoreflp") ||
                lower.equals("com.commonsware.cwac.wakeful.wakefullintentservice") ||
                lower.equals("wakeful statemachine: geofencerstatemachine") ||
                lower.equals("fingerprint_scanner_local") ||
                lower.equals("gpslocationprovider") ||
                lower.equals("cdmainboundsmshandler") ||
                lower.equals("wake:com.google.android.gms/.config.configfetchservice") ||
                lower.equals("networkstats") ||
                lower.equals("ulrdispatchingservice") ||
                lower.equals("fingerprint_scanner_static") ||
                lower.equals("nlpcollectorwakelock")) {
            toReturn = SAFE;
        }
        //Wakelocks - unsafe
        else if (lower.equals("alarmmanager") ||
                lower.equals("e") ||
                lower.equals("m") ||
                lower.equals("audiooffload") ||
                lower.equals("activitymanager-launcher") ||
                lower.equals("windowmanager") ||
                lower.equals("audioin") ||
                lower.equals("hangouts_rtcs") ||
                lower.equals("gcm_conn") ||
                lower.equals("google_c2dm") ||
                lower.equals("timedeventqueue"))
        {
            toReturn = UNSAFE;
        }
        //Alarms - safe
        else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_locator") ||
                lower.equals("com.android.internal.telephony.data-stall") ||
                lower.equals("android.content.syncmanager.sync_alarm") ||
                lower.equals("android.app.backup.intent.run") ||
                lower.equals("com.sonymobile.storagechecker.intent.action.alarm_expired") ||
                lower.equals("com.google.android.intent.gcm_reconnect") ||
                lower.equals("com.google.android.gms.gcm.nts.action_check_queue") ||
                lower.equals("com.whatsapp.messaging.messageservice.logout_action") ||
                lower.equals("com.devexpert.weatheradfree.pfx.wakeup") ||
                lower.equals("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {
            toReturn = SAFE;
        }
        //Alarms - unsafe
        else if (lower.equals("android.intent.action.time_tick") ||
                lower.equals("com.google.android.intent.action.mcs_heartbeat") ||
                lower.equals("com.google.android.apps.hangouts.update_notification") ||
                lower.equals("android.appwidget.action.appwidget_update")) {
            toReturn = UNSAFE;
        }
        return toReturn;
    }

    public static boolean isFree(String eventName) {
        String lower = eventName.toLowerCase();
        boolean toReturn = false;

        if (lower.equals("nlpwakelock")) {
            toReturn = true;
        } else if (lower.equals("nlpcollectorwakelock")) {
            toReturn = true;
        } else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_locator")) {
            toReturn = true;
        } else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {
            toReturn = true;
        }
        return toReturn;
    }


}
