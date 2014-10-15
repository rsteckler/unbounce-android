package com.ryansteckler.nlpunbounce.hooks;

/**
 * Created by ryan steckler on 8/18/14.
 */

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;

import com.ryansteckler.nlpunbounce.ActivityReceiver;
import com.ryansteckler.nlpunbounce.XposedReceiver;
import com.ryansteckler.nlpunbounce.models.InterimWakelock;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;


public class Wakelocks implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    private static final String TAG = "Unbounce: ";
    private static final String VERSION = "1.3.2"; //This needs to be pulled from the manifest or gradle build.
    public static HashMap<IBinder, InterimWakelock> mCurrentWakeLocks;
    private static boolean showedUnsupportedAlarmMessage = false;
    private HashMap<PendingIntent, List<Intent>> mIntentMap ;
    private final BroadcastReceiver mBroadcastReceiver = new XposedReceiver();
    XSharedPreferences m_prefs;
    private HashMap<String, Long> mLastWakelockAttempts = null; //The last time each wakelock was allowed.
    private HashMap<String, Long> mLastAlarmAttempts = null; //The last time each alarm was allowed.
    private long mLastUpdateStats = 0;
    private long mUpdateStatsFrequency = 600000; //Send for saving every ten minutes
    private long mLastReloadPrefs = 0;
    private long mReloadPrefsFrequency = 60000; //Reload prefs every minute
    private boolean mRegisteredRecevier = false;

    private static int tryParseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return 0;
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {


        m_prefs = new XSharedPreferences("com.ryansteckler.nlpunbounce");
        m_prefs.reload();

        mCurrentWakeLocks = new HashMap<IBinder, InterimWakelock>();
        mLastWakelockAttempts = new HashMap<String, Long>();
        mLastAlarmAttempts = new HashMap<String, Long>();
        mIntentMap = new HashMap<PendingIntent, List<Intent>>();

        debugLog("Version " + VERSION);
    }

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {


        defaultLog("Package Listing: " + lpparam.packageName);
        hookAlarms(lpparam);
        return;


    }

    private void setupReceiver(XC_MethodHook.MethodHookParam param) {
        if (!mRegisteredRecevier) {
            mRegisteredRecevier = true;
            Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");

            IntentFilter filter = new IntentFilter();
            filter.addAction(XposedReceiver.RESET_ACTION);
            filter.addAction(XposedReceiver.REFRESH_ACTION);
            context.registerReceiver(mBroadcastReceiver, filter);
        }
    }

    private void hookAlarms(LoadPackageParam lpparam) {
        boolean alarmsHooked = false;

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            //Try for wakelock hooks for API levels 15-16
            debugLog("Attempting 16 AlarmHook");
            try16AlarmHook(lpparam);
            debugLog("Successful 16 AlarmHook");
            alarmsHooked = true;
        }

        if (!alarmsHooked) {
            XposedBridge.log(TAG + "Unsupported Android version trying to hook Alarms.");
        }
    }

    private void hookWakeLocks(LoadPackageParam lpparam) {
        boolean wakeLocksHooked = false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            //Try for wakelock hooks for API levels 15-16
            defaultLog("Attempting 15to16 WakeLockHook");
            try15To16WakeLockHook(lpparam);
            defaultLog("Successful 15to16 WakeLockHook");
            wakeLocksHooked = true;
        }

        if (!wakeLocksHooked) {
            XposedBridge.log(TAG + "Unsupported Android version trying to hook WakeLocks.");
        }
    }

    private void try19To20WakeLockHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String) param.args[2];
                IBinder lock = (IBinder) param.args[0];
                handleWakeLockAcquire(param, wakeLockName, lock);
            }
        });

        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "releaseWakeLockInternal", android.os.IBinder.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                IBinder lock = (IBinder) param.args[0];
                handleWakeLockRelease(param, lock);
            }
        });

    }

    private void try17To18WakeLockHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String) param.args[2];
                IBinder lock = (IBinder) param.args[0];
                handleWakeLockAcquire(param, wakeLockName, lock);
            }
        });

        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "releaseWakeLockInternal", android.os.IBinder.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                IBinder lock = (IBinder) param.args[0];
                handleWakeLockRelease(param, lock);
            }
        });

    }

    private void try15To16WakeLockHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.PowerManagerService", lpparam.classLoader, "acquireWakeLockLocked", int.class, android.os.IBinder.class, int.class, int.class, String.class, android.os.WorkSource.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String) param.args[4];
                IBinder lock = (IBinder) param.args[1];
                handleWakeLockAcquire(param, wakeLockName, lock);
            }
        });

        findAndHookMethod("com.android.server.PowerManagerService", lpparam.classLoader, "releaseWakeLockLocked", android.os.IBinder.class, int.class, boolean.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                IBinder lock = (IBinder) param.args[0];
                handleWakeLockRelease(param, lock);
            }
        });

    }

    private void try19To20AlarmHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, long.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<Object> triggers = (ArrayList<Object>) param.args[0];
                handleAlarm(param, triggers);
            }
        });
    }

    private void try15To18AlarmHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, ArrayList.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<Object> triggers = (ArrayList<Object>) param.args[1];
                handleAlarm(param, triggers);
            }
        });
    }

    private void try16AlarmHook(LoadPackageParam lpparam) {

        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getBroadcast", Context.class, int.class,
                Intent.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getBroadcast: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getBroadcast");
                        }
                    }

                });
        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getService", Context.class, int.class,
                Intent.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getService: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getService");
                        }
                    }

                });
        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getActivity", Context.class, int.class,
                Intent.class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getActivity: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getActivity");
                        }
                    }

                });

        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getActivity", Context.class, int.class,
                Intent.class, int.class, Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getActivity bundle: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getActivity bundle:");
                        }
                    }

                });

        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getActivities", Context.class, int.class,
                Intent[].class, int.class, Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getActivities bundle: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getActivity bundle:");
                        }
                    }

                });

        findAndHookMethod("android.app.PendingIntent", lpparam.classLoader, "getActivities", Context.class, int.class,
                Intent[].class, int.class, new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (null != param.getResult()) {
                            debugLog("Checking PendingIntent getActivities: " + param.getResult().hashCode());
                            insertAdditionalIntentForJB(param);
                        } else {
                            debugLog("Null PendingIntent found for getActivities");
                        }
                    }

                });


        findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, ArrayList.class, long.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                ArrayList<Object> triggers = (ArrayList<Object>) param.args[1];
                handleAlarm(param, triggers);
            }
        });


    }

    private void insertAdditionalIntentForJB(XC_MethodHook.MethodHookParam param) {
        if (null != param.args && param.args.length > 0) {
            for (Object i : param.args) {
                Object objPendingIntent = param.getResult();
                if (null != i && i instanceof Intent) {
                    String intentAction = ((Intent) i).getAction();

                    if (null != objPendingIntent && objPendingIntent instanceof PendingIntent && null != intentAction) {
                        debugLog("Adding additional intent: " + intentAction + " For Hash: " + objPendingIntent.hashCode());
                        XposedHelpers.setAdditionalInstanceField(objPendingIntent, "Intent", (Intent) i);
                        List<Intent> lstIntent = new <Intent>ArrayList();
                        lstIntent.add((Intent) i);
                        mIntentMap.put((PendingIntent) objPendingIntent, lstIntent);
                    } else {
                        debugLog("Adding additional intent: " + intentAction + " For Hash: " + objPendingIntent.hashCode());
                    }
                } else if (null != i && i instanceof Intent[]) {

                    debugLog("Array of Intents found  For Hash: " + objPendingIntent.hashCode());
                    XposedHelpers.setAdditionalInstanceField(objPendingIntent, "Intent", (Intent[]) i);
                    List<Intent> lstIntent = new <Intent>ArrayList();
                    for (Intent intArr : (Intent[]) i) {
                        String intentAction = intArr.getAction();

                        if (null != objPendingIntent && objPendingIntent instanceof PendingIntent && null != intentAction) {
                            debugLog("Adding additional intent: " + intentAction + " For Hash: " + objPendingIntent.hashCode());
                            lstIntent.add(intArr);
                        } else {
                            debugLog("Adding additional intent: " + intentAction + " For Hash: " + objPendingIntent.hashCode());
                        }
                    }
                    mIntentMap.put((PendingIntent) objPendingIntent, lstIntent);
                }
            }
        }
    }


    private void handleWakeLockAcquire(XC_MethodHook.MethodHookParam param, String wakeLockName, IBinder lock) {

        //If we're blocking this wakelock
        String prefName = "wakelock_" + wakeLockName + "_enabled";
        if (m_prefs.getBoolean(prefName, false)) {

            long collectorMaxFreq = m_prefs.getLong("wakelock_" + wakeLockName + "_seconds", 240);
            collectorMaxFreq *= 1000; //convert to ms

            //Debounce this to our minimum interval.
            long lastAttempt = 0;
            try {
                lastAttempt = mLastWakelockAttempts.get(wakeLockName);
            } catch (NullPointerException npe) { /* ok.  Just havent attempted yet.  Use 0 */ }

            long now = SystemClock.elapsedRealtime();
            long timeSinceLastWakeLock = now - lastAttempt;

            if (timeSinceLastWakeLock < collectorMaxFreq) {
                //Not enough time has passed since the last wakelock.  Deny the wakelock
                param.setResult(null);
                recordWakelockBlock(param, wakeLockName);

                debugLog("Preventing " + wakeLockName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastWakeLock);

            } else {
                //Allow the wakelock
                defaultLog(TAG + "Allowing " + wakeLockName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastWakeLock);
                mLastWakelockAttempts.put(wakeLockName, now);
                recordAcquire(wakeLockName, lock);
            }
        } else {
            recordAcquire(wakeLockName, lock);
        }
    }

    private void recordAcquire(String wakeLockName, IBinder lock) {
        //Get the lock
        InterimWakelock curStats = mCurrentWakeLocks.get(lock);
        if (curStats == null) {
            curStats = new InterimWakelock();
            curStats.setName(wakeLockName);
            curStats.setTimeStarted(SystemClock.elapsedRealtime());
            mCurrentWakeLocks.put(lock, curStats);
        }
    }

    private void recordAlarmAcquire(Context context, String alarmName) {
        UnbounceStatsCollection.getInstance().incrementAlarmAllowed(context, alarmName);
    }

    private void handleWakeLockRelease(XC_MethodHook.MethodHookParam param, IBinder lock) {
        InterimWakelock curStats = mCurrentWakeLocks.remove(lock);
        if (curStats != null) {
            curStats.setTimeStopped(SystemClock.elapsedRealtime());
            Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
            UnbounceStatsCollection.getInstance().addInterimWakelock(context, curStats);
        }
    }

    private void updateStatsIfNeeded(Context context) {
        if (context != null) {
            final long now = SystemClock.elapsedRealtime();

            long timeSinceLastUpdateStats = now - mLastUpdateStats;

            if (timeSinceLastUpdateStats > mUpdateStatsFrequency) {
                Intent intent = new Intent(ActivityReceiver.SEND_STATS_ACTION);
                //TODO:  add FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT to the intent to avoid needing to catch
                //      the IllegalStateException.  The flag value changed between 4.3 and 4.4  :/
                intent.putExtra("stats", UnbounceStatsCollection.getInstance().getSerializableStats(UnbounceStatsCollection.STAT_CURRENT));
                intent.putExtra("stat_type", UnbounceStatsCollection.STAT_CURRENT);
                intent.putExtra("running_since", UnbounceStatsCollection.getInstance().getRunningSince());
                try {
                    context.sendBroadcast(intent);
                } catch (IllegalStateException ise) {
                    //Ignore.  This is because boot hasn't completed yet.
                }

                Intent intentPush = new Intent(ActivityReceiver.SEND_STATS_ACTION);
                //TODO:  add FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT to the intent to avoid needing to catch
                //      the IllegalStateException.  The flag value changed between 4.3 and 4.4  :/
                intentPush.putExtra("stats", UnbounceStatsCollection.getInstance().getSerializableStats(UnbounceStatsCollection.STAT_PUSH));
                intentPush.putExtra("stat_type", UnbounceStatsCollection.STAT_PUSH);
                try {
                    context.sendBroadcast(intentPush);
                } catch (IllegalStateException ise) {
                    //Ignore.  This is because boot hasn't completed yet.
                }

                mLastUpdateStats = now;
            }
        }
    }

    private void handleAlarm(XC_MethodHook.MethodHookParam param, ArrayList<Object> triggers) {
        final long now = SystemClock.elapsedRealtime();
        Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
        long sinceReload = now - mLastReloadPrefs;
        if (sinceReload > mReloadPrefsFrequency) {
            setupReceiver(param);
            m_prefs.reload();
            UnbounceStatsCollection.getInstance().refreshPrefs(m_prefs);
            updateStatsIfNeeded(context);
        }

        for (int j = triggers.size() - 1; j >= 0; j--) {
            Object curAlarm = triggers.get(j);

            PendingIntent pi = (PendingIntent) XposedHelpers.getObjectField(curAlarm, "operation");
            Intent intent = null;
            try {
                intent = (Intent) callMethod(pi, "getIntent");
                handleIntent(intent, triggers, now,j,param,context);
            } catch (NoSuchMethodError nsme) {
                //API prior to 4.2.2_r1 don't have this.
                if (!showedUnsupportedAlarmMessage) {
                    // debugLog("Alarm prevention is not yet supported on Android versions less than 4.2.2, adding additional logic for intent retrieval");


                    try {

                        debugLog("Pending Intent for: "
                                + pi.getTargetPackage() + " for Hash: " + pi.hashCode());


                        List<Intent> lstIntent = mIntentMap.get(pi);

                        if (null != lstIntent && lstIntent.size() > 0) {

                            debugLog("Intents retrieved successfully: "
                                    + lstIntent);
                            for (Intent intentItem : lstIntent) {
                                handleIntent(intentItem, triggers, now,j,param,context);
                            }
                        } else {
                            debugLog("No Intent found for this alarm :" + pi.hashCode());
                        }

                    } catch (Exception e) {
                        showedUnsupportedAlarmMessage = true;
                        debugLog("Alarm prevention is not yet supported on Android versions less than 4.2.2, additional logic failed");
                        debugLog("Exception" + e);

                    }
                }
            }


        }
    }

    private void handleIntent(Intent intent, ArrayList<Object> triggers, long now, int j,XC_MethodHook.MethodHookParam param,Context context ) {
        if (intent == null || intent.getAction() == null) {
            //TODO: Why does the system have alarms with null intents?
            return;
        }
        String alarmName = intent.getAction();
        //If we're blocking this wakelock
        String prefName = "alarm_" + alarmName + "_enabled";
        if (m_prefs.getBoolean(prefName, false)) {

            long collectorMaxFreq = m_prefs.getLong("alarm_" + alarmName + "_seconds", 240);
            collectorMaxFreq *= 1000; //convert to ms

            //Debounce this to our minimum interval.
            long lastAttempt = 0;
            try {
                lastAttempt = mLastAlarmAttempts.get(alarmName);
            } catch (NullPointerException npe) { /* ok.  Just havent attempted yet.  Use 0 */ }

            long timeSinceLastAlarm = now - lastAttempt;

            if (timeSinceLastAlarm < collectorMaxFreq) {
                //Not enough time has passed since the last wakelock.  Deny the wakelock
                //Not enough time has passed since the last alarm.  Remove it from the triggerlist
                triggers.remove(j);
                recordAlarmBlock(param, alarmName);

                debugLog("Preventing " + alarmName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastAlarm);

            } else {
                //Allow the wakelock
                defaultLog(TAG + "Allowing " + alarmName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastAlarm);
                mLastAlarmAttempts.put(alarmName, now);
                recordAlarmAcquire(context, alarmName);
            }
        } else {
            recordAlarmAcquire(context, alarmName);
        }
    }

    private void recordWakelockBlock(XC_MethodHook.MethodHookParam param, String name) {

        Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");

        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementWakelockBlock(context, name);
        }
    }

    private void recordAlarmBlock(XC_MethodHook.MethodHookParam param, String name) {

        Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");

        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementAlarmBlock(context, name);
        }

    }

    private void debugLog(String log) {
        String curLevel = m_prefs.getString("logging_level", "default");
        if (curLevel.equals("verbose")) {
            XposedBridge.log(TAG + log);
        }
    }

    private void defaultLog(String log) {
        String curLevel = m_prefs.getString("logging_level", "default");
        if (curLevel.equals("default") || curLevel.equals("verbose")) {
            XposedBridge.log(TAG + log);

        }
    }
}


