package com.ryansteckler.nlpunbounce;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ryansteckler.nlpunbounce.adapters.RegexAdapter;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsteckler on 11/21/15.
 */
public class AlarmRegexFragment extends android.app.ListFragment /* implements BaseDetailFragment.FragmentClearListener */ {

    private RegexAdapter mAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmRegexFragment() {
    }

    public static AlarmRegexFragment newInstance() {
        AlarmRegexFragment fragment = new AlarmRegexFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.regex, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void reload() {
        //Setup the list adapter
        SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
        Set<String> sampleSet = new HashSet<String>();
        Set<String> set = prefs.getStringSet("alarm_regex_set", sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list, "alarm");

        // Sets The Adapter
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_custom) {
            //Create a new custom alarm regex
            DialogFragment dialog = RegexDialogFragment.newInstance("", "", "alarm");
            dialog.setTargetFragment(this, 0);
            dialog.show(getActivity().getFragmentManager(), "RegexDialog");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());

        //Setup the list adapter
        SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
        Set<String> sampleSet = new HashSet<String>();
        Set<String> set = prefs.getStringSet("alarm_regex_set", sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list, "alarm");

        // Sets The Adapter
        setListAdapter(mAdapter);

        setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Switch to detail view.
        //Open up the regex editor and prepop with this regex content.
        String value = mAdapter.getItem(position);
        String name = value.substring(0, value.indexOf("$$||$$"));
        String seconds = value.substring(value.indexOf("$$||$$") + 6);
        DialogFragment dialog = RegexDialogFragment.newInstance(name, seconds, "alarm");
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getFragmentManager(), "RegexDialog");

    }
}
