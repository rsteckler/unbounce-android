package com.ryansteckler.nlpunbounce.com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.WakeLockStatsCombined;

import java.util.ArrayList;

/**
 * Created by rsteckler on 9/7/14.
 */
public class WakelocksAdapter extends ArrayAdapter {
    public WakelocksAdapter(Context context, ArrayList<WakeLockStatsCombined> wakelockStatList) {
        super(context, R.layout.fragment_wakelock_listitem, wakelockStatList);
    }

    private static class ViewHolder {
        TextView name;
        TextView wakeTime;
        TextView wakeCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WakeLockStatsCombined wakelock = (WakeLockStatsCombined)getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_wakelock_listitem, parent, false);
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
        viewHolder.wakeTime.setText(String.valueOf(wakelock.getDurationFormatted()));
        // Return the completed view to render on screen
        return convertView;
    }
}
