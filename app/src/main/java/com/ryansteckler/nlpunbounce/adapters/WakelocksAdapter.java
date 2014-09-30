package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by rsteckler on 9/7/14.
 */
public class WakelocksAdapter extends ArrayAdapter {

    //Track whether we're sorting by count or duration.
    private boolean mByCount = false;
    private long mLowCount = 0;
    private long mHighCount = 0;
    private long mScale = 0;

    private long mCategoryBlockedIndex = 0;
    private long mCategorySafeIndex = 0;
    private long mCategoryUnknownIndex = 0;
    private long mCategoryUnsafeIndex = 0;

    private final static int WAKELOCK_TYPE = 0;
    private final static int CATEGORY_TYPE = 1;

    private ArrayList<WakelockStats> mBackingList = null;

    public WakelocksAdapter(Context context, ArrayList<WakelockStats> wakelockStatList) {
        super(context, R.layout.fragment_wakelocks_listitem, wakelockStatList);
        mBackingList = wakelockStatList;
        calculateScale(context, mBackingList);
        addCategories(mBackingList);
    }

    private void addCategories(ArrayList<WakelockStats> wakelockStatList) {

        mCategoryBlockedIndex = 0;
        mCategorySafeIndex = 1;
        mCategoryUnknownIndex = 2;
        mCategoryUnsafeIndex = 3;

        boolean foundSafe = false;
        boolean foundUnknown = false;

        Iterator<WakelockStats> iter = wakelockStatList.iterator();
        while (iter.hasNext()) {

            WakelockStats curStat = iter.next();

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

    private void calculateScale(Context context, ArrayList<WakelockStats> wakelockStatList) {

        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);

        //Get the max and min values for the red-green spectrum of counts
        Iterator<WakelockStats> iter = wakelockStatList.iterator();
        while (iter.hasNext())
        {
            WakelockStats curStat = iter.next();
            if (curStat.getAllowedCount() > mHighCount)
                mHighCount = curStat.getAllowedCount();
            if (curStat.getAllowedCount() < mLowCount || mLowCount == 0)
                mLowCount = curStat.getAllowedCount();

            //Set the blocking flag
            String blockName = "wakelock_" + curStat.getName() + "_enabled";
            boolean blockingEnabled = prefs.getBoolean(blockName, false);
            curStat.setBlockingEnabled(blockingEnabled);

        }
        mScale = mHighCount - mLowCount;

    }

    private static class WakelockViewHolder {
        TextView name;
        TextView wakeTime;
        TextView wakeCount;
    }

    private static class CategoryViewHolder {
        TextView name;
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
            return WAKELOCK_TYPE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = this.getItemViewType(position);
        switch (itemType) {
            case WAKELOCK_TYPE:
                // Get the data item for this position
                WakelockStats wakelock = (WakelockStats)getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                WakelockViewHolder viewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    viewHolder = new WakelockViewHolder();

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_wakelocks_listitem, parent, false);
                    viewHolder.name = (TextView) convertView.findViewById(R.id.textviewWakelockName);
                    viewHolder.wakeTime = (TextView) convertView.findViewById(R.id.textviewWakelockTime);
                    viewHolder.wakeCount = (TextView) convertView.findViewById(R.id.textViewWakelockCount);

                    convertView.setTag(viewHolder);
                }
                else {
                    viewHolder = (WakelockViewHolder) convertView.getTag();
                }

                // Populate the data into the template view using the data object
                viewHolder.name.setText(wakelock.getName());
                viewHolder.name.setSelected(true);

                viewHolder.wakeTime.setText(String.valueOf(wakelock.getDurationAllowedFormatted()));
                viewHolder.wakeCount.setText(String.valueOf(wakelock.getAllowedCount()));

                //Size the count box width to at least the height.
                viewHolder.wakeCount.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int height = viewHolder.wakeCount.getMeasuredHeight();
                int width = viewHolder.wakeCount.getMeasuredWidth();
                if (height > width) {
                    viewHolder.wakeCount.setLayoutParams(new LinearLayout.LayoutParams(height, height));
                }
                else {
                    viewHolder.wakeCount.setLayoutParams(new LinearLayout.LayoutParams(width, width));
                }

                //Set the background color along the reg-green spectrum based on the severity of the count.
                float correctedStat = wakelock.getAllowedCount() - mLowCount;
                float point = 120 - ((correctedStat / mScale) * 120); //this gives us a 1-120 hue number.

                float[] hsv = {point, 1, 1};
                viewHolder.wakeCount.setBackgroundColor(Color.HSVToColor(hsv));

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
                    categoryViewHolder.name.setText("Unbounced");
                } else if (position == mCategorySafeIndex) {
                    categoryViewHolder.name.setText("Safe to Unbounce");
                } else if (position == mCategoryUnknownIndex) {
                    categoryViewHolder.name.setText("Unknown");
                } else if (position == mCategoryUnsafeIndex) {
                    categoryViewHolder.name.setText("Not safe to Unbounce");
                } else {
                    categoryViewHolder.name.setText("Error");
                }

                break;
        }


        return convertView;
    }

    @Override
    public int getCount() {
        int adjustedCount = super.getCount() + 4; //4 categories
        return adjustedCount;
    }

    public void sort(boolean byCount, boolean categorize) {
        mByCount = byCount;
        sort(SortWakeLocks.getWakelockListComparator(byCount, categorize));
        addCategories(mBackingList);
    }

    public void sort(boolean byCount) {
        sort(byCount, true);
    }

}
