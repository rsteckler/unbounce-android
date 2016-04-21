package com.ryansteckler.nlpunbounce;

import android.os.Bundle;

import com.ryansteckler.nlpunbounce.adapters.AlarmsAdapter;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

/**
 * Created by rsteckler on 11/21/15.
 */
public class AlarmRegexFragment extends RegexFragment {

    private final static String ARG_TASKER_MODE = "taskerMode";
    private boolean mTaskerMode = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmRegexFragment() {
        super();
    }

    public static AlarmRegexFragment newInstance() { return newInstance(false); }

    public static AlarmRegexFragment newInstance(boolean taskerMode) {
        AlarmRegexFragment fragment = new AlarmRegexFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getType() {
        return "alarm";
    }

    @Override
    protected boolean getTaskerMode() { return mTaskerMode; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());
        if (getArguments() != null) {
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }
    }
}
