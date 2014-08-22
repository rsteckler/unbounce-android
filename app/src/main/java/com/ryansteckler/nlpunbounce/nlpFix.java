package com.ryansteckler.nlpunbounce;

/**
 * Created by ryan steckler on 8/18/14.
 */
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Parcel;
import android.os.SystemClock;
import android.os.WorkSource;

import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;


public class nlpFix implements IXposedHookLoadPackage {

    private long mLastLocatorAlarm = 0;  // Last wakelock attempt
    private long mLastDetectionAlarm = 0;  // Last wakelock attempt

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals("android")) {
            findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, long.class, long.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ArrayList<Object> alarmList = (ArrayList<Object>) param.args[0];

                    for (int j = alarmList.size() - 1; j >= 0; j--) {
                        Object curAlarm = alarmList.get(j);

                        PendingIntent pi = (PendingIntent) XposedHelpers.getObjectField(curAlarm, "operation");
                        Intent intent = (Intent) XposedHelpers.callMethod(pi, "getIntent");

                        XSharedPreferences prefs = new XSharedPreferences(nlpFix.class.getPackage().getName());
                        prefs.reload();

                        if (intent.getAction().equals("com.google.android.gms.nlp.ALARM_WAKEUP_LOCATOR")) {
                            int locatorMaxFreq = tryParseInt(prefs.getString("seconds_locator", "240"));
                            locatorMaxFreq *= 1000; //convert to ms

                            //Debounce this to our minimum interval.
                            final long now = SystemClock.elapsedRealtime();
                            long timeSinceLastLocator = now - mLastLocatorAlarm;

                            if (timeSinceLastLocator < locatorMaxFreq) {
                                //Not enough time has passed since the last wakelock
                                XposedBridge.log("NlpUnbounce: Preventing ALARM_WAKEUP_LOCATOR.  Last granted: " + timeSinceLastLocator + " milliseconds ago.  Frequency allowed: " + locatorMaxFreq);
                                alarmList.remove(j);
                            } else {
                                //Allow the wakelock
                                XposedBridge.log("NlpUnbounce: Allowing ALARM_WAKEUP_LOCATOR.");
                                mLastLocatorAlarm = now;
                            }
                        }
                        if (intent.getAction().equals("com.google.android.gms.nlp.ALARM_WAKEUP_ACTIVITY_DETECTION")) {
                            int detectionMaxFreq = tryParseInt(prefs.getString("seconds_detection", "240"));
                            detectionMaxFreq *= 1000; //convert to ms

                            //Debounce this to our minimum interval.
                            final long now = SystemClock.elapsedRealtime();
                            long timeSinceLastDetection = now - mLastDetectionAlarm;

                            if (timeSinceLastDetection < detectionMaxFreq) {
                                //Not enough time has passed since the last wakelock
                                XposedBridge.log("NlpUnbounce: Preventing ALARM_WAKEUP_ACTIVITY_DETECTION.  Last granted: " + timeSinceLastDetection + " milliseconds ago.  Frequency allowed: " + detectionMaxFreq);
                                alarmList.remove(j);
                            } else {
                                //Allow the wakelock
                                XposedBridge.log("NlpUnbounce: Allowing ALARM_WAKEUP_ACTIVITY_DETECTION.");
                                mLastLocatorAlarm = now;
                            }
                        }
                    }
                }
            });
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


