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

    private EventLookup(){}

    public static String getDescription(Context context, String eventName) {
        String lowerTrimmed = eventName.toLowerCase();
        lowerTrimmed= lowerTrimmed.replace("[", "");
        lowerTrimmed =lowerTrimmed.replace("]", "");
        lowerTrimmed =lowerTrimmed.replace(":", "");
        lowerTrimmed =lowerTrimmed.replace("-", "");
        lowerTrimmed =lowerTrimmed.replace(".", "");
        lowerTrimmed =lowerTrimmed.replace("*", "");

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
        int toReturn = UNKNOWN;

        //Wakelocks - safe
        if (eventName.equalsIgnoreCase("nlpwakelock") ||
                eventName.equalsIgnoreCase("audioin") ||
                eventName.equalsIgnoreCase("SyncLoopWakelock") ||
                eventName.equalsIgnoreCase("ICING") ||
                eventName.equalsIgnoreCase("StartingAlertService") ||
                eventName.equalsIgnoreCase("GCoreFlp") ||
                eventName.equalsIgnoreCase("*net_scheduler*") ||
                eventName.equalsIgnoreCase("NetworkStats") ||
                eventName.equalsIgnoreCase("UlrDispatchingService") ||
                eventName.equalsIgnoreCase("fingerprint_scanner_static") ||
                eventName.equalsIgnoreCase("fingerprint_scanner_local") ||
                eventName.equalsIgnoreCase("GPSLocationProvider") ||
                eventName.equalsIgnoreCase("CDMAInboundSMSHandler") ||
                eventName.equalsIgnoreCase("wake:com.google.android.gms/.config.ConfigFetchService") ||
                eventName.equalsIgnoreCase("LocationManagerService") ||
                eventName.equalsIgnoreCase("NfcService:mRoutingWakeLock") ||
                eventName.equalsIgnoreCase("WakefulIntentService[GCoreUlr-LocationReportingService]") ||
                eventName.equalsIgnoreCase("NlpWakelockDetector") ||
                eventName.equalsIgnoreCase("VZWGPSLocationProvider") ||
                eventName.equalsIgnoreCase("com.commonsware.cwac.wakeful.WakefullIntentService") ||
                eventName.equalsIgnoreCase("Wakeful StateMachine: GeofencerStateMachine") ||
                eventName.equalsIgnoreCase("audioin") ||
                eventName.equalsIgnoreCase("*sync*/com.google.android.apps.bigtop.provider.bigtopprovider/com.google/accountname")) {
            toReturn = SAFE;
        }
        //Wakelocks - unsafe
        else if (eventName.equalsIgnoreCase("TimedEventQueue") ||
                eventName.equalsIgnoreCase("GCM_CONN") ||
                eventName.equalsIgnoreCase("GOOGLE_C2DM") ||
                eventName.equalsIgnoreCase("ActivityManager-Launcher") ||
                eventName.equalsIgnoreCase("AlarmManager") ||
                eventName.equalsIgnoreCase("AudioOffload") ||
                eventName.equalsIgnoreCase("WindowManager") ||
                eventName.equalsIgnoreCase("hangouts_rtcs") ||
                eventName.equalsIgnoreCase("e") ||
                eventName.equalsIgnoreCase("m") ||
                eventName.equalsIgnoreCase("AudioMix") ||
                eventName.equalsIgnoreCase("com.oasisfeng.greenify.CLEAN_NOW") ||
                eventName.equalsIgnoreCase("rilj"))
        {
            toReturn = UNSAFE;
        }
        //Alarms - safe
        else if (eventName.equalsIgnoreCase("com.google.android.gms.nlp.alarm_wakeup_locator") ||
                eventName.equalsIgnoreCase("com.sonymobile.storagechecker.intent.action.ALARM_ EXPIRED") ||
                eventName.equalsIgnoreCase("com.google.android.intent.GCM_RECONNECT") ||
                eventName.equalsIgnoreCase("com.android.internal.telephony.data-stall") ||
                eventName.equalsIgnoreCase("Android.app.backup.intent.RUN") ||
                eventName.equalsIgnoreCase("com.google.android.gms.gcm.nts.ACTION_CHECK_QUEUE") ||
                eventName.equalsIgnoreCase("com.whatsapp.messaging.MessageService.LOGOUT_ACTION") ||
                eventName.equalsIgnoreCase("com.devexpert.weatheradfree.pfx.WAKEUP") ||
                eventName.equalsIgnoreCase("com.android.server.UPDATE_TWILIGHT_STATE") ||
                eventName.equalsIgnoreCase("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {
            toReturn = SAFE;
        }
        //Alarms - unsafe
        else if (eventName.equalsIgnoreCase("android.intent.action.time_tick") ||
                eventName.equalsIgnoreCase("com.google.android.apps.hangouts.UPDATE_NOTIFICATION") ||
                eventName.equalsIgnoreCase("com.google.android.intent.action.MCS_HEARTBEAT") ||
                eventName.equalsIgnoreCase("Android.content.SyncManager.SYNC_ALARM") ||
                eventName.equalsIgnoreCase("com.android.server.IdleMaintenanceService.action.UPDATE_IDLE_MAINTENANCE_STATE") ||
                eventName.equalsIgnoreCase("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD") ||
                eventName.equalsIgnoreCase("au.com.weatherzone.android.weatherzoneplus.action.update_clock") ||
                eventName.equalsIgnoreCase("android.appwidget.action.appwidget_update")) {
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
