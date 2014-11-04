package com.ryansteckler.nlpunbounce.models;

/**
 * Created by rsteckler on 9/5/14.
 */
public class InterimEvent implements java.io.Serializable {
    public long getTimeStarted() {
        return mTimeStarted;
    }

    public void setTimeStarted(long timeStarted) {
        this.mTimeStarted = timeStarted;
    }

    public long getTimeStopped() {
        return mTimeStopped;
    }

    public void setTimeStopped(long timeStopped) {
        this.mTimeStopped = timeStopped;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getUId() {
        return mUId;
    }

    public void setUId(int mUId) {
        this.mUId = mUId;
    }

    private long mTimeStarted;
    private long mTimeStopped;
    private String mName;
    private int mUId;


}
