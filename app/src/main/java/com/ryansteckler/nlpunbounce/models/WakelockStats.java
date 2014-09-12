package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsteckler on 9/10/14.
 */
public class WakelockStats implements Serializable {
    private long mAllowedDuration;
    private long mAllowedCount;
    private long mBlockCount;
    private String mName;
    private boolean mBlockingEnabled;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        synchronized (this) {
            this.mName = name;
        }
    }

    public boolean getBlockingEnabled() {
        return mBlockingEnabled;
    }

    public void setBlockingEnabled(boolean blockingEnabled) {
        synchronized (this) {
            this.mBlockingEnabled = blockingEnabled;
        }
    }

    public long getAllowedDuration() {
        return mAllowedDuration;
    }

    public void setAllowedDuration(long duration) {
        synchronized (this) {
            this.mAllowedDuration = duration;
        }
    }

    public long getAllowedCount() {
        return mAllowedCount;
    }

    public void setAllowedCount(long count) {
        synchronized (this) {
            this.mAllowedCount = count;
        }
    }

    public long incrementAllowedCount() {
        synchronized (this) {
            mAllowedCount++;
        }
        return mAllowedCount;
    }

    public long getBlockCount() {
        return mBlockCount;
    }

    public void setBlockCount(long count) {
        synchronized (this) {
            this.mBlockCount = count;
        }
    }

    public long incrementBlockCount() {
        synchronized (this) {
            mBlockCount++;
        }
        return mBlockCount;
    }

    public long addDurationAllowed(long duration)
    {
        synchronized (this) {
            mAllowedDuration += duration;
        }
        return mAllowedDuration;
    }

    public String getDurationAllowedFormatted() {
        long days = TimeUnit.MILLISECONDS.toDays(mAllowedDuration);
        mAllowedDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(mAllowedDuration);
        mAllowedDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mAllowedDuration);
        mAllowedDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mAllowedDuration);

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

    public long getBlockedDuration()
    {
        //Determine the average alive time of a wakelock.
        if (mAllowedCount == 0)
            return 0;
        long averageTime = mAllowedDuration / mAllowedCount;
        //Now multiply that by the number we've blocked.
        long blockedTime = averageTime * mBlockCount;

        return blockedTime;
    }

    public String getDurationBlockedFormatted() {

        long blockedTime = getBlockedDuration();

        long days = TimeUnit.MILLISECONDS.toDays(blockedTime);
        blockedTime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(blockedTime);
        blockedTime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(blockedTime);
        blockedTime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(blockedTime);

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


}
