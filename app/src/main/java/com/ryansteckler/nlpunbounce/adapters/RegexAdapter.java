package com.ryansteckler.nlpunbounce.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsteckler on 11/10/15.
 */
public class RegexAdapter extends ArrayAdapter<String> {


    private final ArrayList<String> list;
    private final Activity context;
    private final String mEntityName;
    private final boolean mTaskerMode;

    public RegexAdapter(Activity context, ArrayList<String> list, String entityName, boolean taskerMode) {
        super(context, R.layout.fragment_regex_listitem, list);
        this.context = context;
        this.list = list;
        this.mEntityName = entityName;
        this.mTaskerMode = taskerMode;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.fragment_regex_listitem, null);
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.textviewRegexName);
        text.setText(this.list.get(position).substring(0, this.list.get(position).indexOf("$$||$$")));
        Button button = (Button) view.findViewById(R.id.btn_delete_regex);
        if (button != null) {
            button.setTag(position);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Integer tag = (Integer) v.getTag();
                    int pos = tag.intValue();

                    list.remove(pos);

                    // saves the list
                    SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
                    Set<String> set = new HashSet<String>();
                    set.addAll(list);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putStringSet(mEntityName + "_regex_set", set);
                    editor.commit();

                    notifyDataSetChanged();

                }
            });

            button.setEnabled(!mTaskerMode);
        }

        TextView regexMatchText = (TextView) view.findViewById(R.id.regexMatchText);
        int matchingAlarmsCount = 0;
        ArrayList<BaseStats> alarms = UnbounceStatsCollection.getInstance().toAlarmArrayList(null);
        for (BaseStats alarm : alarms) {
            if (alarm.getName().matches(text.getText().toString()))
                matchingAlarmsCount++;
        }

        if (this.list.get(position).substring(this.list.get(position).lastIndexOf("$$||$$") + "$$||$$".length()).equals("disabled")) {
            regexMatchText.setText(context.getResources().getString(R.string.regex_list_disabled));
        } else {
            regexMatchText.setText(String.format(context.getResources().getString(R.string.regex_list_enabled),
                    matchingAlarmsCount, alarms.size()));
        }

        return view;
    }

}
