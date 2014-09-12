package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/7/14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.models.WakelockStatsCollection;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance(int sectionNumber) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.onHomeSetTitle("Home");
        loadStatsFromSource(view);
        TextView textView;

        textView = (TextView)view.findViewById(R.id.buttonResetStats);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View textView) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Delete all stats?")
                        .setMessage("This will reset stats for all of your wakelocks!")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                WakelockStatsCollection stats = WakelockStatsCollection.getInstance();
                                stats.resetStats(getActivity());
                                loadStatsFromSource(view);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.buttonKarma1);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_1", 1, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "1");
            }
        });

        layout = (LinearLayout) view.findViewById(R.id.buttonKarma5);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_5", 5, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "5");
            }
        });

        layout = (LinearLayout) view.findViewById(R.id.buttonKarma10);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_10", 10, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "10");
            }
        });

    }

    private void loadStatsFromSource(View view) {
        WakelockStatsCollection stats = WakelockStatsCollection.getInstance();
        String duration = stats.getDurationAllowedFormatted(getActivity());
        TextView textView = (TextView)view.findViewById(R.id.textLocalWakeTimeAllowed);
        textView.setText(duration);
        textView = (TextView)view.findViewById(R.id.textLocalWakeAcquired);
        textView.setText(String.valueOf(stats.getTotalAllowedCount(getActivity())));
        textView = (TextView)view.findViewById(R.id.textLocalWakeBlocked);
        textView.setText(String.valueOf(stats.getTotalBlockCount(getActivity())));
        textView = (TextView)view.findViewById(R.id.textLocalWakeTimeBlocked);
        textView.setText(stats.getDurationBlockedFormatted(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }


    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onHomeSetTitle(String id);
    }

}
