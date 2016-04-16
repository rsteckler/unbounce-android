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
public class AlarmRegexFragment extends BaseRegexFragment /* implements BaseDetailFragment.FragmentClearListener */ {

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmRegexFragment() {
        super();
        this.mType = "alarm";
    }

    public static AlarmRegexFragment newInstance() {
        AlarmRegexFragment fragment = new AlarmRegexFragment();
        return fragment;
    }
}
