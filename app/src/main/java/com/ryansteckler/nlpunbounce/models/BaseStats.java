package com.ryansteckler.nlpunbounce.models;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by rsteckler on 9/10/14.
 */
public abstract class BaseStats implements Serializable {
    private long mAllowedCount;
    private long mBlockCount;
    private String mName;
    private boolean mBlockingEnabled;

    private String mType;
    private String mPackage;

    private int uid = -99;




    private String mDerivedPackageName;

    public String getPackage() {
        return mPackage;
    }

    public void setPackage(String mPackage) {
        this.mPackage = mPackage;
    }

    public String getType() {
        return mType;
    }

    protected void setType(String type) {
        mType = type;
    }

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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public abstract String getDerivedPackageName(Context ctx);

    protected void setDerivedPackageName(String mDerivedPackageName) {
        this.mDerivedPackageName = mDerivedPackageName;
    }
    protected String getDerivedPackageName() {
        return mDerivedPackageName;
    }

}
