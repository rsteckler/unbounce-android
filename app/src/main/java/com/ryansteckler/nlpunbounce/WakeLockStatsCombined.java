package com.ryansteckler.nlpunbounce;

import java.util.concurrent.TimeUnit;

/**
 * Created by rsteckler on 9/5/14.
 */
public class WakeLockStatsCombined {
    private long mDuration;
    private long mCount;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        synchronized (this) {
            this.mName = name;
        }
    }

    private String mName;

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long duration) {
        synchronized (this) {
            this.mDuration = duration;
        }
    }

    public long getCount() {
        return mCount;
    }

    public void setCount(long count) {
        synchronized (this) {
            this.mCount = count;
        }
    }

    public long incrementCount() {
        synchronized (this) {
            mCount++;
        }
        return mCount;
    }

    public long addDuration(long duration)
    {
        synchronized (this) {
            mDuration += duration;
        }
        return mDuration;
    }

    public String getDurationFormatted()
    {

        long days = TimeUnit.MILLISECONDS.toDays(mDuration);
        mDuration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(mDuration);
        mDuration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(mDuration);
        mDuration -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(mDuration);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" d ");
        sb.append(hours);
        sb.append(" h ");
        sb.append(minutes);
        sb.append(" m ");
        sb.append(seconds);
        sb.append(" s");

        return(sb.toString());
    }

}
