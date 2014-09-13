package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.models.WakelockStats;
import com.ryansteckler.nlpunbounce.models.WakelockStatsCollection;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.ryansteckler.nlpunbounce.WakelockDetailFragment.FragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WakelockDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WakelockDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_START_TOP = "startTop";
    private static final String ARG_FINAL_TOP = "finalTop";
    private static final String ARG_START_BOTTOM = "startBottom";
    private static final String ARG_FINAL_BOTTOM = "finalBottom";
    private static final String ARG_CUR_STAT = "curStat";

    private int mStartTop;
    private int mFinalTop;
    private int mStartBottom;
    private int mFinalBottom;
    private WakelockStats mStat;
    private FragmentClearListener mClearListener = null;

    private boolean mKnownSafeWakelock = false;

    private FragmentInteractionListener mListener;

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        ExpandingLayout anim = (ExpandingLayout)getActivity().findViewById(R.id.layoutDetails);
        anim.setAnimationBounds(mStartTop, mFinalTop, mStartBottom, mFinalBottom);
        super.onViewCreated(view, savedInstanceState);
        if (mListener != null)
            mListener.onWakelockDetailSetTitle(mStat.getName());

        loadStatsFromSource(view);

        final EditText edit = (EditText)view.findViewById(R.id.editWakelockSeconds);

        SharedPreferences prefs = getActivity().getSharedPreferences(WakelockDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);
        String blockSeconds = "wakelock_" + mStat.getName() + "_seconds";
        edit.setText(String.valueOf(prefs.getLong(blockSeconds, 240)));
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    try {
                        long seconds = Long.parseLong(textView.getText().toString());
                        SharedPreferences prefs = getActivity().getSharedPreferences(WakelockDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);
                        String blockName = "wakelock_" + mStat.getName() + "_seconds";
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putLong(blockName, seconds);
                        editor.commit();
                        textView.clearFocus();
                        // hide virtual keyboard
                        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                        return true;

                    } catch (NumberFormatException nfe)
                    {
                        //Not a number.  Android let us down.
                    }
                }
                return false;
            }
        });

        final Switch onOff = (Switch)view.findViewById(R.id.switchWakelock);
        String blockName = "wakelock_" + mStat.getName() + "_enabled";
        boolean enabled = prefs.getBoolean(blockName, false);
        onOff.setChecked(enabled);
        getView().findViewById(R.id.editWakelockSeconds).setEnabled(onOff.isChecked());

        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, final boolean b) {
                SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
                final SharedPreferences.Editor editor = prefs.edit();

                if (b) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Unbounce unknown wakelock?")
                            .setMessage("This wakelock hasn't been proven safe to Unbounce.  Would you like to Unbounce it anyway?")
                            .setPositiveButton("UNBOUNCE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    String blockName = "wakelock_" + mStat.getName() + "_enabled";
                                    editor.putBoolean(blockName, b);
                                    editor.commit();
                                    updateEnabledWakelock(b);
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    onOff.setChecked(false);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
                else {
                    updateEnabledWakelock(b);
                }
            }
        });

        View panel = (View)getView().findViewById(R.id.settingsPanel);
        panel.setBackgroundColor(enabled ?
                getResources().getColor(R.color.background_panel_enabled) :
                getResources().getColor(R.color.background_panel_disabled));
        panel.setAlpha(enabled ? 1 : (float) .4);

        TextView resetButton = (TextView)view.findViewById(R.id.buttonResetStats);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View resetView) {
                //Reset stats
                WakelockStatsCollection stats = WakelockStatsCollection.getInstance();
                stats.resetStats(getActivity(), mStat.getName());
                loadStatsFromSource(view);
                if (mClearListener != null)
                {
                    mClearListener.onWakelockCleared();
                }
            }
        });

        TextView description = (TextView)view.findViewById(R.id.textViewWakelockDescription);
        description.setText("We don't have any information about this wakelock, yet.  It may not be safe to unbounce this wakelock.  Only " +
            "do so if you know what you're doing, or you know how to disable Xposed at boot.  We're working hard to collect information about every" +
            " major wakelock and alarm.  Please be patient while we collect this information in the next few weeks.");

        if (mStat.getName().toLowerCase().equals("nlpwakelock")) {
            description.setText("NlpWakeLock is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                "combination of cell towers and WiFi.  Once it has your location, it stores it locally so other apps, like Google Now, can access your " +
                "location without using GPS or getting a new fix.  Recommended settings are between 180 and 600 seconds.");
            mKnownSafeWakelock = true;
        } else if (mStat.getName().toLowerCase().equals("nlpcollectorwakelock")) {
            description.setText("NlpWakeLock is safe to unbounce.  It's used by Google Play Services to determine your rough location using a " +
                "combination of cell towers and wifi.  Once it has your location, it sends it back to Google so they can expand their database " +
                "of WiFi locations.  Recommended settings are between 180 and 600 seconds.");
            mKnownSafeWakelock = true;
        }
    }

    private void updateEnabledWakelock(boolean b) {
        //Enable or disable the seconds setting.
        getView().findViewById(R.id.editWakelockSeconds).setEnabled(b);
        View panel = (View)getView().findViewById(R.id.settingsPanel);
        panel.setBackgroundColor(b ?
                getResources().getColor(R.color.background_panel_enabled) :
                getResources().getColor(R.color.background_panel_disabled));
        panel.setAlpha(b ? 1 : (float) .4);

        if (mClearListener != null)
        {
            mClearListener.onWakelockCleared();
        }
    }

    private void loadStatsFromSource(View view) {
        WakelockStatsCollection coll = WakelockStatsCollection.getInstance();
        mStat = coll.getStat(getActivity(), mStat.getName());

        TextView textView = (TextView)view.findViewById(R.id.textLocalWakeTimeAllowed);
        textView.setText(mStat.getDurationAllowedFormatted());
        textView = (TextView)view.findViewById(R.id.textLocalWakeTimeBlocked);
        textView.setText(mStat.getDurationBlockedFormatted());
        textView = (TextView)view.findViewById(R.id.textLocalWakeBlocked);
        textView.setText(String.valueOf(mStat.getBlockCount()));
        textView = (TextView)view.findViewById(R.id.textLocalWakeAcquired);
        textView.setText(String.valueOf(mStat.getAllowedCount()));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WakelockDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WakelockDetailFragment newInstance(int startTop, int finalTop, int startBottom, int finalBottom, WakelockStats stat) {
        WakelockDetailFragment fragment = new WakelockDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_START_TOP, startTop);
        args.putInt(ARG_FINAL_TOP, finalTop);
        args.putInt(ARG_START_BOTTOM, startBottom);
        args.putInt(ARG_FINAL_BOTTOM, finalBottom);
        args.putSerializable(ARG_CUR_STAT, stat);
        fragment.setArguments(args);
        return fragment;
    }
    public WakelockDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStartTop = getArguments().getInt(ARG_START_TOP);
            mFinalTop = getArguments().getInt(ARG_FINAL_TOP);
            mStartBottom = getArguments().getInt(ARG_START_BOTTOM);
            mFinalBottom = getArguments().getInt(ARG_FINAL_BOTTOM);
            mStat = (WakelockStats)getArguments().getSerializable(ARG_CUR_STAT);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wakelock_detail, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.wakelock_detail, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FragmentInteractionListener {
        // TODO: Update argument type and name
        public void onWakelockDetailSetTitle(String title);
    }

    public interface FragmentClearListener {
        public void onWakelockCleared();
    }

    public void attachClearListener(FragmentClearListener fragment)
    {
        mClearListener = fragment;
    }



}
