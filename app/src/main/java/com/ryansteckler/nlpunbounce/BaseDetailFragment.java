package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.helpers.LocaleHelper;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.tasker.TaskerActivity;

/**
 * Created by rsteckler on 10/20/14.
 */
public abstract class BaseDetailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    protected static final String ARG_START_TOP = "startTop";
    protected static final String ARG_FINAL_TOP = "finalTop";
    protected static final String ARG_START_BOTTOM = "startBottom";
    protected static final String ARG_FINAL_BOTTOM = "finalBottom";
    protected static final String ARG_CUR_STAT = "curStat";
    protected static final String ARG_TASKER_MODE = "taskerMode";

    protected int mStartTop;
    protected int mFinalTop;
    protected int mStartBottom;
    protected int mFinalBottom;
    protected BaseStats mStat;
    protected boolean mTaskerMode;

    protected FragmentClearListener mClearListener = null;

    protected boolean mKnownSafe = false;
    protected boolean mFree = false;

    protected FragmentInteractionListener mListener;

    protected abstract void loadStatsFromSource(View view);

    protected abstract void warnUnknown(Switch onOff);

    protected abstract void updateEnabled(boolean b);

    protected abstract BaseDetailFragment newInstance();


    public String getName() {
        return mStat.getName();
    }

    public boolean getEnabled() {
        Switch onOff = (Switch) getActivity().findViewById(R.id.switchStat);
        return onOff.isChecked();
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ExpandingLayout anim = (ExpandingLayout) getActivity().findViewById(R.id.layoutDetails);
        anim.setAnimationBounds(mStartTop, mFinalTop, mStartBottom, mFinalBottom);
        super.onViewCreated(view, savedInstanceState);
        if (mListener != null) {
            mListener.onDetailSetTitle(mStat.getName());
            mListener.onDetailSetTaskerTitle(getResources().getString(R.string.tasker_choose_settings));
        }

        loadStatsFromSource(view);

        SharedPreferences prefs = getActivity().getSharedPreferences(AlarmDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);

        final Switch onOff = (Switch) view.findViewById(R.id.switchStat);
        //TODO:  If we're in tasker mode, and have an existing configuration, load that instead of prefs.

        onOff.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    //Check license
                    boolean isPremium = false;
                    //We may be running under the TaskerActivity or the MaterialSettingsActivity.
                    Activity baseActivity = getActivity();
                    if (baseActivity instanceof MaterialSettingsActivity) {
                        isPremium = ((MaterialSettingsActivity) getActivity()).isPremium();
                    } else if (baseActivity instanceof TaskerActivity) {
                        isPremium = ((TaskerActivity) getActivity()).isPremium();
                    }

                    if (isPremium || mFree) {
                        final boolean b = !onOff.isChecked();
                        if (b && !mKnownSafe) {
                            warnUnknown(onOff);
                        } else {
                            updateEnabled(b);
                            return false;
                        }
                    } else {
                        //Deny based on licensing.
                        warnLicensing(onOff);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do not call super because of a bug in Android?

            }
        });

        TextView resetButton = (TextView) view.findViewById(R.id.buttonResetStats);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View resetView) {
                //Reset stats
                UnbounceStatsCollection stats = UnbounceStatsCollection.getInstance();
                stats.resetStats(getActivity(), mStat.getName());
                loadStatsFromSource(view);
                if (mClearListener != null) {
                    mClearListener.onCleared();
                }
            }
        });

        TextView description = (TextView) view.findViewById(R.id.textViewDescription);
        String descriptionText = EventLookup.getDescription(getActivity(), mStat.getName());
        description.setText(descriptionText);


        mKnownSafe = EventLookup.isSafe(mStat.getName()) == EventLookup.SAFE;
        mFree = EventLookup.isFree(mStat.getName());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AlarmlockDetailFragment.
     */
    public BaseDetailFragment newInstance(int startTop, int finalTop, int startBottom, int finalBottom, BaseStats stat, boolean taskerMode) {
        BaseDetailFragment fragment = newInstance();
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

    public BaseDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());
        LocaleHelper.onActivityCreateSetLocale(this.getActivity());

        if (getArguments() != null) {
            mStartTop = getArguments().getInt(ARG_START_TOP);
            mFinalTop = getArguments().getInt(ARG_FINAL_TOP);
            mStartBottom = getArguments().getInt(ARG_START_BOTTOM);
            mFinalBottom = getArguments().getInt(ARG_FINAL_BOTTOM);
            mStat = (BaseStats) getArguments().getSerializable(ARG_CUR_STAT);
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }
        setHasOptionsMenu(true);
    }

    protected void warnLicensing(final Switch onOff) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_nopro_title)
                .setMessage(R.string.alert_nopro_content)
                .setNeutralButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onOff.setChecked(false);
                        updateEnabled(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FragmentInteractionListener {
        public void onDetailSetTitle(String title);

        public void onDetailSetTaskerTitle(String title);
    }

    public interface FragmentClearListener {
        public void onCleared();
    }

    public void attachClearListener(FragmentClearListener fragment) {
        mClearListener = fragment;
    }


}
