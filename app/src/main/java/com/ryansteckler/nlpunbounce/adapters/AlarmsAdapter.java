package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.WakelockStats;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rsteckler on 9/7/14.
 */
public class AlarmsAdapter extends BaseAdapter {

    private boolean mTruncateEnd = false;

    public AlarmsAdapter(Context context, ArrayList<BaseStats> alarmStatList) {
        super(context, R.layout.fragment_alarms_listitem, alarmStatList, "alarm");
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        mTruncateEnd = !prefs.getBoolean("scroll_item_names", true);
    }


    private static class AlarmViewHolder {
        TextView name;
        TextView alarmCount;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = this.getItemViewType(position);
        switch (itemType) {
            case ITEM_TYPE:
                // Get the data item for this position
                AlarmStats alarm = (AlarmStats)getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                AlarmViewHolder alarmViewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    alarmViewHolder = new AlarmViewHolder();

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_alarms_listitem, parent, false);
                    alarmViewHolder.name = (TextView) convertView.findViewById(R.id.textviewAlarmName);
                    if (mTruncateEnd) {
                        alarmViewHolder.name.setEllipsize(TextUtils.TruncateAt.END);
                    }
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

                float[] hsv = getBackColorFromSpectrum(alarm);
                alarmViewHolder.alarmCount.setBackgroundColor(Color.HSVToColor(hsv));

                hsv = getForeColorFromBack(hsv);
                alarmViewHolder.alarmCount.setTextColor(Color.HSVToColor(hsv));

                break;

            case CATEGORY_TYPE:
                convertView = getCategoryView(position, convertView, parent);
                break;

        }


        return convertView;
    }



    public void sort(int sortBy, boolean categorize) {
        mSortBy = sortBy;
        Collections.sort(mBackingList, SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));
        sort(SortWakeLocks.getBaseListComparator(mSortBy, categorize, this.getContext()));
        addCategories(mBackingList);
    }

    public void sort(int sortBy) {
        sort(sortBy, true);
    }
}
