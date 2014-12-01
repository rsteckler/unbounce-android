package com.ryansteckler.nlpunbounce.models;

import android.content.Context;

import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;

import java.io.Serializable;

/**
 * Created by rsteckler on 10/19/14.
 */
public class ServiceStats extends BaseStats implements Serializable {


    public ServiceStats(String serviceName, int uId) {

        setType("service");
        setName(serviceName);
        setUid(uId);
        setType("service");

    }

    private ServiceStats() {
        setType("service");
    }


    @Override
    public String getDerivedPackageName(Context ctx) {
        if (null == getDerivedPackageName()) {
            UidNameResolver resolver = UidNameResolver.getInstance(ctx);
            String packName = resolver.getLabelForServices(this.getUid(), this.getName());
            if (null != packName) {
                setDerivedPackageName(packName);
            } else {
                setDerivedPackageName("Unknown");
            }
        }
        return getDerivedPackageName();
    }

}
