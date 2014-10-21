package com.ryansteckler.nlpunbounce.models;

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
import com.ryansteckler.nlpunbounce.XposedReceiver;
import com.ryansteckler.nlpunbounce.helpers.NetworkHelper;

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
import java.util.concurrent.TimeUnit;

import de.robv.android.xposed.XSharedPreferences;

import static com.ryansteckler.nlpunbounce.helpers.LocaleHelper.getFormattedTime;

/**
 * Created by rsteckler on 9/5/14.
 */
public class UnbounceStatsCollection implements Serializable {

//    final private static String URL_STATS = "http://localhost:8080/stats";
//    final private static String URL_STATS = "http://www.unbounceme.com/stats";
    final private static String URL_STATS = "http://unbounce-server-1.appspot.com/stats";

    final private static String STATS_FILENAME_CURRENT = "nlpunbounce.stats";
    final private static String STATS_FILENAME_GLOBAL = "nlpunbounce.global.stats";
    final private static String STATS_FILENAME_PUSH = "nlpunbounce.push.stats";
    HashMap<String, BaseStats> mCurrentStats = null;
    HashMap<String, Long> mGlobalStats = null;
    HashMap<String, BaseStats> mSincePushStats = null;
    private long mRunningSince = 0;
    private boolean mGlobalParticipation = true;

    public static final int STAT_CURRENT = 0;
    public static final int STAT_GLOBAL = 1;
    public static final int STAT_PUSH = 2;

    long mLastSave = 0;
    long mLastPush = 0;
    long mSaveTimeFrequency = 600000; //Save every ten minutes
    long mPushTimeFrequency = 86400000; //Push every 24 hours

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
    }

    public void populateSerializableStats(HashMap<String, BaseStats> source, int statType, long runningSince)
    {
        if (statType == STAT_CURRENT) {
            mCurrentStats = source;
            mRunningSince = runningSince;
        } else if (statType == STAT_PUSH) {
            mSincePushStats = source;
        }
    }

    public HashMap<String, BaseStats> getSerializableStats(int statType)
    {
        if (statType == STAT_CURRENT) {
            return mCurrentStats;
        } else if (statType == STAT_PUSH) {
            return mSincePushStats;
        }
        return null;
    }

    public Long getRunningSince() {
        return mRunningSince;
    }

    public String getRunningSinceFormatted() {
        long now = SystemClock.elapsedRealtime();
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

        /*StringBuilder sb = new StringBuilder(64);
        if (years > 0) {
            sb.append(years);
            sb.append(" y ");
        }
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");

        return (sb.toString());*/

    }

    public ArrayList<AlarmStats> toAlarmArrayList(Context context)
    {
        loadStats(context);
        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());
        ArrayList<AlarmStats> alarms = new ArrayList<AlarmStats>();
        Iterator<BaseStats> iter = bases.iterator();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        for (BaseStats curStat : bases) {

            if (curStat instanceof AlarmStats) {
                alarms.add((AlarmStats) curStat);
            }
        }

        return alarms;
    }

    public ArrayList<WakelockStats> toWakelockArrayList(Context context) {
        loadStats(context);
        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());
        ArrayList<WakelockStats> wakelocks = new ArrayList<WakelockStats>();


        //TODO:  There are WAY better ways to do this, other than copying arrays.
        for (BaseStats curStat : bases) {

            if (curStat instanceof WakelockStats) {
                wakelocks.add((WakelockStats) curStat);
            }
        }

        return wakelocks;
    }

    public ArrayList<ServiceStats> toServiceArrayList(Context context)
    {
        loadStats(context);
        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mCurrentStats.values());
        ArrayList<ServiceStats> services = new ArrayList<ServiceStats>();
        Iterator<BaseStats> iter = bases.iterator();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof ServiceStats) {
                services.add((ServiceStats)curStat);
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
            HashMap<String, BaseStats> statChoice = null;
            loadStats(context);

            //Iterate over all wakelocks and return the duration
            Iterator<BaseStats> iter = mCurrentStats.values().iterator();
            while (iter.hasNext())
            {
                BaseStats curStat = iter.next();
                if (curStat instanceof WakelockStats) {
                    totalDuration += ((WakelockStats)(curStat)).getAllowedDuration();
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

        /*StringBuilder sb = new StringBuilder(64);
        if (years > 0) {
            sb.append(years);
            sb.append(" y ");
        }
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");


        return (sb.toString());*/
    }

    public WakelockStats getWakelockStats(Context context, String wakelockName)
    {
        loadStats(context);
        if (mCurrentStats.containsKey(wakelockName)) {
            BaseStats base = mCurrentStats.get(wakelockName);
            if (base instanceof WakelockStats)
                return (WakelockStats)base;
            else
                return null;
        }
        else {
            WakelockStats emptyStat = new WakelockStats();
            emptyStat.setAllowedCount(0);
            emptyStat.setAllowedDuration(0);
            emptyStat.setBlockCount(0);
            emptyStat.setName(wakelockName);
            mCurrentStats.put(wakelockName, emptyStat);
            saveNow(context);
            return emptyStat;
        }
    }

    public ServiceStats getServiceStats(Context context, String serviceName)
    {
        loadStats(context);
        if (mCurrentStats.containsKey(serviceName)) {
            BaseStats base = mCurrentStats.get(serviceName);
            if (base instanceof ServiceStats)
                return (ServiceStats)base;
            else
                return null;
        }
        else {
            ServiceStats emptyStat = new ServiceStats();
            emptyStat.setAllowedCount(0);
            emptyStat.setBlockCount(0);
            emptyStat.setName(serviceName);
            mCurrentStats.put(serviceName, emptyStat);
            saveNow(context);
            return emptyStat;
        }
    }

    public AlarmStats getAlarmStats(Context context, String alarmName)
    {
        HashMap<String, BaseStats> statChoice = null;
        loadStats(context);

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
            saveNow(context);
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
            loadStats(context);
            //Iterate over all stats and return the duration
            Iterator<BaseStats> iter = mCurrentStats.values().iterator();
            while (iter.hasNext())
            {
                BaseStats curStat = iter.next();
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

        /*StringBuilder sb = new StringBuilder(64);
        if (years > 0) {
            sb.append(years);
            sb.append(" y ");
        }
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");

        return (sb.toString());*/
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
            loadStats(context);

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
            loadStats(context);
            Iterator<BaseStats> iter = mCurrentStats.values().iterator();
            while (iter.hasNext())
            {
                BaseStats curStat = iter.next();
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
            loadStats(context);

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
            loadStats(context);
            Iterator<BaseStats> iter = mCurrentStats.values().iterator();
            while (iter.hasNext())
            {
                BaseStats curStat = iter.next();
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
            loadStats(context);

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
            loadStats(context);

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
        loadStats(context);

        addInterimWakelock(toAdd, mCurrentStats);
        if (mGlobalParticipation) {
            addInterimWakelock(toAdd, mSincePushStats);
        }


    }

    private void addInterimWakelock(InterimEvent toAdd, HashMap<String, BaseStats> statChoice) {
        BaseStats combined = statChoice.get(toAdd.getName());
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(toAdd.getName());
        }
        if (combined instanceof WakelockStats) {
            ((WakelockStats)combined).addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());
            combined.incrementAllowedCount();
            statChoice.put(toAdd.getName(), combined);
        }
    }

    public void incrementWakelockBlock(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        incrementWakelockBlock(statName, mCurrentStats);
        if (mGlobalParticipation) {
            incrementWakelockBlock(statName, mSincePushStats);
        }

    }

    private void incrementWakelockBlock(String statName, HashMap<String, BaseStats> statChoice) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(statName);
        }
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

    public void incrementServiceBlock(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        incrementServiceBlock(statName, mCurrentStats);
        if (mGlobalParticipation) {
            incrementServiceBlock(statName, mSincePushStats);
        }

    }

    private void incrementServiceBlock(String statName, HashMap<String, BaseStats> statChoice) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new ServiceStats();
            combined.setName(statName);
        }
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

   
    public void incrementAlarmBlock(Context context, String statName,String packageName) {
        //Load from disk and populate our stats
        loadStats(context);

        incrementAlarmBlock(statName, mCurrentStats,packageName);
        if (mGlobalParticipation) {
            incrementAlarmBlock(statName, mSincePushStats,packageName);
        }

    }

    private void incrementAlarmBlock(String statName, HashMap<String, BaseStats> statChoice,String packageName) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null) {
            combined = new AlarmStats(statName,packageName);
        }
        combined.setmPackage(packageName);
        combined.incrementBlockCount();
        statChoice.put(statName, combined);
    }

    public void incrementAlarmAllowed(Context context, String statName,String packageName) {
        //Load from disk and populate our stats
        loadStats(context);
        incrementAlarmAllowed(statName, mCurrentStats,packageName);
        if (mGlobalParticipation) {
            incrementAlarmAllowed(statName, mSincePushStats,packageName);
        }

    }

    private void incrementAlarmAllowed(String statName, HashMap<String, BaseStats> statChoice,String packageName) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null) {
            combined = new AlarmStats(statName,packageName);
        }
        combined.setmPackage(packageName);
        combined.incrementAllowedCount();
        statChoice.put(statName, combined);
    }
    public void incrementServiceAllowed(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        incrementServiceAllowed(statName, mCurrentStats);
        if (mGlobalParticipation) {
            incrementServiceAllowed(statName, mSincePushStats);
        }

    }
    private void incrementServiceAllowed(String statName, HashMap<String, BaseStats> statChoice) {
        BaseStats combined = statChoice.get(statName);
        if (combined == null)
        {
            combined = new ServiceStats();
            combined.setName(statName);
        }
        combined.incrementAllowedCount();
        statChoice.put(statName, combined);
    }
    public void resetStats(Context context, String statName)
    {
        loadStats(context);
        mCurrentStats.remove(statName);
        saveNow(context);
    }

    public void resetLocalStats(String statName)
    {
        mCurrentStats.remove(statName);
    }

    public void resetStats(Context context, int statType)
    {
        if (statType == STAT_CURRENT) {
            mCurrentStats.clear();
            mRunningSince = SystemClock.elapsedRealtime();
            clearStatFile(STATS_FILENAME_CURRENT);
        }
        else if (statType == STAT_PUSH) {
            mSincePushStats.clear();
            clearStatFile(STATS_FILENAME_PUSH);
        }
    }

    public void resetLocalStats(int statType)
    {
        if (statType == STAT_CURRENT) {
            mCurrentStats.clear();
            mRunningSince = SystemClock.elapsedRealtime();
        }
        else if (statType == STAT_PUSH) {
            mSincePushStats.clear();
        }
    }

    public void saveIfNeeded(Context context) {
        //Find out how long since our last save
        synchronized (this) {
            final long now = SystemClock.elapsedRealtime();
            long timeSinceLastSave = now - mLastSave;

            if (timeSinceLastSave > mSaveTimeFrequency) {
                //Save now
                mLastSave = now;

                saveStatsToFile(STATS_FILENAME_CURRENT, mCurrentStats);
                saveStatsToFile(STATS_FILENAME_PUSH, mSincePushStats);
                saveStatsToFile(STATS_FILENAME_GLOBAL, mGlobalStats);

                long timeSinceLastPush = now - mLastPush;

                if (timeSinceLastPush > mPushTimeFrequency || (mGlobalStats != null && mGlobalStats.size() == 0)) {
                    //Push now
                    pushStatsToNetwork(context);
                }
            }
        }
    }

    private void saveStatsToFile(String statFilename, HashMap statChoice) {
        if (statChoice != null) {
            String filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;

            Object toWrite = statChoice;
            if (statChoice == mSincePushStats || statChoice == mCurrentStats) {
                //Wrap statchoice to contain metadata
                BaseStatsWrapper wrap = new BaseStatsWrapper();
                if (statChoice == mCurrentStats) {
                    wrap.mRunningSince = mRunningSince;
                }
                wrap.mStats = statChoice;
                toWrite = wrap;
            }

            try {

                File outFile = new File(filename);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                outFile.setReadable(true, false);
                FileOutputStream out = new FileOutputStream(outFile);
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                objOut.writeObject(toWrite);
                objOut.close();
                out.close();
                outFile.setReadable(true, false);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearStatFile(String filename) {
        File toClear = new File(filename);
        toClear.delete();
    }

    public void saveNow(Context context) {
        mLastSave = 0; //force a save
        saveIfNeeded(context);
    }

    private void loadStats(Context context) {
        if (mCurrentStats == null) {
            mCurrentStats = loadStatsFromFile(STATS_FILENAME_CURRENT);
            if (mCurrentStats == null)
                mCurrentStats = new HashMap<String, BaseStats>();
        }
        if (mSincePushStats == null) {
            mSincePushStats = loadStatsFromFile(STATS_FILENAME_PUSH);
            if (mSincePushStats == null)
                mSincePushStats = new HashMap<String, BaseStats>();
        }
        if (mGlobalStats == null) {
            mGlobalStats = loadStatsFromFile(STATS_FILENAME_GLOBAL);
            if (mGlobalStats == null)
                mGlobalStats = new HashMap<String, Long>();
        }
    }

    private HashMap loadStatsFromFile(String statFilename) {
        HashMap statChoice = null;
        String filename = "";
        try {
            filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + statFilename;
            Log.d("Unbounce:WLSC", "Loading file: " + filename);

            File inFile = new File(filename);
            if (inFile.exists() && !inFile.canRead()) {
                Log.d("Unbounce:WLSC", "Can't load file yet.  Skipping...");
            }
            else if (inFile.exists() && inFile.canRead()) {
                Log.d("Unbounce:WLSC", "Ready to load file.");
                FileInputStream in = new FileInputStream(inFile);
                ObjectInputStream objIn = new ObjectInputStream(in);
                if (statFilename == STATS_FILENAME_GLOBAL) {
                    statChoice = (HashMap)objIn.readObject();
                } else {
                    BaseStatsWrapper wrap = (BaseStatsWrapper) objIn.readObject();
                    //Validate all stats are of the correct basetype
                    Iterator<BaseStats> iter = wrap.mStats.values().iterator();
                    while (iter.hasNext()) {
                        //Check if this is castable.
                        BaseStats curStat = iter.next();
                    }

                    //Looks good.
                    statChoice = wrap.mStats;

                    if (wrap.mRunningSince != -1) {
                        mRunningSince = wrap.mRunningSince;
                    }

                }
                objIn.close();
                in.close();
            }
            else {
                Log.d("Unbounce:WLSC", "Unknown file state.  Resetting.");
                statChoice = new HashMap<String, BaseStats>();
            }

        } catch (FileNotFoundException e) {
            Log.d("Unbounce:WLSC", "Please send this log to Ryan.  It's a problem.  FNF: " + filename);
            new Exception().printStackTrace();
            statChoice = new HashMap<String, BaseStats>();
        } catch (StreamCorruptedException e) {
            statChoice = new HashMap<String, BaseStats>();
        } catch (IOException e) {
            statChoice = new HashMap<String, BaseStats>();
        } catch (ClassNotFoundException e) {
            statChoice = new HashMap<String, BaseStats>();
        } catch (ClassCastException e) {
            statChoice = new HashMap<String, BaseStats>();
        }
        return statChoice;
    }

    public void loadGlobalStats() {
        //Download the file from the server
        //Save it to the local system
    }

    public void pushStatsToNetwork(final Context context) {
        //Are we allowed to?
        if (mGlobalParticipation) {
            //Serialize the collection to JSON
            if (mSincePushStats != null) {
                if (mSincePushStats.size() > 0) {
                    final Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(mSincePushStats.values().toArray());

                    //Push the JSON to the server
                    NetworkHelper.sendToServer("DeviceStats", json, URL_STATS, new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            if (msg.what == 1) {
                                mLastPush = SystemClock.elapsedRealtime();

                                //Update global stats
                                String globalStats = msg.getData().getString("global_stats");
                                if (globalStats != null) {
                                    Type globalType = new TypeToken<HashMap<String, Long>>() {
                                    }.getType();
                                    mGlobalStats = gson.fromJson(globalStats, globalType);
                                }

                                //Reset the push collection, locally and in Xposed
                                Intent intent = new Intent(XposedReceiver.RESET_ACTION);
                                intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_PUSH);
                                try {
                                    context.sendBroadcast(intent);
                                } catch (IllegalStateException ise) {

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
                            Type globalType = new TypeToken<HashMap<String, Long>>() {
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
}
