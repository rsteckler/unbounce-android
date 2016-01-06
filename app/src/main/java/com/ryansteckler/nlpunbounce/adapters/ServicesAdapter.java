package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.ServiceStats;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by rsteckler on 10/21/14.
 */
public class ServicesAdapter extends BaseAdapter {

    private boolean mTruncateEnd = false;

    public ServicesAdapter(Context context, ArrayList<BaseStats> serviceStatList) {
        super(context, R.layout.fragment_service_listitem, serviceStatList, "service");
        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        mTruncateEnd = !prefs.getBoolean("scroll_item_names", true);
    }


    private static class ServiceViewHolder {
        TextView name;
        TextView serviceCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemType = this.getItemViewType(position);
        switch (itemType) {
            case ITEM_TYPE:
                // Get the data item for this position
                ServiceStats service = (ServiceStats)getItem(position);

                // Check if an existing view is being reused, otherwise inflate the view
                ServiceViewHolder serviceViewHolder; // view lookup cache stored in tag
                if (convertView == null) {
                    serviceViewHolder = new ServiceViewHolder();

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    convertView = inflater.inflate(R.layout.fragment_service_listitem, parent, false);
                    serviceViewHolder.name = (TextView) convertView.findViewById(R.id.textviewServiceName);
                    if (mTruncateEnd) {
                        serviceViewHolder.name.setEllipsize(TextUtils.TruncateAt.END);
                    }
                    serviceViewHolder.serviceCount = (TextView) convertView.findViewById(R.id.textViewServiceCount);

                    convertView.setTag(serviceViewHolder);
                }
                else {
                    serviceViewHolder = (ServiceViewHolder) convertView.getTag();
                }

                // Populate the data into the template view using the data object
                serviceViewHolder.name.setText(service.getName());
                serviceViewHolder.serviceCount.setText(String.valueOf(service.getAllowedCount()));

                serviceViewHolder.name.setSelected(true);

                //Size the count box width to at least the height.
                serviceViewHolder.serviceCount.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                int height = serviceViewHolder.serviceCount.getMeasuredHeight();
                int width = serviceViewHolder.serviceCount.getMeasuredWidth();
                if (height > width) {
                    serviceViewHolder.serviceCount.setLayoutParams(new LinearLayout.LayoutParams(height, height));
                }
                else {
                    serviceViewHolder.serviceCount.setLayoutParams(new LinearLayout.LayoutParams(width, width));
                }

                //Set the background color along the reg-green spectrum based on the severity of the count.
                float correctedStat = service.getAllowedCount() - mLowCount;
                float point = 120 - ((correctedStat / mScale) * 120); //this gives us a 1-120 hue number.

                float[] hsv = getBackColorFromSpectrum(service);
                serviceViewHolder.serviceCount.setBackgroundColor(Color.HSVToColor(hsv));

                hsv = getForeColorFromBack(hsv);
                serviceViewHolder.serviceCount.setTextColor(Color.HSVToColor(hsv));

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
        sort(SortWakeLocks.getBaseListComparator(mSortBy, categorize,this.getContext()));
        addCategories(mBackingList);
    }

    public void sort(int sortBy) {
        sort(sortBy, true);
    }


}
