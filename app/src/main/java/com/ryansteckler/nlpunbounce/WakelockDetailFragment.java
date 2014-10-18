package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;
import com.ryansteckler.nlpunbounce.tasker.TaskerActivity;


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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_START_TOP = "startTop";
    private static final String ARG_FINAL_TOP = "finalTop";
    private static final String ARG_START_BOTTOM = "startBottom";
    private static final String ARG_FINAL_BOTTOM = "finalBottom";
    private static final String ARG_CUR_STAT = "curStat";
    private static final String ARG_TASKER_MODE = "taskerMode";

    private int mStartTop;
    private int mFinalTop;
    private int mStartBottom;
    private int mFinalBottom;
    private WakelockStats mStat;
    private boolean mTaskerMode;
    private FragmentClearListener mClearListener = null;

    private boolean mKnownSafeWakelock = false;
    private boolean mFreeWakelock = false;

    private FragmentInteractionListener mListener;

    public String getName() {
        return mStat.getName();
    }

    public boolean getEnabled() {
        Switch onOff = (Switch)getActivity().findViewById(R.id.switchWakelock);
        return onOff.isChecked();
    }

    public long getSeconds() {
        EditText editSeconds = (EditText)getActivity().findViewById(R.id.editWakelockSeconds);
        String text = editSeconds.getText().toString();
        long seconds = Long.parseLong(text);
        return seconds;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        ExpandingLayout anim = (ExpandingLayout)getActivity().findViewById(R.id.layoutDetails);
        anim.setAnimationBounds(mStartTop, mFinalTop, mStartBottom, mFinalBottom);
        super.onViewCreated(view, savedInstanceState);
        if (mListener != null) {
            mListener.onWakelockDetailSetTitle(mStat.getName());
            mListener.onWakelockDetailSetTaskerTitle(getResources().getString(R.string.tasker_choose_settings));
        }

        loadStatsFromSource(view);

        final EditText edit = (EditText)view.findViewById(R.id.editWakelockSeconds);

        SharedPreferences prefs = getActivity().getSharedPreferences(WakelockDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);
        String blockSeconds = "wakelock_" + mStat.getName() + "_seconds";
        Long blockSecondsLong = prefs.getLong(blockSeconds, 240);
        edit.setText(String.valueOf(blockSecondsLong));
        edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    return handleTextChange(textView, edit);
                }
                return false;
            }
        });
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    handleTextChange((TextView)view, edit);
                }
            }
        });

        final Switch onOff = (Switch)view.findViewById(R.id.switchWakelock);
        //TODO:  If we're in tasker mode, and have an existing configuration, load that instead of prefs.
        boolean enabled = false;
        String blockName = "wakelock_" + mStat.getName() + "_enabled";
        enabled = prefs.getBoolean(blockName, false);

        onOff.setChecked(enabled);
        getView().findViewById(R.id.editWakelockSeconds).setEnabled(onOff.isChecked());

        onOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Check license
                    boolean isPremium = false;
                    //We may be running under the TaskerActivity or the MaterialSettingsActivity.
                    Activity baseActivity = getActivity();
                    if (baseActivity instanceof MaterialSettingsActivity) {
                        isPremium = ((MaterialSettingsActivity) getActivity()).isPremium();
                    }
                    else if (baseActivity instanceof TaskerActivity) {
                        isPremium = ((TaskerActivity) getActivity()).isPremium();
                    }

                    if (isPremium || mFreeWakelock) {
                        final boolean b = !onOff.isChecked();
                        if (b && !mKnownSafeWakelock) {
                            warnUnknownWakelock(onOff);
                        } else {
                            updateEnabledWakelock(b);
                            return false;
                        }
                    } else {
                        //Deny based on licensing.
                        warnLicensing(onOff);
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        });

        View panel = (View)getView().findViewById(R.id.settingsPanel);
        TypedValue backgroundValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        int resId = enabled ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;
        boolean success = theme.resolveAttribute(resId, backgroundValue, true);
        Drawable backgroundColor = getResources().getDrawable(R.drawable.header_background_dark);
        if (success) {
            backgroundColor = getResources().getDrawable(backgroundValue.resourceId);
        }
        panel.setBackgroundDrawable(backgroundColor);
        panel.setAlpha(enabled ? 1 : (float) .4);

        TextView resetButton = (TextView)view.findViewById(R.id.buttonResetStats);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View resetView) {
                //Reset stats
                Intent intent = new Intent(XposedReceiver.RESET_ACTION);
                intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);
                intent.putExtra(XposedReceiver.STAT_NAME, mStat.getName());
                try {
                    getActivity().sendBroadcast(intent);
                } catch (IllegalStateException ise) {

                }
                UnbounceStatsCollection.getInstance().resetLocalStats(mStat.getName());
                loadStatsFromSource(view);
                if (mClearListener != null)
                {
                    mClearListener.onWakelockCleared();
                }
            }
        });

        TextView description = (TextView)view.findViewById(R.id.textViewWakelockDescription);
        String descriptionText = EventLookup.getDescription(getActivity(), mStat.getName());
        description.setText(descriptionText);
        mKnownSafeWakelock = EventLookup.isSafe(mStat.getName()) == EventLookup.SAFE;
        mFreeWakelock = EventLookup.isFree(mStat.getName());
    }

    private boolean handleTextChange(TextView textView, EditText edit) {
        try {
            long seconds = Long.parseLong(textView.getText().toString());
            if (!mTaskerMode) {
                //Save to prefs
                SharedPreferences prefs = getActivity().getSharedPreferences(WakelockDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);
                String blockName = "wakelock_" + mStat.getName() + "_seconds";
                SharedPreferences.Editor editor = prefs.edit();
                editor.putLong(blockName, seconds);
                editor.apply();
            }

            textView.clearFocus();
            // hide virtual keyboard
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            return true;

        } catch (NumberFormatException nfe)
        {
            //Not a number.  Android let us down.
        }
        return false;
    }

    private void warnLicensing(final Switch onOff) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_nopro_title)
                .setMessage(R.string.alert_nopro_content)
                .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onOff.setChecked(false);
                        updateEnabledWakelock(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }

    private void warnUnknownWakelock(final Switch onOff) {
        new AlertDialog.Builder(getActivity())
            .setTitle(R.string.alert_unknown_wakelock_title)
            .setMessage(R.string.alert_unknown_wakelock_content)
            .setPositiveButton(R.string.dialog_unbounce, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    onOff.setChecked(true);
                    updateEnabledWakelock(true);
                }
            })
            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //don't set the switch
                    onOff.setChecked(false);
                    updateEnabledWakelock(false);
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void updateEnabledWakelock(boolean b) {
        String blockName = "wakelock_" + mStat.getName() + "_enabled";
        if (!mTaskerMode) {
            //Save to prefs
            SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(blockName, b);
            editor.apply();
        }

        //Enable or disable the seconds setting.
        getView().findViewById(R.id.editWakelockSeconds).setEnabled(b);
        View panel = (View)getView().findViewById(R.id.settingsPanel);
        TypedValue backgroundValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        int resId = b ? R.attr.background_panel_enabled : R.attr.background_panel_disabled;
        boolean success = theme.resolveAttribute(resId, backgroundValue, true);
        Drawable backgroundColor = getResources().getDrawable(R.drawable.header_background_dark);
        if (success) {
            backgroundColor = getResources().getDrawable(backgroundValue.resourceId);
        }
        panel.setBackgroundDrawable(backgroundColor);
        panel.setAlpha(b ? 1 : (float) .4);

        if (mClearListener != null)
        {
            mClearListener.onWakelockCleared();
        }
    }

    private void loadStatsFromSource(View view) {
        UnbounceStatsCollection coll = UnbounceStatsCollection.getInstance();
        mStat = coll.getWakelockStats(getActivity(), mStat.getName());

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
    public static WakelockDetailFragment newInstance(int startTop, int finalTop, int startBottom, int finalBottom, WakelockStats stat, boolean taskerMode) {
        WakelockDetailFragment fragment = new WakelockDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_START_TOP, startTop);
        args.putInt(ARG_FINAL_TOP, finalTop);
        args.putInt(ARG_START_BOTTOM, startBottom);
        args.putInt(ARG_FINAL_BOTTOM, finalBottom);
        args.putSerializable(ARG_CUR_STAT, stat);
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);
        return fragment;
    }

    public WakelockDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());

        if (getArguments() != null) {
            mStartTop = getArguments().getInt(ARG_START_TOP);
            mFinalTop = getArguments().getInt(ARG_FINAL_TOP);
            mStartBottom = getArguments().getInt(ARG_START_BOTTOM);
            mFinalBottom = getArguments().getInt(ARG_FINAL_BOTTOM);
            mStat = (WakelockStats)getArguments().getSerializable(ARG_CUR_STAT);
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
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
        public void onWakelockDetailSetTitle(String title);
        public void onWakelockDetailSetTaskerTitle(String title);
    }

    public interface FragmentClearListener {
        public void onWakelockCleared();
    }

    public void attachClearListener(FragmentClearListener fragment)
    {
        mClearListener = fragment;
    }



}
