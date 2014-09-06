package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/5/14.
 */

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class SortWakeLockMap {
    public static boolean COUNT = true;
    public static boolean DURATION = false;

//    public static void main(String[] args)
//    {
//
//        // Creating dummy unsorted map
//        Map<String, Integer> unsortMap = new HashMap<String, Integer>();
//        unsortMap.put("B", 55);
//        unsortMap.put("A", 80);
//        unsortMap.put("D", 20);
//        unsortMap.put("C", 70);
//
//        System.out.println("Before sorting......");
//        printMap(unsortMap);
//
//        System.out.println("After sorting ascending order......");
//        Map<String, Integer> sortedMapAsc = sortByComparator(unsortMap, ASC);
//        printMap(sortedMapAsc);
//
//
//        System.out.println("After sorting descindeng order......");
//        Map<String, Integer> sortedMapDesc = sortByComparator(unsortMap, DESC);
//        printMap(sortedMapDesc);
//
//    }

    public static HashMap<String, WakeLockStatsCombined> sortByComparator(HashMap<String, WakeLockStatsCombined> unsortMap, final boolean order)
    {

        List<Entry<String, WakeLockStatsCombined>> list = new LinkedList<Entry<String, WakeLockStatsCombined>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, WakeLockStatsCombined>>()
        {
            public int compare(Entry<String, WakeLockStatsCombined> o1,
                               Entry<String, WakeLockStatsCombined> o2)
            {
                if (order)
                {
                    return ((Long)o2.getValue().getCount()).compareTo(o1.getValue().getCount());
                }
                else
                {
                    return ((Long)o2.getValue().getDuration()).compareTo(o1.getValue().getDuration());
                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        HashMap<String, WakeLockStatsCombined> sortedMap = new LinkedHashMap<String, WakeLockStatsCombined>();
        for (Entry<String, WakeLockStatsCombined> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}