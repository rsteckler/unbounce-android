package com.ryansteckler.nlpunbounce.helpers;

/**
 * Created by rsteckler on 9/5/14.
 */

import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.Comparator;


public class SortWakeLocks {

//    public static Comparator<Entry<String, WakelockStats>> getMapComparator(final boolean byCount)
//    {
//        return new Comparator<Entry<String, WakelockStats>>()
//        {
//            public int compare(Entry<String, WakelockStats> o1,
//                               Entry<String, WakelockStats> o2)
//            {
//                if (byCount)
//                {
//                    return ((Long)o2.getValue().getAllowedCount()).compareTo(o1.getValue().getAllowedCount());
//                }
//                else
//                {
//                    return ((Long)o2.getValue().getAllowedDuration()).compareTo(o1.getValue().getAllowedDuration());
//                }
//            }
//        };
//    }

    public static Comparator<WakelockStats> getWakelockListComparator(final boolean byCount)
    {
        return new Comparator<WakelockStats>()
        {
            public int compare(WakelockStats o1,
                               WakelockStats o2)
            {
                if (byCount)
                {
                    return ((Long)o2.getAllowedCount()).compareTo(o1.getAllowedCount());
                }
                else
                {
                    return ((Long)o2.getAllowedDuration()).compareTo(o1.getAllowedDuration());
                }
            }
        };
    }

    public static Comparator<AlarmStats> getAlarmListComparator()
    {
        return new Comparator<AlarmStats>()
        {
            public int compare(AlarmStats o1,
                               AlarmStats o2)
            {
                return ((Long)o2.getAllowedCount()).compareTo(o1.getAllowedCount());
            }
        };
    }

//    public static HashMap<String, WakelockStats> sortByComparator(HashMap<String, WakelockStats> unsortMap, final boolean byCount)
//    {
//
//        List<Entry<String, WakelockStats>> list = new LinkedList<Entry<String, WakelockStats>>(unsortMap.entrySet());
//
//        // Sorting the list based on values
//        Collections.sort(list, getMapComparator(byCount));
//
//        // Maintaining insertion order with the help of LinkedList
//        HashMap<String, WakelockStats> sortedMap = new LinkedHashMap<String, WakelockStats>();
//        for (Entry<String, WakelockStats> entry : list)
//        {
//            sortedMap.put(entry.getKey(), entry.getValue());
//        }
//
//        return sortedMap;
//    }
}