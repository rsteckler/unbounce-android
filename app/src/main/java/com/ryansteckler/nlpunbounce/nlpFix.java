package com.ryansteckler.nlpunbounce;

/**
 * Created by ryan steckler on 8/18/14.
 */
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;

public class nlpFix implements IXposedHookLoadPackage {

    private static final String TAG = "NlpUnbounce: ";
    private long mLastLocatorAlarm = 0;  // Last alarm attempt
    private long mLastDetectionAlarm = 0;  // Last alarm attempt
    private long mLastNlpWakeLock = 0;  // Last wakelock attempt
    private long mLastNlpCollectorWakeLock = 0;  // Last wakelock attempt

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals("android")) {

            final XSharedPreferences prefs = new XSharedPreferences(nlpFix.class.getPackage().getName());
            prefs.reload();

            hookAlarms(lpparam, prefs);
            hookWakeLocks(lpparam, prefs);

        }
    }

    private void hookAlarms(LoadPackageParam lpparam, XSharedPreferences prefs) {
        boolean alarmsHooked = false;
        try {
            //Try for alarm hooks for API levels 19-20
            try19To20AlarmHook(lpparam, prefs);
            alarmsHooked = true;
        } catch (NoSuchMethodError nsme) {
            //No problem.
        }

        try {
            //Try for alarm hooks for API levels 15-18.
            try15To18AlarmHook(lpparam, prefs);
            alarmsHooked = true;
        } catch (NoSuchMethodError nsme) {
            //No problem.
        }

        if (!alarmsHooked) {
            XposedBridge.log(TAG + "Unsupported Android version trying to hook Alarms.");
        }
    }

    private void hookWakeLocks(LoadPackageParam lpparam, XSharedPreferences prefs) {
        boolean wakeLocksHooked = false;
        try {
            //Try for wakelock hooks for API levels 17-20
            try17To20WakeLockHook(lpparam, prefs);
            wakeLocksHooked = true;
        } catch (NoSuchMethodError nsme) {
            //No problem.
        }

        try {
            //Try for wakelock hooks for API levels 15-16
            try15To16WakeLockHook(lpparam, prefs);
            wakeLocksHooked = true;
        } catch (NoSuchMethodError nsme) {
            //No problem.
        }

        if (!wakeLocksHooked) {
            XposedBridge.log(TAG + "Unsupported Android version trying to hook WakeLocks.");
        }
    }

    private void try17To20WakeLockHook(LoadPackageParam lpparam, final XSharedPreferences prefs) {
        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String)param.args[2];
                handleWakeLock(param, prefs, wakeLockName);
            }
        });
    }

    private void try15To16WakeLockHook(LoadPackageParam lpparam, final XSharedPreferences prefs) {
        findAndHookMethod("com.android.server.PowerManagerService", lpparam.classLoader, "acquireWakeLockLocked", int.class, android.os.IBinder.class, int.class, int.class, String.class, android.os.WorkSource.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String)param.args[4];
                handleWakeLock(param, prefs, wakeLockName);
            }
        });
    }

    private void try19To20AlarmHook(LoadPackageParam lpparam, final XSharedPreferences prefs) {
        findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, long.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<Object> triggers = (ArrayList<Object>)param.args[0];
                handleAlarm(prefs, triggers);
            }
        });
    }

    private void try15To18AlarmHook(LoadPackageParam lpparam, final XSharedPreferences prefs) {
        findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, ArrayList.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<Object> triggers = (ArrayList<Object>)param.args[1];
                handleAlarm(prefs, triggers);
            }
        });
    }

    private void handleWakeLock(XC_MethodHook.MethodHookParam param, XSharedPreferences prefs, String wakeLockName) {
        prefs.reload();

        if (wakeLockName.equals("NlpCollectorWakeLock"))
        {
            int collectorMaxFreq = tryParseInt(prefs.getString("seconds_wake_collector", "240"));
            collectorMaxFreq *= 1000; //convert to ms

            if (collectorMaxFreq != 0) {
                //Debounce this to our minimum interval.
                final long now = SystemClock.elapsedRealtime();
                long timeSinceLastWakeLock = now - mLastNlpCollectorWakeLock;

                if (timeSinceLastWakeLock < collectorMaxFreq) {
                    //Not enough time has passed since the last wakelock
                    //XposedBridge.log(TAG + "Preventing NlpCollectorWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.  Frequency allowed: " + collectorMaxFreq);

                } else {
                    //Allow the wakelock
                    XposedBridge.log(TAG + "Allowing NlpCollectorWakeLock." + collectorMaxFreq);
                    mLastNlpCollectorWakeLock = now;
                }
            }
        }
        else if (wakeLockName.equals("NlpWakeLock"))
        {
            int nlpMaxFreq = tryParseInt(prefs.getString("seconds_wake_nlp", "240"));
            nlpMaxFreq *= 1000; //convert to ms

            if (nlpMaxFreq != 0) {

                //Debounce this to our minimum interval.
                final long now = SystemClock.elapsedRealtime();
                long timeSinceLastWakeLock = now - mLastNlpWakeLock;

                if (timeSinceLastWakeLock < nlpMaxFreq) {
                    //Not enough time has passed since the last wakelock
                    //XposedBridge.log(TAG + "Preventing NlpWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.  Frequency allowed: " + nlpMaxFreq);

                    param.setResult(null);
                } else {
                    //Allow the wakelock
                    XposedBridge.log(TAG + "Allowing NlpWakeLock." + nlpMaxFreq);
                    mLastNlpWakeLock = now;
                }
            }
        }
    }

    private void handleAlarm(XSharedPreferences prefs, ArrayList<Object> triggers) {
        prefs.reload();

        for (int j = triggers.size() - 1; j >= 0; j--) {
            Object curAlarm = triggers.get(j);

            PendingIntent pi = (PendingIntent) XposedHelpers.getObjectField(curAlarm, "operation");
            Intent intent = (Intent) XposedHelpers.callMethod(pi, "getIntent");
            if(intent==null || intent.getAction()==null){
                //TODO: Why does the system have alarms with null intents?
                continue;
            }

            if (intent.getAction().equals("com.google.android.gms.nlp.ALARM_WAKEUP_LOCATOR")) {
                int locatorMaxFreq = tryParseInt(prefs.getString("seconds_locator", "240"));
                locatorMaxFreq *= 1000; //convert to ms

                if (locatorMaxFreq != 0) {
                    //Debounce this to our minimum interval.
                    final long now = SystemClock.elapsedRealtime();
                    long timeSinceLastLocator = now - mLastLocatorAlarm;

                    if (timeSinceLastLocator < locatorMaxFreq) {
                        //Not enough time has passed since the last wakelock
                        //XposedBridge.log("NlpUnbounce: Preventing ALARM_WAKEUP_LOCATOR.  Last granted: " + timeSinceLastLocator + " milliseconds ago.  Frequency allowed: " + locatorMaxFreq);

                        triggers.remove(j);
                    } else {
                        //Allow the wakelock
                        XposedBridge.log(TAG + "Allowing ALARM_WAKEUP_LOCATOR." + locatorMaxFreq);
                        mLastLocatorAlarm = now;
                    }
                }
            }
            if (intent.getAction().equals("com.google.android.gms.nlp.ALARM_WAKEUP_ACTIVITY_DETECTION")) {
                int detectionMaxFreq = tryParseInt(prefs.getString("seconds_detection", "240"));
                detectionMaxFreq *= 1000; //convert to ms

                if (detectionMaxFreq != 0) {
                    //Debounce this to our minimum interval.
                    final long now = SystemClock.elapsedRealtime();
                    long timeSinceLastDetection = now - mLastDetectionAlarm;

                    if (timeSinceLastDetection < detectionMaxFreq) {
                        //Not enough time has passed since the last wakelock
                        //XposedBridge.log("NlpUnbounce: Preventing ALARM_WAKEUP_ACTIVITY_DETECTION.  Last granted: " + timeSinceLastDetection + " milliseconds ago.  Frequency allowed: " + detectionMaxFreq);

                        triggers.remove(j);
                    } else {
                        //Allow the wakelock
                        XposedBridge.log(TAG + "Allowing ALARM_WAKEUP_ACTIVITY_DETECTION." + detectionMaxFreq);
                        //mLastLocatorAlarm = now;
                        mLastDetectionAlarm = now;
                    }
                }
            }
        }
    }


    private static int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }
}


