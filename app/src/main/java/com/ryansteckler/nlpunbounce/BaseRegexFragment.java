package com.ryansteckler.nlpunbounce;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ryansteckler.nlpunbounce.adapters.RegexAdapter;
import com.ryansteckler.nlpunbounce.helpers.LogHelper;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.AlarmStats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseRegexFragment extends android.app.ListFragment implements AlarmDetailFragment.FragmentClearListener {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected final static String ARG_TYPE = "type";
    protected static final String PREF_SET_TEMPLATE = "%1$s_regex_set";

    private RegexAdapter mAdapter;
    private boolean mReloadOnShow = false;
    private boolean mTaskerMode = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BaseRegexFragment() {
    }

    protected abstract String getType();

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
        Set<String> set = prefs.getStringSet(String.format(PREF_SET_TEMPLATE, getType()), sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list, getType());

        // Sets The Adapter
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_custom) {
            //Create a new custom alarm regex
            //Fragment fragment = RegexDialogFragment.newInstance("", "", "", mType);
            //fragment.setTargetFragment(this, 0);
            switchToDetail(0, "", "", "");
            //fragment.show(getActivity().getFragmentManager(), "RegexDialog");
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
        Set<String> set = prefs.getStringSet(String.format(PREF_SET_TEMPLATE, getType()), sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list, getType());

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
        String[] values = value.split("\\$\\$\\|\\|\\$\\$");  // matches $$||$$
        String name, seconds, enabled;
        enabled = "enabled";

        if (values.length < 2)
            return;

        name = values[0];
        seconds = values[1];

        if (values.length > 2)
            enabled = values[2];

        //Fragment fragment = RegexDialogFragment.newInstance(name, seconds, enabled, mType);
        //fragment.setTargetFragment(this, 0);
        //fragment.show(getActivity().getFragmentManager(), "RegexDialog");

        //Switch to detail view.
        switchToDetail(position, name, seconds, enabled);
    }

    private void switchToDetail(int position, String name, String seconds, String enabled) {
        //We're going for an animation from the list item, expanding to take the entire screen.
        //Start by getting the bounds of the current list item, as a starting point.
        ListView list = (ListView) getActivity().findViewById(android.R.id.list);
        View listItem = list.getChildAt(position - list.getFirstVisiblePosition());
        if (listItem == null) {
            LogHelper.defaultLog(getActivity(), "About to crash.  null item at position: " + position);
        }
        final Rect startBounds = new Rect();
        listItem.getGlobalVisibleRect(startBounds);

        //Now get the final bounds for the animation:  the same bounds as the parent list.
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        list.getGlobalVisibleRect(finalBounds, globalOffset);

        //Offset both bounds because we aren't full-screen.
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        //Spin up the new Detail fragment.  Dig the custom animations.  Also put it on the back stack
        //so we can hit the back button and come back to the list.
        FragmentManager fragmentManager = getFragmentManager();
        RegexDialogFragment newFrag = (RegexDialogFragment) new RegexDialogFragment().newInstance(startBounds.top, finalBounds.top, startBounds.bottom, finalBounds.bottom, new AlarmStats("Regex", "Regex"), mTaskerMode,
                name, seconds, enabled, getType());
        //RegexDialogFragment newFrag = (RegexDialogFragment) new RegexDialogFragment().newInstance(name, seconds, enabled, mType);
        newFrag.attachClearListener(this);
        newFrag.setTargetFragment(this, 0);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.expand_in, R.animator.noop, R.animator.noop, R.animator.expand_out)
                .hide(this)
                .add(R.id.container, newFrag, "regex_detail")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onCleared() {
        mReloadOnShow = true;
    }
}
