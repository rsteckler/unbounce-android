package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by rsteckler on 9/7/14.
 */
public class AlarmsAdapter extends ArrayAdapter {

    private long mLowCount = 0;
    private long mHighCount = 0;
    private long mScale = 0;

    private long mCategoryBlockedIndex = 0;
    private long mCategorySafeIndex = 0;
    private long mCategoryUnknownIndex = 0;
    private long mCategoryUnsafeIndex = 0;

    private final static int ALARM_TYPE = 0;
    private final static int CATEGORY_TYPE = 1;

    private ArrayList<AlarmStats> mBackingList = null;

    public AlarmsAdapter(Context context, ArrayList<AlarmStats> alarmStatList) {
        super(context, R.layout.fragment_wakelocks_listitem, alarmStatList);
        mBackingList = alarmStatList;
        calculateScale(context, alarmStatList);
        addCategories(mBackingList);
    }

    private void addCategories(ArrayList<AlarmStats> alarmStatList) {
        mCategoryBlockedIndex = 0;
        mCategorySafeIndex = 1;
        mCategoryUnknownIndex = 2;
        mCategoryUnsafeIndex = 3;

        boolean foundSafe = false;
        boolean foundUnknown = false;

        Iterator<AlarmStats> iter = alarmStatList.iterator();
        while (iter.hasNext()) {

            AlarmStats curStat = iter.next();

            if (!curStat.getBlockingEnabled()) {
                foundSafe = true;
            }

            if (!foundUnknown && foundSafe && EventLookup.isSafe(curStat.getName()) == EventLookup.UNKNOWN) {
                foundUnknown = true;
            }

            if (foundUnknown && EventLookup.isSafe(curStat.getName()) == EventLookup.UNSAFE) {
                break;
            }

            if (!foundSafe)
                mCategorySafeIndex++;

            if (!foundUnknown)
                mCategoryUnknownIndex++;

            mCategoryUnsafeIndex++;

        }
    }

    private void calculateScale(Context context, ArrayList<AlarmStats> alarmStatList) {

        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);

        //Get the max and min values for the red-green spectrum of counts
        Iterator<AlarmStats> iter = alarmStatList.iterator();
        while (iter.hasNext())
        {
            AlarmStats curStat = iter.next();
            if (curStat.getAllowedCount() > mHighCount)
                mHighCount = curStat.getAllowedCount();
            if (curStat.getAllowedCount() < mLowCount || mLowCount == 0)
                mLowCount = curStat.getAllowedCount();

            //Set the blocking flag
            String blockName = "alarm_" + curStat.getName() + "_enabled";
            curStat.setBlockingEnabled(prefs.getBoolean(blockName, false));

        }
        mScale = mHighCount - mLowCount;

    }

    private static class AlarmViewHolder {
        TextView name;
        TextView alarmCount;
    }

    private static class CategoryViewHolder {
        TextView name;
    }

    @Override
    public int getCount() {
        return super.getCount() + 4; //4 categories
    }

    @Override
    public Object getItem(int position) {
        int newPosition = position;
        if (position > mCategoryBlockedIndex)
            newPosition--;
        if (position > mCategorySafeIndex)
            newPosition--;
        if (position > mCategoryUnknownIndex)
            newPosition--;
        if (position > mCategoryUnsafeIndex)
            newPosition--;

        return super.getItem(newPosition);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mCategoryBlockedIndex ||
                position == mCategorySafeIndex ||
                position == mCategoryUnknownIndex ||
                position == mCategoryUnsafeIndex)
            return CATEGORY_TYPE;
        else
            return ALARM_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = this.getItemViewType(position);
        switch (itemType) {
            case ALARM_TYPE:
                // Get the data item for this position
                AlarmStats alarm = (AlarmStats)getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                AlarmViewHolder alarmViewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    alarmViewHolder = new AlarmViewHolder();

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_alarms_listitem, parent, false);
                    alarmViewHolder.name = (TextView) convertView.findViewById(R.id.textviewAlarmName);
                    alarmViewHolder.alarmCount = (TextView) convertView.findViewById(R.id.textViewAlarmCount);

                    convertView.setTag(alarmViewHolder);
                }
                else {
                   alarmViewHolder = (AlarmViewHolder) convertView.getTag();
                }

                // Populate the data into the template view using the data object
                alarmViewHolder.name.setText(alarm.getName());
                alarmViewHolder.alarmCount.setText(String.valueOf(alarm.getAllowedCount()));

                alarmViewHolder.name.setSelected(true);

                //Size the count box width to at least the height.
                alarmViewHolder.alarmCount.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int height = alarmViewHolder.alarmCount.getMeasuredHeight();
                int width = alarmViewHolder.alarmCount.getMeasuredWidth();
                if (height > width) {
                    alarmViewHolder.alarmCount.setLayoutParams(new LinearLayout.LayoutParams(height, height));
                }
                else {
                    alarmViewHolder.alarmCount.setLayoutParams(new LinearLayout.LayoutParams(width, width));
                }

                //Set the background color along the reg-green spectrum based on the severity of the count.
                float correctedStat = alarm.getAllowedCount() - mLowCount;
                float point = 120 - ((correctedStat / mScale) * 120); //this gives us a 1-120 hue number.

                float[] hsv = {point, 1, 1};
                alarmViewHolder.alarmCount.setBackgroundColor(Color.HSVToColor(hsv));

                break;

            case CATEGORY_TYPE:
                //Take care of the category special cases.
                CategoryViewHolder categoryViewHolder = null; // view lookup cache stored in tag

                // Check if an existing view is being reused, otherwise inflate the view
                if (convertView == null) {
                    categoryViewHolder = new CategoryViewHolder();
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_wakelocks_listgroup, parent, false);
                    categoryViewHolder.name = (TextView) convertView.findViewById(R.id.textviewCategoryName);
                    convertView.setTag(categoryViewHolder);
                } else {
                    categoryViewHolder = (CategoryViewHolder) convertView.getTag();

                }

                if (position == mCategoryBlockedIndex) {
                    categoryViewHolder.name.setText(R.string.category_unbounced);
                } else if (position == mCategorySafeIndex) {
                    categoryViewHolder.name.setText(R.string.category_safe);
                } else if (position == mCategoryUnknownIndex) {
                    categoryViewHolder.name.setText(R.string.category_unknown);
                } else if (position == mCategoryUnsafeIndex) {
                    categoryViewHolder.name.setText(R.string.category_not_safe);
                } else {
                    categoryViewHolder.name.setText(R.string.category_error);
                }

                break;
        }


        return convertView;
    }

    public void sort(boolean categorize) {
        sort(SortWakeLocks.getAlarmListComparator(categorize));
        addCategories(mBackingList);
    }

    public void sort() {
        sort(true);
    }


}
