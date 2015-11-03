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
import android.os.IBinder;
import android.os.SystemClock;

import com.ryansteckler.nlpunbounce.ActivityReceiver;
import com.ryansteckler.nlpunbounce.XposedReceiver;
import com.ryansteckler.nlpunbounce.models.InterimEvent;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Wakelocks implements IXposedHookLoadPackage {

    private static final String TAG = "Amplify: ";
    public static final String VERSION = "3.1.1"; //This needs to be pulled from the manifest or gradle build.
    public static final String FILE_VERSION = "3"; //This needs to be pulled from the manifest or gradle build.
    private HashMap<String, Long> mLastWakelockAttempts = null; //The last time each wakelock was allowed.
    private HashMap<String, Long> mLastAlarmAttempts = null; //The last time each alarm was allowed.

    private long mLastUpdateStats = 0;
    private long mUpdateStatsFrequency = 300000; //Save every five minutes
    private long mLastReloadPrefs = 0;
    private long mReloadPrefsFrequency = 60000; //Reload prefs every minute

    private final BroadcastReceiver mBroadcastReceiver = new XposedReceiver();
    private boolean mRegisteredRecevier = false;

    XSharedPreferences m_prefs;
    public static HashMap<IBinder, InterimEvent> mCurrentWakeLocks;

    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {

        if (lpparam.packageName.equals("android")) {

            m_prefs = new XSharedPreferences("com.ryansteckler.nlpunbounce");
            m_prefs.reload();

            defaultLog("Version " + VERSION);

            mCurrentWakeLocks = new HashMap<IBinder, InterimEvent>();
            mLastWakelockAttempts = new HashMap<String, Long>();
            mLastAlarmAttempts = new HashMap<String, Long>();

            hookAlarms(lpparam);
            hookWakeLocks(lpparam);
            hookServices(lpparam);
            resetFilesIfNeeded(null);
        } else if (lpparam.packageName.equals("com.ryansteckler.nlpunbounce")) {
            hookAmplifyClasses(lpparam);

        }
    }


    private void resetFilesIfNeeded(Context context) {
        //Get the version number and compare it to our app version.
        String lastVersion = m_prefs.getString("file_version", "0");
        if (!lastVersion.equals(FILE_VERSION)) {
            //Reset stats
            defaultLog("Resetting stat files on version upgrade.");
            if (context != null) {
                Intent intent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);
                try {
                    context.sendBroadcast(intent);
                } catch (IllegalStateException ise) {
                }
            }
        }
    }

    private void hookAmplifyClasses(LoadPackageParam lpparam) {
        findAndHookMethod("com.ryansteckler.nlpunbounce.HomeFragment", lpparam.classLoader, "isUnbounceServiceRunning", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(true);
            }
        });
        findAndHookMethod("com.ryansteckler.nlpunbounce.HomeFragment", lpparam.classLoader, "getAmplifyKernelVersion", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(VERSION);
            }
        });

//        findAndHookMethod("com.ryansteckler.nlpunbounce.HomeFragment", lpparam.classLoader, "requestRefresh", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String lastVersion = m_prefs.getString("file_version", "0");
//            }
//        });

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //Try for alarm hooks for API levels >= 19
            defaultLog("Attempting 19to21 AlarmHook");
            try19To21AlarmHook(lpparam);
            defaultLog("Successful 19to21 AlarmHook");
            alarmsHooked = true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //Try for alarm hooks for API levels 15-18.
            defaultLog("Attempting 15to18 AlarmHook");
            try15To18AlarmHook(lpparam);
            defaultLog("Successful 15to18 AlarmHook");
            alarmsHooked = true;

        }

        if (!alarmsHooked) {
            XposedBridge.log(TAG + "Unsupported Android version trying to hook Alarms.");
        }
    }

    private void hookWakeLocks(LoadPackageParam lpparam) {
        boolean wakeLocksHooked = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Try for wakelock hooks for API levels 19-20
            defaultLog("Attempting 21 WakeLockHook");
            try21WakeLockHook(lpparam);
            defaultLog("Successful 21 WakeLockHook");
            wakeLocksHooked = true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT &&
                Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //Try for wakelock hooks for API levels 19-20
            defaultLog("Attempting 19to20 WakeLockHook");
            try19To20WakeLockHook(lpparam);
            defaultLog("Successful 19to20 WakeLockHook");
            wakeLocksHooked = true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 &&
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            //Try for wakelock hooks for API levels 17-18
            defaultLog("Attempting 17to18 WakeLockHook");
            try17To18WakeLockHook(lpparam);
            defaultLog("Successful 17to18 WakeLockHook");
            wakeLocksHooked = true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1 &&
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

    private void hookServices(LoadPackageParam lpparam) {

        boolean isServicBlockingEnabled = m_prefs.getBoolean("enable_service_block", true);

        defaultLog("Service Blocking Status: " + isServicBlockingEnabled);

        if (isServicBlockingEnabled) {
            boolean servicesHooked = false;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                //Try for wakelock hooks for API levels 19-20
                defaultLog("Attempting 17to20 ServiceHook");
                try17To20ServiceHook(lpparam);
                defaultLog("Successful 17to20 ServiceHook");
                servicesHooked = true;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH &&
                    Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                //Try for wakelock hooks for API levels 19-20
                defaultLog("Attempting 14to16 ServiceHook");
                try14To16ServiceHook(lpparam);
                defaultLog("Successful 14to16 ServiceHook");
                servicesHooked = true;
            }

            if (!servicesHooked) {
                defaultLog("Unsupported Android version trying to hook Services.");
            }
        } else {
            defaultLog("Service Blocking is disabled.");
        }
    }

    //in back to 4.2_r1
    private void try17To20ServiceHook(LoadPackageParam lpparam) {
        try {
            findAndHookMethod("com.android.server.am.ActiveServices", lpparam.classLoader, "startServiceLocked", "android.app.IApplicationThread", Intent.class, String.class, int.class, int.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Intent intent = (Intent) param.args[1];
                    handleServiceStart(param, intent);
                }
            });
        } catch (NoSuchMethodError nsme) {
            //Nonstandard version of the library.  Try an alternate
            defaultLog("Standard Service hook failed.  Trying alternate.");
            findAndHookMethod("com.android.server.am.ActiveServices", lpparam.classLoader, "startServiceLocked", "android.app.IApplicationThread", Intent.class, String.class, int.class, int.class, int.class, Context.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Intent intent = (Intent) param.args[1];
                    handleServiceStart(param, intent);
                }
            });
        }
    }

    //4.1.2r1 to
    private void try14To16ServiceHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.am.ActivityManagerService", lpparam.classLoader, "startServiceLocked", "android.app.IApplicationThread", Intent.class, String.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Intent intent = (Intent) param.args[1];
                handleServiceStart(param, intent);
            }
        });
    }

    private void try21WakeLockHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, String.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String) param.args[2];
                IBinder lock = (IBinder) param.args[0];
                int uId = (Integer) param.args[6];
                handleWakeLockAcquire(param, wakeLockName, lock, uId);
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


    private void try19To20WakeLockHook(LoadPackageParam lpparam) {
        findAndHookMethod("com.android.server.power.PowerManagerService", lpparam.classLoader, "acquireWakeLockInternal", android.os.IBinder.class, int.class, String.class, String.class, android.os.WorkSource.class, int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String wakeLockName = (String) param.args[2];
                IBinder lock = (IBinder) param.args[0];
                int uId = (Integer) param.args[5];
                handleWakeLockAcquire(param, wakeLockName, lock, uId);
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
                int uId = (Integer) param.args[4];
                handleWakeLockAcquire(param, wakeLockName, lock, uId);
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
                int uId = (Integer) param.args[2];
                handleWakeLockAcquire(param, wakeLockName, lock, uId);
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

    private void try19To21AlarmHook(LoadPackageParam lpparam) {
        try {
            findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, long.class, long.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ArrayList<Object> triggers = (ArrayList<Object>) param.args[0];
                    handleAlarm(param, triggers);
                }
            });
        } catch (NoSuchMethodError nsme) {
            //Nonstandard version of the library.  Try an alternate
            defaultLog("Standard Alarm hook failed.  Trying alternate for Sony device.");
            findAndHookMethod("com.android.server.AlarmManagerService", lpparam.classLoader, "triggerAlarmsLocked", ArrayList.class, long.class, long.class, boolean.class, boolean.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    ArrayList<Object> triggers = (ArrayList<Object>) param.args[0];
                    handleAlarm(param, triggers);
                }
            });
        }
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

    private void handleServiceStart(XC_MethodHook.MethodHookParam param, Intent serviceIntent) {

        if (serviceIntent == null || serviceIntent.getComponent() == null) {
            return;
        }

        String serviceName = serviceIntent.getComponent().flattenToShortString();
        if (serviceName == null) {
            return;
        }
        int callingUid = (Integer) param.args[4];

        String prefName = "service_" + serviceName + "_enabled";
        if (m_prefs.getBoolean(prefName, false)) {

            param.setResult(null);
            recordServiceBlock(param, serviceName, callingUid);
            debugLog("Preventing Service " + serviceName + ".");

        } else {
            debugLog("Allowing service " + serviceName + ".");
            recordServiceStart(param, serviceName, callingUid);
        }

    }

    private void handleWakeLockAcquire(XC_MethodHook.MethodHookParam param, String wakeLockName, IBinder lock, int uId) {

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
                recordWakelockBlock(param, wakeLockName, uId);

                debugLog("Preventing Wakelock " + wakeLockName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastWakeLock);

            } else {
                //Allow the wakelock
                defaultLog("Allowing Wakelock " + wakeLockName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastWakeLock);
                mLastWakelockAttempts.put(wakeLockName, now);
                recordAcquire(wakeLockName, lock, uId);
            }
        } else {
            recordAcquire(wakeLockName, lock, uId);
        }
    }

    private void recordServiceStart(XC_MethodHook.MethodHookParam param, String serviceName, int uId) {
        //Get the service
//        InterimEvent curStats = mCurrentServices.get(serviceName);
//        if (curStats == null) {
//            curStats = new InterimEvent();
//            curStats.setName(serviceName);
//            curStats.setTimeStarted(SystemClock.elapsedRealtime());
//            mCurrentServices.put(serviceName, curStats);
//        }
        Context context = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
            } catch (NoSuchFieldError nsfe) {
                Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");
                context = (Context) XposedHelpers.getObjectField(am, "mContext");
            }
        } else {
            Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");
            context = (Context) XposedHelpers.getObjectField(am, "mContext");
        }
        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementServiceAllowed(context, serviceName, uId);
        }

    }

    private void recordAcquire(String wakeLockName, IBinder lock, int uId) {
        //Get the lock
        InterimEvent curStats = mCurrentWakeLocks.get(lock);
        if (curStats == null) {
            curStats = new InterimEvent();
            curStats.setName(wakeLockName);
            curStats.setUId(uId);
            curStats.setTimeStarted(SystemClock.elapsedRealtime());
            mCurrentWakeLocks.put(lock, curStats);
        }
    }

    private void recordAlarmAcquire(Context context, String alarmName, String packageName) {
        UnbounceStatsCollection.getInstance().incrementAlarmAllowed(context, alarmName, packageName);
    }

//    private void handleServiceStop(XC_MethodHook.MethodHookParam param, Intent serviceIntent) {
//
//        if (serviceIntent == null || serviceIntent.getComponent() == null) {
//            defaultLog("Service (stop) intent or component is null");
//            return;
//        }
//
//        String serviceName = serviceIntent.getComponent().flattenToShortString();
//        if (serviceName == null) {
//            defaultLog("Service (stop) component name is null");
//            return;
//        }
//        defaultLog("Service (stop) intent name: " + serviceName);
//
//        InterimEvent curStats = mCurrentServices.remove(serviceName);
//        if (curStats != null) {
//            curStats.setTimeStopped(SystemClock.elapsedRealtime());
//            Object am = (Object)XposedHelpers.getObjectField(param.thisObject, "mAm");
//            Context context = (Context) XposedHelpers.getObjectField(am, "mContext");
//            UnbounceStatsCollection.getInstance().addInterimService(context, curStats);
//        }
//    }

    private void handleWakeLockRelease(XC_MethodHook.MethodHookParam param, IBinder lock) {
        InterimEvent curStats = mCurrentWakeLocks.remove(lock);
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
                resetFilesIfNeeded(context);

                if (!UnbounceStatsCollection.getInstance().saveNow(context)) {
                    //Request the activity to create the files.
                    Intent intent = new Intent(ActivityReceiver.CREATE_FILES_ACTION);
                    try {
                        context.sendBroadcast(intent);
                    } catch (IllegalStateException ise) {
                    }
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
            } catch (NoSuchMethodError nsme) {
                try {
                    Object mTarget = XposedHelpers.getObjectField(pi, "mTarget");

                    if (null != mTarget) {
                        Object pendingIntentRecord$Key = XposedHelpers.getObjectField(mTarget, "key");
                        if (null != pendingIntentRecord$Key) {
                            Object requestIntent = XposedHelpers.getObjectField(pendingIntentRecord$Key, "requestIntent");
                            intent = (Intent) requestIntent;
                        }
                    }
                } catch (Exception e) {
                    defaultLog("Additional logic to detect alarms on 4.1.2 failed for: " + pi);
                }
            }

            if (intent == null) {
                //TODO: Why does the system have alarms with null intents?
                continue;
            }

            String alarmName = null;
            //Make sure one of the tags exists.
            if (intent.getAction() != null) {
                alarmName = intent.getAction();
            } else if (intent.getComponent() != null) {
                alarmName = intent.getComponent().flattenToShortString();
            }

            if (alarmName == null) {
                //TODO: Why does the system have alarms with null intents?
                continue;
            }

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
                    //Not enough time has passed since the last alarm.  Remove it from the triggerlist
                    triggers.remove(j);
                    recordAlarmBlock(param, alarmName, pi.getTargetPackage());

                    debugLog("Preventing Alarm " + alarmName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastAlarm);

                } else {
                    //Allow the wakelock
                    defaultLog("Allowing Alarm " + alarmName + ".  Max Interval: " + collectorMaxFreq + " Time since last granted: " + timeSinceLastAlarm);
                    mLastAlarmAttempts.put(alarmName, now);
                    recordAlarmAcquire(context, alarmName, pi.getTargetPackage());
                }
            } else {
                recordAlarmAcquire(context, alarmName, pi.getTargetPackage());
            }
        }
    }

    private void recordWakelockBlock(XC_MethodHook.MethodHookParam param, String name, int uId) {

        Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");

        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementWakelockBlock(context, name, uId);
        }
    }

    private void recordServiceBlock(XC_MethodHook.MethodHookParam param, String name, int uId) {

        Context context = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");
            } catch (NoSuchFieldError nsfe) {
                Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");
                context = (Context) XposedHelpers.getObjectField(am, "mContext");
            }
        } else {
            Object am = (Object) XposedHelpers.getObjectField(param.thisObject, "mAm");
            context = (Context) XposedHelpers.getObjectField(am, "mContext");
        }

        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementServiceBlock(context, name, uId);
        }
    }

    private void recordAlarmBlock(XC_MethodHook.MethodHookParam param, String name, String packageName) {

        Context context = (Context) XposedHelpers.getObjectField(param.thisObject, "mContext");

        if (context != null) {
            UnbounceStatsCollection.getInstance().incrementAlarmBlock(context, name, packageName);
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


