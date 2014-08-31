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

import java.util.Date;
import java.text.DateFormat;

public class nlpFix implements IXposedHookLoadPackage {

    private long mLastLocatorAlarm = 0;  // Last alarm attempt
    private long mLastDetectionAlarm = 0;  // Last alarm attempt
    private long mLastNlpWakelock = 0;  // Last wakelock attempt
    private long mLastNlpCollectorWakelock = 0;  // Last wakelock attempt

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
                        if(intent==null || intent.getAction()==null){
                            continue;
                        } 

                        XSharedPreferences prefs = new XSharedPreferences(nlpFix.class.getPackage().getName());
                        prefs.reload();

                        String dt = DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
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

                                    alarmList.remove(j);
                                } else {
                                    //Allow the wakelock
                                    XposedBridge.log(dt + " NlpUnbounce: Allowing ALARM_WAKEUP_LOCATOR." + locatorMaxFreq);
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

                                    alarmList.remove(j);
                                } else {
                                    //Allow the wakelock
                                    XposedBridge.log(dt + " NlpUnbounce: Allowing ALARM_WAKEUP_ACTIVITY_DETECTION." + detectionMaxFreq);
                                    //mLastLocatorAlarm = now;
                                    mLastDetectionAlarm = now;
                                }
                            }
                        }
                    }
                }
            });


            findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    XSharedPreferences prefs = new XSharedPreferences(nlpFix.class.getPackage().getName());
                    prefs.reload();

                    String wakeLockName = (String)param.args[2];
                    String dt = DateFormat.getDateTimeInstance().format(new Date(System.currentTimeMillis()));
                    if (wakeLockName.equals("NlpCollectorWakeLock"))
                    {
                        int collectorMaxFreq = tryParseInt(prefs.getString("seconds_wake_collector", "240"));
                        collectorMaxFreq *= 1000; //convert to ms

                        if (collectorMaxFreq != 0) {
                            //Debounce this to our minimum interval.
                            final long now = SystemClock.elapsedRealtime();
                            long timeSinceLastWakelock = now - mLastNlpCollectorWakelock;

                            if (timeSinceLastWakelock < collectorMaxFreq) {
                                //Not enough time has passed since the last wakelock
                                //XposedBridge.log("NlpUnbounce: Preventing NlpCollectorWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.  Frequency allowed: " + collectorMaxFreq);

                                param.setResult(null);
                            } else {
                                //Allow the wakelock
                                XposedBridge.log(dt + " NlpUnbounce: Allowing NlpCollectorWakeLock." + collectorMaxFreq);
                                mLastNlpCollectorWakelock = now;
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
                            long timeSinceLastWakelock = now - mLastNlpWakelock;

                            if (timeSinceLastWakelock < nlpMaxFreq) {
                                //Not enough time has passed since the last wakelock
                                //XposedBridge.log("NlpUnbounce: Preventing NlpWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.  Frequency allowed: " + nlpMaxFreq);

                                param.setResult(null);
                            } else {
                                //Allow the wakelock
                                XposedBridge.log(dt + " NlpUnbounce: Allowing NlpWakeLock." + nlpMaxFreq);
                                mLastNlpWakelock = now;
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


