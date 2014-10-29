package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;

/**
 * Created by rsteckler on 9/13/14.
 */
public class AlarmStats extends BaseStats implements Serializable {

    public AlarmStats(String alarmName, String packageName)
    {
        setType("alarm");
        setName(alarmName);
        if(null!=packageName && !packageName.trim().equals("")){
            setPackage(packageName);
        }else{
            setPackage("No Package");
        }

    }

    private AlarmStats(){};
}
