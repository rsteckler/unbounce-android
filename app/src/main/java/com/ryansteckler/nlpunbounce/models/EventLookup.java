package com.ryansteckler.nlpunbounce.models;

/**
 * Created by rsteckler on 9/28/14.
 */
public class EventLookup {

    public static final int UNSAFE = 0;
    public static final int UNKNOWN = 1;
    public static final int SAFE = 2;

    public static String getDescription(String eventName) {
        String lower = eventName.toLowerCase();

        //Unknown
        String toReturn = "We don't have any information about this event, yet.  This may not be safe to Unbounce.  Only " +
                "do so if you know what you're doing and you know how to disable Xposed at boot.  We're working hard to collect information about every" +
                " major wakelock and alarm.  Please be patient while we collect this information.";

        //Known wakelocks
        if (lower.equals("nlpwakelock")) {
            toReturn = "NlpWakeLock is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                    "combination of cell towers and WiFi.  Once it has your location, it stores it locally so other apps, like Google Now, can access your " +
                    "location without using GPS or getting a new fix.  Recommended settings are between 180 and 600 seconds.";
        } else if (lower.equals("nlpcollectorwakelock")) {
            toReturn = "NlpCollectorWakeLock is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                    "combination of cell towers and wifi.  Once it has your location, it sends it back to Google so they can expand their database " +
                    "of WiFi locations.  Recommended settings are between 180 and 600 seconds.";
        } else if (lower.equals("alarmmanager")) {
            toReturn = "This provides access to the system alarm services. When an alarm goes off, the Intent that had been registered for it is broadcast by the system, automatically starting the target application if it is not already running. Registered alarms are retained while the device is asleep (and can optionally wake the device up if they go off during that time), but will be cleared if it is turned off and rebooted.";
        } else if (lower.equals("audiooffload")) {
            toReturn = "Audio playback when your screen is turned off.  If you disable this, you won't have background audio or music able to play when your screen is off.";
        } else if (lower.equals("activitymanager-launcher")) {
            toReturn = "Interact with the overall activities running in the system. If you Unbounce this, nothing will work well.";
        } else if (lower.equals("windowmanager")) {
            toReturn = "This is an Android System level WakeLock.  The interface that apps use to talk to the window manager - meaning, applications will require a WindowManager lock everytime they need to be shown on the screen.";
        } else if (lower.equals("rilj")) {
            toReturn = "RILJ keeps your device awake while processing phone actions, such as phone calls and cell tower communication.";
        } else if (lower.equals("syncloopwakelock")) {
            toReturn = "This is the WakeLock used by Android SyncManager to Sync accounts like Google+, Twitter, Linkedin, Gmail etc. The higher the unbouncing, the longer the amount of time until your accounts will get synced again.";
        } else if (lower.equals("icing")) {
            toReturn = "ICING is a Google Services WakeLock.  Currently looking for more details.";
        } else if (lower.equals("startingalertservice")) {
            toReturn = "This WakeLock can usually be associated with Calendar, although may effect other apps such as messaging. If your Calendar has notifications / alerts it can hold this WakeLock.";
        } else if (lower.equals("audiomix")) {
            toReturn = "This WakeLock handles Touch Sounds and Alert Sounds among others. It has plagued some devices even with Touch Sounds turned off.";
        } else if (lower.equals("locationmanagerservice")) {
            toReturn = "As the name implies. This is not typically a high battery drainer for some it might held a big wakelock.";
        } else if (lower.equals("audioin")) {
            toReturn = "This wakelock is used to hold the device awake listening for Google Search/Hotword Detection. \"Okay, Google\"";
        } else if (lower.equals("nfcservice:mroutingwakelock")) {
            toReturn = "Related to NFC service routing detected tags to the appropriate application.";
        } else if (lower.equals("wakefulintentservice[gcoreulr-locationreportingservice]")) {
            toReturn = "Used by Google Location Services to report your current location";
        } else if (lower.equals("vzwgpslocationprovider")) {
            toReturn = "Verizon specific location service used for cell tower statistics.";
        } else if (lower.equals("hangouts_rtcs")) {
            toReturn = "Google Hangouts.  If you Unbounce this, Hangouts will no longer work.";
        } else if (lower.equals("gcm_conn")) {
            toReturn = "Google Cloud Messaging. Sends lightweight messages to your apps. Responsible for sending push notifications to your phone, for all apps.";
        } else if (lower.equals("google_c2dm")) {
            toReturn = "Cloud to Device Messaging (C2DM) is a service that helps developers send data to their apps on devices. The service provides a mechanism that servers can use to tell mobile applications to contact the server to fetch updated application or user data. The C2DM service handles all aspects of queueing of messages & delivery to the target application running on target devices. This service was deprecated since 2012 but there are still apps that are using this service instead of the newly & improved GCM.";
        } else if (lower.equals("timedeventqueue")) {
            toReturn = "This is a core part of Android that is best left alone. It queues up all incoming events.";
        }
        //Known Alarms
        else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_locator")) {
            toReturn = "This alarm is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                    "combination of cell towers and WiFi.  Once it has your location, it stores it locally so other apps, like Google Now, can access your " +
                    "location without using GPS or getting a new fix.  Recommended settings are between 180 and 600 seconds.";
        } else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {
            toReturn = "This alarm is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                    "combination of cell towers and wifi.  Once it has your location, it sends it back to Google so they can expand their database " +
                    "of WiFi locations.  Recommended settings are between 180 and 600 seconds.";
        } else if (lower.equals("com.google.android.apps.hangouts.update_notification")) {
            toReturn = "The listener which updates your Google Hangouts notifications.";
        } else if (lower.equals("com.android.internal.telephony.data-stall")) {
            toReturn = "Wakelock from com.android.phone (Phone app) when using Data through Cellular towers. Using WiFi will not trigger this alarm. Usually it gets through the roof while using data, especially on a mediocre connection.";
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
                lower.equals("audioin") ||
                lower.equals("nfcservice:mroutingwakelock") ||
                lower.equals("wakefulintentservice[gcoreulr-locationreportingservice]") ||
                lower.equals("vzwgpslocationprovider") ||
                lower.equals("nlpcollectorwakelock")) {
            toReturn = SAFE;
        }
        //Wakelocks - unsafe
        else if (lower.equals("alarmmanager") ||
                lower.equals("rilj") ||
                lower.equals("audiooffload") ||
                lower.equals("activitymanager-launcher") ||
                lower.equals("windowmanager") ||
                lower.equals("hangouts_rtcs") ||
                lower.equals("gcm_conn") ||
                lower.equals("google_c2dm") ||
                lower.equals("timedeventqueue"))
        {
            toReturn = UNSAFE;
        }
        //Alarms - safe
        else if (lower.equals("com.google.android.gms.nlp.alarm_wakeup_locator") ||
                lower.equals("com.google.android.apps.hangouts.update_notification") ||
                lower.equals("com.android.internal.telephony.data-stall") ||
                lower.equals("android.content.syncmanager.sync_alarm") ||
                lower.equals("android.app.backup.intent.run") ||
                lower.equals("com.google.android.gms.gcm.nts.action_check_queue") ||
                lower.equals("com.whatsapp.messaging.messageservice.logout_action") ||
                lower.equals("com.google.android.gms.nlp.alarm_wakeup_activity_detection")) {
            toReturn = SAFE;
        }
        //Alarms - unsafe
        else if (lower.equals("android.intent.action.time_tick") ||
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
