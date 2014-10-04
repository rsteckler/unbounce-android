package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by rsteckler on 10/3/14.
 */
public class BaseStatsWrapper implements Serializable{
    public long mRunningSince = -1;
    public HashMap<String, BaseStats> mStats = null;
}
