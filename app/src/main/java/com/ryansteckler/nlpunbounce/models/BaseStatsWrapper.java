package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by rsteckler on 10/3/14.
 */
class BaseStatsWrapper implements Serializable{
    public long mRunningSince = -1;
    public Map<String, BaseStats> mStats = null;
}
