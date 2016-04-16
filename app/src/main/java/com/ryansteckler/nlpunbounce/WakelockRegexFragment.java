package com.ryansteckler.nlpunbounce;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.DialogFragment;
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
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {OnFragmentInteractionListener}
 * interface.
 */
public class WakelockRegexFragment extends BaseRegexFragment {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelockRegexFragment() {
        super();
        this.mType = "wakelock";
    }

    public static WakelockRegexFragment newInstance() {
        WakelockRegexFragment fragment = new WakelockRegexFragment();
        return fragment;
    }
}
