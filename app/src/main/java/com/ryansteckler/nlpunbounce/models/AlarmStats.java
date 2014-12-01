package com.ryansteckler.nlpunbounce.models;

import android.content.Context;

import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;

import java.io.Serializable;

/**
 * Created by rsteckler on 9/13/14.
 */
public class AlarmStats extends BaseStats implements Serializable {

    public AlarmStats(String alarmName, String packageName) {
        setType("alarm");
        setName(alarmName);
        if (null != packageName && !packageName.trim().equals("")) setPackage(packageName);
        else {
            setPackage("No Package");
        }

    }

    private AlarmStats() {
    }

    @Override
    public String getDerivedPackageName(Context ctx) {
        if (null== getDerivedPackageName()){
            UidNameResolver resolver = UidNameResolver.getInstance(ctx);
            String packName = resolver.getLabel(this.getPackage());
            if (null != packName) {
                setDerivedPackageName(packName);
            } else {
                setDerivedPackageName("Unknown");
            }
        }
        return getDerivedPackageName();
    }
}
