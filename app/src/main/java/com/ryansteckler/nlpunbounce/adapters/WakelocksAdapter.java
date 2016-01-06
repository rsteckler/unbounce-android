package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by rsteckler on 9/7/14.
 */
public class WakelocksAdapter extends BaseAdapter {

    private boolean mTruncateEnd = false;

    public WakelocksAdapter(Context context, ArrayList<BaseStats> wakelockStatList) {
        super(context, R.layout.fragment_wakelocks_listitem, wakelockStatList, "wakelock");
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        mTruncateEnd = !prefs.getBoolean("scroll_item_names", true);
    }


    private static class WakelockViewHolder {
        TextView name;
        TextView wakeTime;
        TextView wakeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = this.getItemViewType(position);
        switch (itemType) {
            case ITEM_TYPE:
                // Get the data item for this position
                WakelockStats wakelock = (WakelockStats)getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                WakelockViewHolder viewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    viewHolder = new WakelockViewHolder();

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_wakelocks_listitem, parent, false);
                    viewHolder.name = (TextView) convertView.findViewById(R.id.textviewWakelockName);
                    if (mTruncateEnd) {
                        viewHolder.name.setEllipsize(TextUtils.TruncateAt.END);
                    }
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
                float[] hsv = getBackColorFromSpectrum(wakelock);
                viewHolder.wakeCount.setBackgroundColor(Color.HSVToColor(hsv));

                hsv = getForeColorFromBack(hsv);
                viewHolder.wakeCount.setTextColor(Color.HSVToColor(hsv));
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
        sort(SortWakeLocks.getWakelockListComparator(mSortBy, categorize, this.getContext()));
        addCategories(mBackingList);
    }

    public void sort(int sortBy) {
        sort(sortBy, true);
    }

}
