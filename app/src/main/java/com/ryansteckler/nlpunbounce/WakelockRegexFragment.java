package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.DialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.ryansteckler.nlpunbounce.adapters.RegexAdapter;
import com.ryansteckler.nlpunbounce.adapters.WakelocksAdapter;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {OnFragmentInteractionListener}
 * interface.
 */
public class WakelockRegexFragment extends android.app.ListFragment /* implements BaseDetailFragment.FragmentClearListener */ {

//    private final static String ARG_TASKER_MODE = "taskerMode";
//    private OnFragmentInteractionListener mListener;
    private RegexAdapter mAdapter;
//    //Whether we're sorting the wakelocks list by duration or count
//    private int mSortBy = SortWakeLocks.SORT_COUNT;
//    private boolean mReloadOnShow = false;
//    private boolean mTaskerMode = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelockRegexFragment() {
    }

    public static WakelockRegexFragment newInstance() {
        WakelockRegexFragment fragment = new WakelockRegexFragment();
//        Bundle args = new Bundle();
//        args.putBoolean(ARG_TASKER_MODE, taskerMode);
//        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        if (mListener != null)
//            mListener.onWakelocksSetTitle(getResources().getString(R.string.title_wakelocks));

//        mAdapter.sort(mSortBy);
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
        Set<String> set = prefs.getStringSet("wakelock_regex_set", sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list);

        // Sets The Adapter
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_new_custom) {
            //Create a new custom wakelock regex
            DialogFragment dialog = RegexDialogFragment.newInstance("");
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
        Set<String> set = prefs.getStringSet("wakelock_regex_set", sampleSet);
        ArrayList<String> list = new ArrayList<String>(set);

        // Create The Adapter with passing ArrayList as 3rd parameter
        mAdapter = new RegexAdapter(getActivity(), list);

        // Sets The Adapter
        setListAdapter(mAdapter);

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //Switch to detail view.
        //TODO Open up the regex editor and prepop with this regex content.
        DialogFragment dialog = RegexDialogFragment.newInstance("startingText");
        dialog.setTargetFragment(this, 0);
        dialog.show(getActivity().getFragmentManager(), "RegexDialog");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Remember the scroll pos so we can reinstate it
//        if (!hidden) {
//            if (mListener != null) {
//                mListener.onWakelocksSetTitle(getResources().getString(R.string.title_wakelocks));
//            }
//        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        public void onWakelocksSetTitle(String id);
//    }

}
