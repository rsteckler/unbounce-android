package com.ryansteckler.nlpunbounce.helpers;

/**
 * Created by rsteckler on 9/5/14.
 */

import android.content.Context;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.Comparator;


public class SortWakeLocks {

    public final static int SORT_COUNT = 0;
    public final static int SORT_ALPHA = 1;
    public final static int SORT_TIME = 2; //Only makes sense for Wakelocks

    public final static int SORT_PACKAGE = 3;

    //public final static int CAT_BLOCKING_MODE=0;
    //public final static int CAT_PACKAGE_MODE =1;

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

    public static Comparator<WakelockStats> getWakelockListComparator(final int sortType, Context ctx) {
        return getWakelockListComparator(sortType, true, ctx);
    }

    public static Comparator<WakelockStats> getWakelockListComparator(final int sortType, final boolean categorize, final Context ctx) {
        return new Comparator<WakelockStats>() {
            public int compare(WakelockStats o1,
                               WakelockStats o2) {
                if (categorize) {
                    int blockingCompare = ((Boolean) o2.getBlockingEnabled()).compareTo(o1.getBlockingEnabled());
                    if (blockingCompare != 0)
                        return blockingCompare;

                    int safetyCompare = ((Integer) EventLookup.isSafe(o2.getName())).compareTo(EventLookup.isSafe(o1.getName()));
                    if (safetyCompare != 0)
                        return safetyCompare;

                    //The category state is the same.  Sub-compare
                    if (sortType == SORT_COUNT) {
                        return ((Long) o2.getAllowedCount()).compareTo(o1.getAllowedCount());
                    } else if (sortType == SORT_TIME) {
                        return ((Long) o2.getAllowedDuration()).compareTo(o1.getAllowedDuration());
                    } else if (sortType == SORT_PACKAGE) {
                        return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));
                    } else {
                        return ((String) o1.getName()).compareTo(o2.getName());
                    }
                } else {
                    if (sortType == SORT_COUNT) {
                        return ((Long) o2.getAllowedCount()).compareTo(o1.getAllowedCount());
                    } else if (sortType == SORT_TIME) {
                        return ((Long) o2.getAllowedDuration()).compareTo(o1.getAllowedDuration());
                    } else if (sortType == SORT_PACKAGE) {
                        return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));
                    } else {
                        return ((String) o1.getName()).compareTo(o2.getName());
                    }
                }

            }
        };
    }

    public static Comparator<BaseStats> getBaseListComparator(final int sortType, Context ctx) {
        return getBaseListComparator(sortType, true, ctx);
    }

    public static Comparator<BaseStats> getBaseListComparator(final int sortType, final boolean categorize, final Context ctx) {
        return new Comparator<BaseStats>() {
            public int compare(BaseStats o1,
                               BaseStats o2) {
                if (categorize) {

                    int blockingCompare = ((Boolean) o2.getBlockingEnabled()).compareTo(o1.getBlockingEnabled());
                    if (blockingCompare != 0)
                        return blockingCompare;

                    int safetyCompare = ((Integer) EventLookup.isSafe(o2.getName())).compareTo(EventLookup.isSafe(o1.getName()));
                    if (safetyCompare != 0)
                        return safetyCompare;

                    //The category state is the same.  Sub-compare
                    if (sortType == SORT_COUNT) {
                        return ((Long) o2.getAllowedCount()).compareTo(o1.getAllowedCount());
                    } else if (sortType == SORT_TIME) {
                        if (o2 instanceof WakelockStats && o1 instanceof WakelockStats) {
                            WakelockStats o2prime = (WakelockStats)o2;
                            WakelockStats o1prime = (WakelockStats)o1;
                            return ((Long) o2prime.getAllowedDuration()).compareTo(o1prime.getAllowedDuration());
                        } else {
                            return ((String) o1.getName()).compareTo(o2.getName());
                        }
                    } else if (sortType == SORT_PACKAGE) {
                        return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));
                    } else {
                        return ((String) o1.getName()).compareTo(o2.getName());
                    }
                } else {
                    if (sortType == SORT_COUNT) {
                        return ((Long) o2.getAllowedCount()).compareTo(o1.getAllowedCount());
                    } else if (sortType == SORT_TIME) {
                        if (o2 instanceof WakelockStats && o1 instanceof WakelockStats) {
                            WakelockStats o2prime = (WakelockStats)o2;
                            WakelockStats o1prime = (WakelockStats)o1;
                            return ((Long) o2prime.getAllowedDuration()).compareTo(o1prime.getAllowedDuration());
                        } else {
                            return ((String) o1.getName()).compareTo(o2.getName());
                        }
                    } else if (sortType == SORT_PACKAGE) {
                        return (o2.getDerivedPackageName(ctx)).compareTo(o1.getDerivedPackageName(ctx));
                    } else {
                        return ((String) o1.getName()).compareTo(o2.getName());
                    }
                }
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