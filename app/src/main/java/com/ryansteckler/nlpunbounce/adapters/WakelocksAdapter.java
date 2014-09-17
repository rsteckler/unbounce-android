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

    public WakelocksAdapter(Context context, ArrayList<WakelockStats> wakelockStatList) {
        super(context, R.layout.fragment_wakelocks_listitem, wakelockStatList);
        calculateScale(context, wakelockStatList);
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
            curStat.setBlockingEnabled(prefs.getBoolean(blockName, false));

        }
        mScale = mHighCount - mLowCount;

    }

    private static class ViewHolder {
        TextView name;
        TextView wakeTime;
        TextView wakeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WakelockStats wakelock = (WakelockStats)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view

        //TODO:  The ViewHolder pattern is messing up the dynamic sizing of the count tag.  Removed
        //ViewHolder for now  :(
        ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_wakelocks_listitem, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.textviewWakelockName);
            viewHolder.wakeTime = (TextView) convertView.findViewById(R.id.textviewWakelockTime);
            viewHolder.wakeCount = (TextView) convertView.findViewById(R.id.textViewWakelockCount);
            convertView.setTag(viewHolder);
       }
       else {
           viewHolder = (ViewHolder) convertView.getTag();
       }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(wakelock.getName());
        viewHolder.wakeTime.setText(String.valueOf(wakelock.getDurationAllowedFormatted()));
        viewHolder.wakeCount.setText(String.valueOf(wakelock.getAllowedCount()));
        viewHolder.name.setSelected(true);

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

        if (wakelock.getBlockingEnabled()) {
            viewHolder.name.setTypeface(null, Typeface.BOLD);
        } else {
            viewHolder.name.setTypeface(null, Typeface.NORMAL);
        }


        return convertView;
    }

    public void sort(boolean byCount)
    {
        mByCount = byCount;
        sort(SortWakeLocks.getWakelockListComparator(byCount));
    }

}
