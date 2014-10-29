package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsteckler on 10/19/14.
 */
public class ServiceStats extends BaseStats implements Serializable{


    public ServiceStats(String serviceName, int uId)
    {

        setType("service");
        setName(serviceName);
        setUid(uId);
        setType("service");

    }
    private ServiceStats() {
        setType("service");
    }

}
