package com.ryansteckler.nlpunbounce.helpers;

/**
 * Created by rsteckler on 9/5/14.
 */

import com.ryansteckler.nlpunbounce.models.WakeLockStatsCombined;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;


public class SortWakeLocks {

    public static Comparator<Entry<String, WakeLockStatsCombined>> getMapComparator(final boolean byCount)
    {
        return new Comparator<Entry<String, WakeLockStatsCombined>>()
        {
            public int compare(Entry<String, WakeLockStatsCombined> o1,
                               Entry<String, WakeLockStatsCombined> o2)
            {
                if (byCount)
                {
                    return ((Long)o2.getValue().getCount()).compareTo(o1.getValue().getCount());
                }
                else
                {
                    return ((Long)o2.getValue().getDuration()).compareTo(o1.getValue().getDuration());
                }
            }
        };
    }

    public static Comparator<WakeLockStatsCombined> getListComparator(final boolean byCount)
    {
        return new Comparator<WakeLockStatsCombined>()
        {
            public int compare(WakeLockStatsCombined o1,
                               WakeLockStatsCombined o2)
            {
                if (byCount)
                {
                    return ((Long)o2.getCount()).compareTo(o1.getCount());
                }
                else
                {
                    return ((Long)o2.getDuration()).compareTo(o1.getDuration());
                }
            }
        };
    }

    public static HashMap<String, WakeLockStatsCombined> sortByComparator(HashMap<String, WakeLockStatsCombined> unsortMap, final boolean byCount)
    {

        List<Entry<String, WakeLockStatsCombined>> list = new LinkedList<Entry<String, WakeLockStatsCombined>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, getMapComparator(byCount));

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, WakeLockStatsCombined> sortedMap = new LinkedHashMap<String, WakeLockStatsCombined>();
        for (Entry<String, WakeLockStatsCombined> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}