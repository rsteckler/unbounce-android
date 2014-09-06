package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/5/14.
 */
public class WakeLockStatsCombined {
    private long mDuration;
    private long mCount;

    public long getDuration() {
        return mDuration;
    }

    public void setDuration(long mDuration) {
        synchronized (this) {
            this.mDuration = mDuration;
        }
    }

    public long getCount() {
        return mCount;
    }

    public void setCount(long mCount) {
        synchronized (this) {
            this.mCount = mCount;
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


}
