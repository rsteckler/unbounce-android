package com.ryansteckler.nlpunbounce.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ryansteckler.nlpunbounce.ActivityReceiver;
import com.ryansteckler.nlpunbounce.XposedReceiver;
import com.ryansteckler.nlpunbounce.helpers.NetworkHelper;
import com.ryansteckler.nlpunbounce.hooks.Wakelocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import de.robv.android.xposed.XSharedPreferences;

import static com.ryansteckler.nlpunbounce.helpers.LocaleHelper.getFormattedTime;

/**
 * Created by rsteckler on 9/5/14.
 */
public class UnbounceStatsCollection implements Serializable {

//    final private static String URL_STATS = "http://localhost:8080/stats";
    final private static String URL_STATS = "http://unbounce-server-1.appspot.com/stats";

    final private static String STATS_DIRECTORY = "/data/data/com.ryansteckler.nlpunbounce/files/";
    final private static String STATS_FILENAME_CURRENT = "nlpunbounce.stats";
    final private static String STATS_FILENAME_GLOBAL = "nlpunbounce.global.stats";
    final private static String STATS_FILENAME_PUSH = "nlpunbounce.push.stats";
    private Map<String, BaseStats> mCurrentStats = null;
    private Map<String, Long> mGlobalStats = null;
    private Map<String, BaseStats> mSincePushStats = null;
    private long mRunningSince = 0;
    private boolean mGlobalParticipation = true;

    public static final int STAT_CURRENT = 0;
    public static final int STAT_GLOBAL = 1;
    private static final int STAT_PUSH = 2;

    private long mLastPush = 0;

    private static UnbounceStatsCollection mInstance = null;

    public static UnbounceStatsCollection getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UnbounceStatsCollection();
        }
        return mInstance;
    }

    private UnbounceStatsCollection() {
        mRunningSince = System.currentTimeMillis();
    }

    public Long getRunningSince() {
        return mRunningSince;
    }

    public String getRunningSinceFormatted() {
        long now = System.currentTimeMillis();
        long running = now - mRunningSince;

        long years = TimeUnit.MILLISECONDS.toDays(running) / 365;
        running -= TimeUnit.DAYS.toMillis(years * 365);
        long days = TimeUnit.MILLISECONDS.toDays(running);
        running -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(running);
        running -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(running);
        running -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(running);

        return getFormattedTime(years,days,hours,minutes,seconds);

    }

    public ArrayList<BaseStats> toAlarmArrayList(Context context) {
        loadStats(false);
        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());
        ArrayList<BaseStats> alarms = new ArrayList<>();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        for (BaseStats curStat : bases) {

            if (curStat instanceof AlarmStats) {
                alarms.add(curStat);
            }
        }

        return alarms;
    }

    public ArrayList<BaseStats> toWakelockArrayList(Context context) {
        loadStats(false);
        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());
        ArrayList<BaseStats> wakelocks = new ArrayList<>();
        Iterator<BaseStats> iter = bases.iterator();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        for (BaseStats curStat : bases) {

            if (curStat instanceof WakelockStats) {
                wakelocks.add(curStat);
            }
        }

        return wakelocks;
    }

    public ArrayList<BaseStats> toServiceArrayList(Context context)
    {
        loadStats(false);
        ArrayList<BaseStats> bases = new ArrayList<>(mCurrentStats.values());
        ArrayList<BaseStats> services = new ArrayList<>();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        for (BaseStats curStat : bases) {
            if (curStat instanceof ServiceStats) {
                services.add(curStat);
            }
        }

        return services;
    }

    public String getWakelockDurationAllowedFormatted(Context context, int statType) {

        long totalDuration = 0;

        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("WakelockAllowedDuration")) {
                    totalDuration = mGlobalStats.get("WakelockAllowedDuration");
                }
            }
        } else if (statType == STAT_CURRENT) {
            Map<String, BaseStats> statChoice = null;
            loadStats(false);

            //Iterate over all wakelocks and return the duration
            for (BaseStats curStat : mCurrentStats.values()) {
                if (curStat instanceof WakelockStats) {
                    totalDuration += ((WakelockStats) (curStat)).getAllowedDuration();
                }
            }
        }


        long years = TimeUnit.MILLISECONDS.toDays(totalDuration) / 365;
        totalDuration -= TimeUnit.DAYS.toMillis(years * 365);
        long days = TimeUnit.MILLISECONDS.toDays(totalDuration);
        totalDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(totalDuration);
        totalDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalDuration);
        totalDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalDuration);

        return getFormattedTime(years,days,hours,minutes,seconds);

    }

    public WakelockStats getWakelockStats(Context context, String wakelockName)
    {
        loadStats(false);
        if (mCurrentStats.containsKey(wakelockName)) {
            BaseStats base = mCurrentStats.get(wakelockName);
            if (base instanceof WakelockStats)
                return (WakelockStats)base;
            else
                return null;
        }
        else {
            WakelockStats emptyStat = new WakelockStats(wakelockName,-1);
            emptyStat.setAllowedCount(0);
            emptyStat.setAllowedDuration(0);
            emptyStat.setBlockCount(0);
            emptyStat.setName(wakelockName);
            mCurrentStats.put(wakelockName, emptyStat);
            return emptyStat;
        }
    }

    public ServiceStats getServiceStats(Context context, String serviceName)
    {
        loadStats(false);
        if (mCurrentStats.containsKey(serviceName)) {
            BaseStats base = mCurrentStats.get(serviceName);
            if (base instanceof ServiceStats)
                return (ServiceStats)base;
            else
                return null;
        }
        else {
            ServiceStats emptyStat = new ServiceStats(serviceName,-1);
            emptyStat.setAllowedCount(0);
            emptyStat.setBlockCount(0);
            emptyStat.setName(serviceName);
            mCurrentStats.put(serviceName, emptyStat);
            return emptyStat;
        }
    }

    public AlarmStats getAlarmStats(Context context, String alarmName)
    {
        Map<String, BaseStats> statChoice = null;
        loadStats(false);

        if (mCurrentStats.containsKey(alarmName)) {
            BaseStats base = mCurrentStats.get(alarmName);
            if (base instanceof AlarmStats)
                return (AlarmStats)base;
            else
                return null;
        } else {
            AlarmStats emptyStat = new AlarmStats(alarmName,"");
            emptyStat.setAllowedCount(0);
            emptyStat.setBlockCount(0);
            mCurrentStats.put(alarmName, emptyStat);
            return emptyStat;
        }
    }

    public String getWakelockDurationBlockedFormatted(Context context, int statType) {

        long totalDuration = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("WakelockBlockedDuration")) {
                    totalDuration = mGlobalStats.get("WakelockBlockedDuration");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);
            //Iterate over all stats and return the duration
            for (BaseStats curStat : mCurrentStats.values()) {
                if (curStat instanceof WakelockStats) {
                    totalDuration += ((WakelockStats) curStat).getBlockedDuration();
                }
            }

        }


        long years = TimeUnit.MILLISECONDS.toDays(totalDuration) / 365;
        totalDuration -= TimeUnit.DAYS.toMillis(years * 365);
        long days = TimeUnit.MILLISECONDS.toDays(totalDuration);
        totalDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(totalDuration);
        totalDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalDuration);
        totalDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalDuration);

        return getFormattedTime(years,days,hours,minutes,seconds);

    }

    public long getTotalAllowedWakelockCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("WakelockAllowedCount")) {
                    totalCount = mGlobalStats.get("WakelockAllowedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);

            for (BaseStats curStat : mCurrentStats.values()) {

                if (curStat instanceof WakelockStats)
                    totalCount += curStat.getAllowedCount();
            }
        }

        return totalCount;
    }

    public long getTotalAllowedServiceCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("ServiceAllowedCount")) {
                    totalCount = mGlobalStats.get("ServiceAllowedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);
            for (BaseStats curStat : mCurrentStats.values()) {
                if (curStat instanceof ServiceStats)
                    totalCount += curStat.getAllowedCount();
            }
        }

        return totalCount;
    }

    public long getTotalBlockWakelockCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("WakelockBlockedCount")) {
                    totalCount = mGlobalStats.get("WakelockBlockedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);

            for (BaseStats curStat : mCurrentStats.values()) {


                if (curStat instanceof WakelockStats)
                    totalCount += curStat.getBlockCount();
            }

        }

        return totalCount;
    }

    public long getTotalBlockServiceCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("ServiceBlockedCount")) {
                    totalCount = mGlobalStats.get("ServiceBlockedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);
            for (BaseStats curStat : mCurrentStats.values()) {
                if (curStat instanceof ServiceStats)
                    totalCount += curStat.getBlockCount();
            }

        }

        return totalCount;
    }

    public long getTotalAllowedAlarmCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("AlarmAllowedCount")) {
                    totalCount = mGlobalStats.get("AlarmAllowedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);

            for (BaseStats curStat : mCurrentStats.values()) {


                if (curStat instanceof AlarmStats)
                    totalCount += curStat.getAllowedCount();
            }
        }

        return totalCount;
    }

    public long getTotalBlockAlarmCount(Context context, int statType)
    {
        long totalCount = 0;
        if (statType == STAT_GLOBAL) {
            if (mGlobalStats != null) {
                if (mGlobalStats.containsKey("AlarmBlockedCount")) {
                    totalCount = mGlobalStats.get("AlarmBlockedCount");
                }
            }
        } else if (statType == STAT_CURRENT) {
            loadStats(false);

            for (BaseStats curStat : mCurrentStats.values()) {

                if (curStat instanceof AlarmStats)
                    totalCount += curStat.getBlockCount();
            }
        }
        return totalCount;
    }

    public void addInterimWakelock(Context context, InterimEvent toAdd)
    {
        //Load from disk and populate our stats
        loadStats(false);

        addInterimWakelock(toAdd, mCurrentStats);
        if (mGlobalParticipation) {
            addInterimWakelock(toAdd, mSincePushStats);
        }


    }

    private void addInterimWakelock(InterimEvent toAdd, Map<String, BaseStats> statChoice) {
        BaseStats combined = statChoice.get(toAdd.getName());
        if (combined == null)
        {
            combined = new WakelockStats(toAdd.getName(),toAdd.getUId());
        }
        if (combined instanceof WakelockStats) {
            ((WakelockStats)combined).addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());
            combined.setUid(toAdd.getUId());
            combined.incrementAllowedCount();
            statChoice.put(toAdd.getName(), combined);
        }
    }

    public void incrementWakelockBlock(Context context, String statName, int uId)
    {
        //Load from disk and populate our stats
        loadStats(false);

        incrementWakelockBlock(statName, mCurrentStats, uId);
        if (mGlobalParticipation) {
            incrementWakelockBlock(statName, mSincePushStats, uId);
        }

    }

    private void incrementWakelockBlock(String statName, Map<String, BaseStats> statChoice, int uId) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new WakelockStats(statName,uId);

        }
        combined.setUid(uId);
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

    public void incrementServiceBlock(Context context, String statName,  int uId)
    {
        //Load from disk and populate our stats
        loadStats(false);

        incrementServiceBlock(statName, mCurrentStats, uId);
        if (mGlobalParticipation) {
            incrementServiceBlock(statName, mSincePushStats, uId);
        }

    }

    private void incrementServiceBlock(String statName, Map<String, BaseStats> statChoice, int uId) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new ServiceStats(statName, uId);
                   }
        combined.setUid(uId);
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

   
    public void incrementAlarmBlock(Context context, String statName,String packageName) {
        //Load from disk and populate our stats
        loadStats(false);

        incrementAlarmBlock(statName, mCurrentStats,packageName);
        if (mGlobalParticipation) {
            incrementAlarmBlock(statName, mSincePushStats,packageName);
        }

    }

    private void incrementAlarmBlock(String statName, Map<String, BaseStats> statChoice,String packageName) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null) {
            combined = new AlarmStats(statName,packageName);
        }
        combined.setPackage(packageName);
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

    public void incrementAlarmAllowed(Context context, String statName,String packageName) {
        //Load from disk and populate our stats
        loadStats(false);
        incrementAlarmAllowed(statName, mCurrentStats,packageName);
        if (mGlobalParticipation) {
            incrementAlarmAllowed(statName, mSincePushStats,packageName);
        }

    }

    private void incrementAlarmAllowed(String statName, Map<String, BaseStats> statChoice,String packageName) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null) {
            combined = new AlarmStats(statName,packageName);
        }
        combined.setPackage(packageName);
        combined.incrementAllowedCount();
        statChoice.put(statName, combined);
    }
    public void incrementServiceAllowed(Context context, String statName, int uId)
    {
        //Load from disk and populate our stats
        loadStats(false);

        incrementServiceAllowed(statName, mCurrentStats, uId);
        if (mGlobalParticipation) {
            incrementServiceAllowed(statName, mSincePushStats, uId);
        }

    }
    private void incrementServiceAllowed(String statName, Map<String, BaseStats> statChoice, int uId) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new ServiceStats(statName, uId);
                   }
        combined.setUid(uId);
        combined.incrementAllowedCount();
        statChoice.put(statName, combined);
    }

    public void resetStats(Context context, String statName)
    {
        loadStats(false);
        mCurrentStats.remove(statName);
    }

    public void resetLocalStats(String statName)
    {
        mCurrentStats.remove(statName);
    }

    public void resetStats(Context context, int statType)
    {
        if (statType == STAT_CURRENT) {
            mCurrentStats.clear();
            mRunningSince = System.currentTimeMillis();
            clearStatFile(context, STATS_FILENAME_CURRENT);
        }
        else if (statType == STAT_PUSH) {
            mSincePushStats.clear();
            clearStatFile(context, STATS_FILENAME_PUSH);
        }
    }

    public void resetLocalStats(int statType)
    {
        if (statType == STAT_CURRENT) {
            if (mCurrentStats != null) {
                mCurrentStats.clear();
            }
            mRunningSince = System.currentTimeMillis();
        }
        else if (statType == STAT_PUSH) {
            if (mSincePushStats != null) {
                mSincePushStats.clear();
            }
        }
    }

    private boolean saveStatsToFile(String statFilename, Map statChoice) {
        String statFilename1 = statFilename;
        Map statChoice1 = statChoice;
        if (statChoice != null) {
            String filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;

            Object toWrite = statChoice;
            if (statChoice == mSincePushStats || statChoice == mCurrentStats) {
                //Wrap statchoice to contain metadata
                BaseStatsWrapper wrap = new BaseStatsWrapper();
                if (statChoice == mCurrentStats) {
                    wrap.mRunningSince = mRunningSince;
                }
                wrap.mStats = (Map)statChoice;
                toWrite = wrap;
            }

            try {
                File  outFile = new File(filename);
                if (!outFile.exists()) {
                    return true;
                }

                FileOutputStream out = new FileOutputStream(outFile);
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                objOut.writeObject(toWrite);
                objOut.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void clearStatFile(Context context, String filename) {
        try {
            FileOutputStream out = context.openFileOutput(filename, Activity.MODE_WORLD_WRITEABLE | Activity.MODE_WORLD_READABLE);
            out.write(0);
            out.close();
        } catch (IOException ignored) {

        }
    }

    public boolean saveNow(Context context) {
        Context context1 = context;

        if (saveStatsToFile(STATS_FILENAME_CURRENT, mCurrentStats)) {
            return true;
        }
        if (saveStatsToFile(STATS_FILENAME_PUSH, mSincePushStats)) {
            return true;
        }
        if (saveStatsToFile(STATS_FILENAME_GLOBAL, mGlobalStats)) {
            return true;
        }

        long now = SystemClock.elapsedRealtime();
        long timeSinceLastPush = now - mLastPush;
        long mPushTimeFrequency = 86400000;
        if (timeSinceLastPush > mPushTimeFrequency) {
            //Push now
            mLastPush = now;
            pushStatsToNetwork(context);
        }
        return false;
    }

    public void loadStats(boolean forceReload) {
        if (mCurrentStats == null || forceReload) {
            mCurrentStats = loadStatsFromFile(STATS_FILENAME_CURRENT);
            if (mCurrentStats == null)
                mCurrentStats = new HashMap<>();
        }
        if (mSincePushStats == null || forceReload) {
            mSincePushStats = loadStatsFromFile(STATS_FILENAME_PUSH);
            if (mSincePushStats == null)
                mSincePushStats = new HashMap<>();
        }
        if (mGlobalStats == null || forceReload) {
            mGlobalStats = loadStatsFromFile(STATS_FILENAME_GLOBAL);
            if (mGlobalStats == null)
                mGlobalStats = new HashMap<>();
        }
    }

    private Map loadStatsFromFile(String statFilename) {
        Map statChoice = null;
        String filename;
        try {
            filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;

            File inFile = new File(filename);
            if (inFile.exists())
                if (!inFile.canRead()) {
                } else {
                    if (inFile.exists() && inFile.canRead()) {
                        FileInputStream in = new FileInputStream(inFile);
                        ObjectInputStream objIn = new ObjectInputStream(in);
                        if (statFilename.equals(STATS_FILENAME_GLOBAL)) {
                            statChoice = (Map) objIn.readObject();
                        } else {
                            BaseStatsWrapper wrap = (BaseStatsWrapper) objIn.readObject();
                            //Validate all stats are of the correct basetype
                            for (BaseStats curStat : wrap.mStats.values()) {
                                //Check if this is castable.
                            }

                            //Looks good.
                            statChoice = wrap.mStats;

                            if (wrap.mRunningSince != -1) {
                                mRunningSince = wrap.mRunningSince;
                            }

                        }
                        objIn.close();
                        in.close();
                    } else {
                        statChoice = new HashMap<>();
                    }
                }
            else if (inFile.exists() && inFile.canRead()) {
                FileInputStream in = new FileInputStream(inFile);
                ObjectInputStream objIn = new ObjectInputStream(in);
                if (statFilename.equals(STATS_FILENAME_GLOBAL)) {
                    statChoice = (Map) objIn.readObject();
                } else {
                    BaseStatsWrapper wrap = (BaseStatsWrapper) objIn.readObject();
                    //Validate all stats are of the correct basetype
                    for (BaseStats curStat : wrap.mStats.values()) {
                        //Check if this is castable.
                    }

                    //Looks good.
                    statChoice = wrap.mStats;

                    if (wrap.mRunningSince != -1) {
                        mRunningSince = wrap.mRunningSince;
                    }

                }
                objIn.close();
                in.close();
            } else {
                statChoice = new HashMap<>();
            }

        } catch (FileNotFoundException e) {
            new Exception().printStackTrace();
            statChoice = new HashMap<>();
        } catch (ClassCastException | ClassNotFoundException | IOException e) {
            statChoice = new HashMap<>();
        }
        return statChoice;
    }

    private void pushStatsToNetwork(final Context context) {
        //Send a broadcast to the activity asking for this.
        Intent intent = new Intent(ActivityReceiver.PUSH_NETWORK_STATS);
        try {
            context.sendBroadcast(intent);
        } catch (IllegalStateException ignored) {
        }

    }

    public void pushStatsToNetworkInternal(final Context context) {
        //Are we allowed to?
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        mGlobalParticipation = prefs.getBoolean("global_participation", true);
        if (mGlobalParticipation) {
            //Serialize the collection to JSON
            loadStats(true);
            if (mSincePushStats != null) {
                if (mSincePushStats.size() > 0) {
                    final Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(mSincePushStats.values().toArray());

                    //Push the JSON to the server
                    NetworkHelper.sendToServer(json, URL_STATS, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 1) {

                                //Update global stats
                                String globalStats = msg.getData().getString("global_stats");
                                if (globalStats != null) {
                                    Type globalType = new TypeToken<Map<String, Long>>() {
                                    }.getType();
                                    mGlobalStats = gson.fromJson(globalStats, globalType);
                                }

                                //Reset the push collection, locally and in Xposed
                                Intent intent = new Intent(XposedReceiver.RESET_ACTION);
                                intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_PUSH);
                                try {
                                    context.sendBroadcast(intent);
                                } catch (IllegalStateException ignored) {

                                }
                                resetStats(context, STAT_PUSH);

                            }
                        }
                    });
                }
            }
        }
    }

    public void getStatsFromNetwork(final Context context, final Handler clientHandler) {
        //Push the JSON to the server
        //Are we allowed to?
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        if (prefs.getBoolean("global_participation", true)) {
            NetworkHelper.getFromServer(URL_STATS, new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
                        //Update global stats
                        String globalStats = msg.getData().getString("global_stats");
                        if (globalStats != null) {
                            Type globalType = new TypeToken<Map<String, Long>>() {
                            }.getType();
                            final Gson gson = new GsonBuilder().create();
                            mGlobalStats = gson.fromJson(globalStats, globalType);
                        }
                    }
                    clientHandler.sendEmptyMessage(1);
                }
            });
        }
    }

    public void refreshPrefs(XSharedPreferences prefs) {
        //Refresh the global options from the settings
        mGlobalParticipation = prefs.getBoolean("global_participation", true);
    }

    public void recreateFiles(Context context) {
        Log.d("Amplify: ", "Removing files in: " + STATS_DIRECTORY);
        new File(STATS_DIRECTORY + STATS_FILENAME_CURRENT).delete();
        new File(STATS_DIRECTORY + STATS_FILENAME_GLOBAL).delete();
        new File(STATS_DIRECTORY + STATS_FILENAME_PUSH).delete();
        createFiles(context);
    }

    public void createFiles(Context context) {
        try {
            if (!new File(STATS_DIRECTORY + STATS_FILENAME_CURRENT).exists()) {
                FileOutputStream out = context.openFileOutput(STATS_FILENAME_CURRENT, Activity.MODE_WORLD_WRITEABLE | Activity.MODE_WORLD_READABLE);
                out.write(0);
                out.close();
            }

            if (!new File(STATS_DIRECTORY + STATS_FILENAME_GLOBAL).exists()) {
                FileOutputStream out = context.openFileOutput(STATS_FILENAME_GLOBAL, Activity.MODE_WORLD_WRITEABLE | Activity.MODE_WORLD_READABLE);
                out.write(0);
                out.close();
            }

            if (!new File(STATS_DIRECTORY + STATS_FILENAME_PUSH).exists()) {
                FileOutputStream out = context.openFileOutput(STATS_FILENAME_PUSH, Activity.MODE_WORLD_WRITEABLE | Activity.MODE_WORLD_READABLE);
                out.write(0);
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("Amplify: ", "Writing creation version");
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("file_version", Wakelocks.FILE_VERSION);
        edit.apply();
        mRunningSince = System.currentTimeMillis();
    }
}
