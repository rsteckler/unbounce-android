package com.ryansteckler.nlpunbounce;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {OnFragmentInteractionListener}
 * interface.
 */
public class WakelockRegexFragment extends RegexFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelockRegexFragment() {
        super();
    }

    public static WakelockRegexFragment newInstance() {
        WakelockRegexFragment fragment = new WakelockRegexFragment();
        return fragment;
    }

    @Override
    protected String getType() {
        return "wakelock";
    }


}
