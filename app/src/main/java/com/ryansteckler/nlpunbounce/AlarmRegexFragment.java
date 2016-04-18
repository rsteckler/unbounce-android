package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 11/21/15.
 */
public class AlarmRegexFragment extends RegexFragment /* implements BaseDetailFragment.FragmentClearListener */ {
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmRegexFragment() {
        super();
    }

    public static AlarmRegexFragment newInstance() {
        AlarmRegexFragment fragment = new AlarmRegexFragment();
        return fragment;
    }

    @Override
    protected String getType() {
        return "alarm";
    }
}
