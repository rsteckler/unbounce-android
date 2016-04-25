package com.ryansteckler.nlpunbounce;

import android.os.Bundle;

import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {OnFragmentInteractionListener}
 * interface.
 */
public class WakelockRegexFragment extends RegexFragment {

    private final static String ARG_TASKER_MODE = "taskerMode";
    private boolean mTaskerMode = false;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelockRegexFragment() {
        super();
    }

    public static WakelockRegexFragment newInstance() { return newInstance(false); }

    public static WakelockRegexFragment newInstance(boolean taskerMode) {
        WakelockRegexFragment fragment = new WakelockRegexFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getType() {
        return "wakelock";
    }

    @Override
    protected boolean getTaskerMode() { return mTaskerMode; }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }

        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());
    }

}
