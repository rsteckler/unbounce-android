package com.ryansteckler.nlpfix;

/**
 * Created by ryan steckler on 8/18/14.
 */
import android.os.SystemClock;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;


public class nlpFix implements IXposedHookLoadPackage {

    //Configuration:
    //If Play Services tries to set a recurring LocationRequest at or below this frequency:
    private static final int MIN_NETWORK_THRESHOLD_MILLIS = 60000;
    //...we change the request to this frequency:
    private static final int MIN_NETWORK_RETRY_MILLIS = 240000;

    //Don't allow NlpWakeLock to acquire a wakelock more frequently than:
    private static final int NLP_WAKELOCK_MAX_FREQ = 240000;
    //Don't allow NlpCollectorWakeLock to acquire a wakelock more frequently than:
    private static final int NLP_COLLECTOR_WAKELOCK_MAX_FREQ = 240000;




    //Don't touch anything below here unless you know what you're doing.
    private long mLastNlpWakeLockTime = 0;  // Last wakelock attempt
    private long mLastNlpCollectorWakeLockTime = 0;  // Last wakelock attempt

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("android")) {
            findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    String wakeLockName = (String)param.args[2];
                    if (wakeLockName.equals("NlpCollectorWakeLock"))
                    {
                        //Debounce this to our minimum interval.
                        final long now = SystemClock.elapsedRealtime();
                        long timeSinceLastWakelock = now -  mLastNlpCollectorWakeLockTime;

                        if(timeSinceLastWakelock < NLP_COLLECTOR_WAKELOCK_MAX_FREQ) {
                            //Not enough time has passed since the last wakelock
                            XposedBridge.log("NlpUnbounce: Preventing NlpCollectorWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.");
                            param.setResult(null);
                        }
                        else
                        {
                            //Allow the wakelock
                            XposedBridge.log("NlpUnbounce: Allowing NlpCollectorWakeLock.");
                            mLastNlpCollectorWakeLockTime = now;
                        }

                    }
                    else if (wakeLockName.equals("NlpWakeLock"))
                    {
                        //Debounce this to our minimum interval.
                        final long now = SystemClock.elapsedRealtime();
                        long timeSinceLastWakelock = now - mLastNlpWakeLockTime;

                        if(timeSinceLastWakelock < NLP_WAKELOCK_MAX_FREQ) {
                            //Not enough time has passed since the last wakelock
                            XposedBridge.log("NlpUnbounce: Preventing NlpWakeLock.  Last granted: " + timeSinceLastWakelock + " milliseconds ago.");
                            param.setResult(null);
                        }
                        else
                        {
                            //Allow the wakelock
                            XposedBridge.log("NlpUnbounce: Allowing NlpWakeLock.");
                            mLastNlpWakeLockTime = now;
                        }
                    }
                }
            });
        }

        if (lpparam.packageName.equals("com.google.android.gms")) {
            findAndHookMethod("com.google.android.gms.location.LocationRequest", lpparam.classLoader, "a", long.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {

                    if((Long)(param.args[0]) <= MIN_NETWORK_THRESHOLD_MILLIS)
                    {
                        XposedBridge.log("NlpUnbounce: Detected NLP Reporting equal to or 60 seconds. (requested: " + param.args[0] + " milliseconds).  Setting interval to " + MIN_NETWORK_THRESHOLD_MILLIS + " milliseconds.");
                        param.args[0] = MIN_NETWORK_RETRY_MILLIS;
                    }
                }
            });
        }
    }
}


