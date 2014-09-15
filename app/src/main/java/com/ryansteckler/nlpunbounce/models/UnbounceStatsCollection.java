package com.ryansteckler.nlpunbounce.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsteckler on 9/5/14.
 */
public class UnbounceStatsCollection implements Serializable {

    final private static String STATS_FILENAME = "nlpunbounce.stats";
    HashMap<String, BaseStats> mStats = null;
    long mLastSave = 0;
    long mSaveTimeFrequency = 15000; //Save every 15 seconds

    private UnbounceStatsCollection(){};
    private static UnbounceStatsCollection mInstance = null;

    public static UnbounceStatsCollection getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new UnbounceStatsCollection();
        }
        return mInstance;
    }

    public void populateSerializableStats(HashMap<String, BaseStats> source)
    {
        mStats = source;
    }

    public HashMap<String, BaseStats> getSerializableStats()
    {
        return mStats;
    }

    public ArrayList<AlarmStats> toAlarmArrayList(Context context)
    {
        loadStats(context);
        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mStats.values());
        ArrayList<AlarmStats> alarms = new ArrayList<AlarmStats>();
        Iterator<BaseStats> iter = bases.iterator();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof AlarmStats) {
                alarms.add((AlarmStats)curStat);
            }
        }

        return alarms;
    }

    public ArrayList<WakelockStats> toWakelockArrayList(Context context)
    {
        loadStats(context);
        ArrayList<BaseStats> bases = new ArrayList<BaseStats>(mStats.values());
        ArrayList<WakelockStats> wakelocks = new ArrayList<WakelockStats>();
        Iterator<BaseStats> iter = bases.iterator();

        //TODO:  There are WAY better ways to do this, other than copying arrays.
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof WakelockStats) {
                wakelocks.add((WakelockStats)curStat);
            }
        }

        return wakelocks;
    }

    public String getDurationAllowedFormatted(Context context) {
        //Iterate over all wakelocks and return the duration
        loadStats(context);

        long totalDuration = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof WakelockStats) {
                totalDuration += ((WakelockStats)(curStat)).getAllowedDuration();
            }
        }

        long days = TimeUnit.MILLISECONDS.toDays(totalDuration);
        totalDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(totalDuration);
        totalDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalDuration);
        totalDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalDuration);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");

        return (sb.toString());
    }

    public WakelockStats getWakelockStats(Context context, String wakelockName)
    {
        loadStats(context);
        if (mStats.containsKey(wakelockName)) {
            BaseStats base = mStats.get(wakelockName);
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
            mStats.put(wakelockName, emptyStat);
            saveNow(context);
            return emptyStat;
        }
    }

    public AlarmStats getAlarmStats(Context context, String alarmName)
    {
        loadStats(context);
        if (mStats.containsKey(alarmName)) {
            BaseStats base = mStats.get(alarmName);
            if (base instanceof AlarmStats)
                return (AlarmStats)base;
            else
                return null;
        }
        else {
            AlarmStats emptyStat = new AlarmStats();
            emptyStat.setAllowedCount(0);
            emptyStat.setBlockCount(0);
            emptyStat.setName(alarmName);
            mStats.put(alarmName, emptyStat);
            saveNow(context);
            return emptyStat;
        }
    }

    public String getDurationBlockedFormatted(Context context) {
        loadStats(context);
        //Iterate over all wakelocks and return the duration
        long totalDuration = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof WakelockStats)
                totalDuration += ((WakelockStats)curStat).getBlockedDuration();
        }

        long days = TimeUnit.MILLISECONDS.toDays(totalDuration);
        totalDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(totalDuration);
        totalDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(totalDuration);
        totalDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(totalDuration);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");

        return (sb.toString());
    }

    public long getTotalAllowedWakelockCount(Context context)
    {
        loadStats(context);
        long totalCount = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof WakelockStats)
                totalCount += curStat.getAllowedCount();
        }
        return totalCount;
    }

    public long getTotalBlockWakelockCount(Context context)
    {
        loadStats(context);

        long totalCount = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof WakelockStats)
                totalCount += curStat.getBlockCount();
        }
        return totalCount;
    }

    public long getTotalAllowedAlarmCount(Context context)
    {
        loadStats(context);
        long totalCount = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof AlarmStats)
                totalCount += curStat.getAllowedCount();
        }
        return totalCount;
    }

    public long getTotalBlockAlarmCount(Context context)
    {
        loadStats(context);

        long totalCount = 0;
        Iterator<BaseStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            BaseStats curStat = iter.next();
            if (curStat instanceof AlarmStats)
                totalCount += curStat.getBlockCount();
        }
        return totalCount;
    }

    public String getDurationAllowedFormatted(Context context, String wakelockName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        BaseStats stat = mStats.get(wakelockName);
        if (stat instanceof WakelockStats) {
            return ((WakelockStats)stat).getDurationAllowedFormatted();
        }
        else {
            return "";
        }
    }

    public void addInterimWakelock(Context context, InterimWakelock toAdd)
    {
        //Load from disk and populate our stats
        loadStats(context);

        BaseStats combined = mStats.get(toAdd.getName());
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(toAdd.getName());
        }
        if (combined instanceof WakelockStats) {
            ((WakelockStats)combined).addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());
            combined.incrementAllowedCount();
            mStats.put(toAdd.getName(), combined);
        }
        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void incrementWakelockBlock(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        BaseStats combined = mStats.get(statName);
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(statName);
        }
        combined.incrementBlockCount();
        mStats.put(statName, combined);

        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void incrementAlarmBlock(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        BaseStats combined = mStats.get(statName);
        if (combined == null)
        {
            combined = new AlarmStats();
            combined.setName(statName);
        }
        combined.incrementBlockCount();
        mStats.put(statName, combined);

        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void incrementAlarmAllowed(Context context, String statName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        BaseStats combined = mStats.get(statName);
        if (combined == null)
        {
            combined = new AlarmStats();
            combined.setName(statName);
        }
        combined.incrementAllowedCount();
        mStats.put(statName, combined);

        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void resetStats(Context context, String statName)
    {
        loadStats(context);
        mStats.remove(statName);
        saveNow(context);
    }

    public void resetLocalStats(String statName)
    {
        mStats.remove(statName);
    }

    public void resetStats(Context context)
    {
        loadStats(context);
        mStats.clear();
        saveNow(context);
    }

    public void resetLocalStats()
    {
        mStats.clear();
    }

    public void saveIfNeeded(Context context) {
        //Find out how long since our last save
        final long now = SystemClock.elapsedRealtime();
        long timeSinceLastSave = now - mLastSave;

        if (timeSinceLastSave > mSaveTimeFrequency) {
            //Save now
            mLastSave = now;

            String filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + STATS_FILENAME;
            try {

                File outFile = new File(filename);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                outFile.setReadable(true, false);
                FileOutputStream out = new FileOutputStream(outFile);
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                objOut.writeObject(mStats);
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

    public void saveNow(Context context) {
        mLastSave = 0; //force a save
        saveIfNeeded(context);
    }

    private void loadStats(Context context) {
        if (mStats == null) {
            String filename = "";
            try {
                filename = Environment.getDataDirectory() + "/data/" + "com.ryansteckler.nlpunbounce" + "/files/" + STATS_FILENAME;
                File inFile = new File(filename);
                if (inFile.exists() && !inFile.canRead()) {
                    Log.d("NlpUnbounce:WLSC", "Can't load file yet.  Skipping...");
                }
                else if (inFile.exists() && inFile.canRead()) {
                    Log.d("NlpUnbounce:WLSC", "Ready to load file.");
                    FileInputStream in = new FileInputStream(inFile);
                    ObjectInputStream objIn = new ObjectInputStream(in);
                    mStats = (HashMap<String, BaseStats>) objIn.readObject();
                    objIn.close();
                    in.close();
                }
                else {
                    Log.d("NlpUnbounce:WLSC", "Unknown file state.  Resetting.");
                    mStats = new HashMap<String, BaseStats>();
                }

            } catch (FileNotFoundException e) {
                Log.d("NlpUnbounce:WLSC", "Please send this log to Ryan.  It's a problem.  FNF: " + filename);
                new Exception().printStackTrace();
                mStats = new HashMap<String, BaseStats>();
            } catch (StreamCorruptedException e) {
                mStats = new HashMap<String, BaseStats>();
            } catch (IOException e) {
                mStats = new HashMap<String, BaseStats>();
            } catch (ClassNotFoundException e) {
                mStats = new HashMap<String, BaseStats>();
            }
        }
    }

}
