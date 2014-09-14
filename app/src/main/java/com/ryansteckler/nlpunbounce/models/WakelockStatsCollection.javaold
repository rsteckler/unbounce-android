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
public class WakelockStatsCollection implements Serializable {

    final private static String STATS_FILENAME = "wakelocks.stats";
    HashMap<String, WakelockStats> mStats = null;
    long mLastSave = 0;
    long mSaveTimeFrequency = 15000; //Save every 15 seconds

    private WakelockStatsCollection(){};
    private static WakelockStatsCollection mInstance = null;

    public static WakelockStatsCollection getInstance()
    {
        if (mInstance == null)
        {
            mInstance = new WakelockStatsCollection();
        }
        return mInstance;
    }

    public void populateSerializableStats(HashMap<String, WakelockStats> source)
    {
        mStats = source;
    }

    public HashMap<String, WakelockStats> getSerializableStats()
    {
        return mStats;
    }

    public ArrayList<WakelockStats> toArrayList(Context context)
    {
        loadStats(context);
        return new ArrayList<WakelockStats>(mStats.values());
    }

    public String getDurationAllowedFormatted(Context context) {
        //Iterate over all wakelocks and return the duration
        loadStats(context);

        long totalDuration = 0;
        Iterator<WakelockStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            totalDuration += curStat.getAllowedDuration();
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

    public WakelockStats getStat(Context context, String wakelockName)
    {
        loadStats(context);
        if (mStats.containsKey(wakelockName)) {
            return mStats.get(wakelockName);
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

    public String getDurationBlockedFormatted(Context context) {
        loadStats(context);
        //Iterate over all wakelocks and return the duration
        long totalDuration = 0;
        Iterator<WakelockStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            totalDuration += curStat.getBlockedDuration();
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

    public long getTotalAllowedCount(Context context)
    {
        loadStats(context);
        long totalCount = 0;
        Iterator<WakelockStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            totalCount += curStat.getAllowedCount();
        }
        return totalCount;
    }

    public long getTotalBlockCount(Context context)
    {
        loadStats(context);

        long totalCount = 0;
        Iterator<WakelockStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            totalCount += curStat.getBlockCount();
        }
        return totalCount;
    }

    public String getDurationAllowedFormatted(Context context, String wakelockName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        WakelockStats stat = mStats.get(wakelockName);
        return stat.getDurationAllowedFormatted();
    }

    public void addInterimWakelock(Context context, InterimWakelock toAdd)
    {
        //Load from disk and populate our stats
        loadStats(context);

        WakelockStats combined = mStats.get(toAdd.getName());
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(toAdd.getName());
        }
        combined.addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());
        combined.incrementAllowedCount();
        mStats.put(toAdd.getName(), combined);

        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void incrementWakelockBlock(Context context, String wakelockName)
    {
        //Load from disk and populate our stats
        loadStats(context);

        WakelockStats combined = mStats.get(wakelockName);
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(wakelockName);
        }
        combined.incrementBlockCount();
        mStats.put(wakelockName, combined);

        //We explicitly don't save here because this method "happens" to only be called by the
        //hook process, which shouldn't save the file because it's root, and our app needs to read the file.
        //Todo:  In the future, we should provide a parameter so the hook process can call this specifying a no-save
        //condition.
        //        saveIfNeeded(context);

    }

    public void resetStats(Context context, String wakelockName)
    {
        loadStats(context);
        mStats.remove(wakelockName);
        saveNow(context);
    }

    public void resetStats(Context context)
    {
        loadStats(context);
        mStats.clear();
        saveNow(context);
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
                    mStats = (HashMap<String, WakelockStats>) objIn.readObject();
                    objIn.close();
                    in.close();
                }
                else {
                    Log.d("NlpUnbounce:WLSC", "Unknown file state.  Resetting.");
                    mStats = new HashMap<String, WakelockStats>();
                }

            } catch (FileNotFoundException e) {
                Log.d("NlpUnbounce:WLSC", "Please send this log to Ryan.  It's a problem.  FNF: " + filename);
                new Exception().printStackTrace();
                mStats = new HashMap<String, WakelockStats>();
            } catch (StreamCorruptedException e) {
                mStats = new HashMap<String, WakelockStats>();
            } catch (IOException e) {
                mStats = new HashMap<String, WakelockStats>();
            } catch (ClassNotFoundException e) {
                mStats = new HashMap<String, WakelockStats>();
            }
        }
    }

}
