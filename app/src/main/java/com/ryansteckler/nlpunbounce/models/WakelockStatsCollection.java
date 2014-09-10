package com.ryansteckler.nlpunbounce.models;

import android.content.Context;
import android.os.SystemClock;

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
    long mSaveTimeFrequency = 600000; //10 minutes

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

    public ArrayList<WakelockStats> toArrayList()
    {
        return new ArrayList<WakelockStats>(mStats.values());
    }

    public String getDurationAllowedFormatted(Context context) {
        //Iterate over all wakelocks and return the duration
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

    public String getDurationBlockedFormatted(Context context) {
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

    public long getTotalAllowedCount()
    {
        long totalCount = 0;
        Iterator<WakelockStats> iter = mStats.values().iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            totalCount += curStat.getAllowedCount();
        }
        return totalCount;
    }

    public long getTotalBlockCount()
    {
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
        if (mStats == null)
        {
            //Load from disk and populate our stats
            loadStats(context);
        }

        WakelockStats stat = mStats.get(wakelockName);
        return stat.getDurationAllowedFormatted();
    }

    public void addInterimWakelock(Context context, InterimWakelock toAdd)
    {
        if (mStats == null)
        {
            //Load from disk and populate our stats
            loadStats(context);
        }

        WakelockStats combined = mStats.get(toAdd.getName());
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(toAdd.getName());
        }
        combined.addDurationAllowed(toAdd.getTimeStopped() - toAdd.getTimeStarted());
        combined.incrementAllowedCount();
        mStats.put(toAdd.getName(), combined);

        saveIfNeeded(context);

    }

    public void incrementWakelockBlock(Context context, String wakelockName)
    {
        if (mStats == null)
        {
            //Load from disk and populate our stats
            loadStats(context);
        }

        WakelockStats combined = mStats.get(wakelockName);
        if (combined == null)
        {
            combined = new WakelockStats();
            combined.setName(wakelockName);
        }
        combined.incrementBlockCount();
        mStats.put(wakelockName, combined);

        saveIfNeeded(context);

    }

    private void saveIfNeeded(Context context) {
        //Find out how long since our last save
        final long now = SystemClock.elapsedRealtime();
        long timeSinceLastSave = now - mLastSave;

        if (timeSinceLastSave > mSaveTimeFrequency) {
            //Save now
            mLastSave = now;

            try {
                FileOutputStream out = context.openFileOutput(STATS_FILENAME, Context.MODE_WORLD_WRITEABLE);
                ObjectOutputStream objOut = new ObjectOutputStream(out);
                objOut.writeObject(mStats);
                objOut.close();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void loadStats(Context context) {
        try {
            FileInputStream in = context.openFileInput(STATS_FILENAME);
            ObjectInputStream objIn = new ObjectInputStream(in);
            mStats = (HashMap<String, WakelockStats>)objIn.readObject();
            objIn.close();
            in.close();
        } catch (FileNotFoundException e) {
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
