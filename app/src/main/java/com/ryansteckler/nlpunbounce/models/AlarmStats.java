package com.ryansteckler.nlpunbounce.models;

import java.io.Serializable;

/**
 * Created by rsteckler on 9/13/14.
 */
public class AlarmStats extends BaseStats implements Serializable {

    public AlarmStats() {
        setType("alarm");
    }

}
