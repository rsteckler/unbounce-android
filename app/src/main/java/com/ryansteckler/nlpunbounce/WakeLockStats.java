package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/5/14.
 */
public class WakeLockStats implements java.io.Serializable{
    public long getTimeStarted() {
        return mTimeStarted;
    }

    public void setTimeStarted(long mTimeStarted) {
        this.mTimeStarted = mTimeStarted;
    }

    public long getTimeStopped() {
        return mTimeStopped;
    }

    public void setTimeStopped(long mTimeStopped) {
        this.mTimeStopped = mTimeStopped;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    private long mTimeStarted;
    private long mTimeStopped;
    private String mName;

}
