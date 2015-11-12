package com.ryansteckler.nlpunbounce.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsteckler on 11/10/15.
 */
public class RegexAdapter extends ArrayAdapter<String> {


    private final ArrayList<String> list;
    private final Activity context;
//    private ViewHolder viewHolder;

    public RegexAdapter(Activity context, ArrayList<String> list) {
        super(context, R.layout.fragment_regex_listitem, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.fragment_regex_listitem, null);
//            viewHolder = new ViewHolder();
        } else {
            view = convertView;
        }

        TextView text = (TextView) view.findViewById(R.id.textviewRegexName);
        text.setText(this.list.get(position));
        Button button = (Button) view.findViewById(R.id.btn_delete_regex);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    ViewParent parentView = v.getParent();
//                    View parent = (View) parentView;

                Integer tag = (Integer)v.getTag();
                int pos = tag.intValue();

//                    TextView itemLabelTextView = (TextView) parent.findViewById(R.id.textviewRegexName);

                list.remove(pos);
//                    RegexAdapter.this.remove(itemLabelTextView.getText().toString());

                // saves the list
                SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
                Set<String> set = new HashSet<String>();
                set.addAll(list);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putStringSet("wakelock_regex_set", set);
                editor.commit();

                notifyDataSetChanged();

            }
        });

        return view;
    }

//    @Override
//    public int getCount() {
//        return list.size();
//    }
//
//    @Override
//    public String getItem(int pos) {
//        return list.get(pos);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return list.indexOf(getItem(position));
//    }

//    static class ViewHolder {
//        protected TextView text;
//        protected Button button;
//    }

}
