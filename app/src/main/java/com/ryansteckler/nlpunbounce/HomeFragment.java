package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/7/14.
 */

import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.helpers.LocaleHelper;
import com.ryansteckler.nlpunbounce.helpers.NetworkHelper;
import com.ryansteckler.nlpunbounce.helpers.SettingsHelper;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.util.HashMap;
import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocaleHelper.onActivityCreateSetLocale(this.getActivity());
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(refreshReceiver);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListener.onHomeSetTitle(getResources().getString(R.string.title_home));

        //Register for stats updates
        refreshReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                loadStatsFromSource(view);
            }
        };
        //Register when new stats come in.
        getActivity().registerReceiver(refreshReceiver, new IntentFilter(ActivityReceiver.SEND_STATS_ACTION));

        loadStatsFromSource(view);
        TextView textView;

        textView = (TextView)view.findViewById(R.id.buttonResetStats);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View textView) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.alert_delete_stats_title)
                        .setMessage(R.string.alert_delete_stats_content)
                        .setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UnbounceStatsCollection.getInstance().resetStats(getActivity(), UnbounceStatsCollection.STAT_CURRENT);
                                loadStatsFromSource(view);

                                Intent intent = new Intent(XposedReceiver.RESET_ACTION);
                                intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);
                                try {
                                    getActivity().sendBroadcast(intent);
                                } catch (IllegalStateException ise) {

                                }
                            }
                        })
                        .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        if (prefs.getBoolean("first_launch", true)) {
            SettingsHelper.resetToDefaults(prefs);
            textView = (TextView)view.findViewById(R.id.textviewCloseBanner);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View textview) {
                    //Animate this view out of sight
                    ViewGroup bannerContainer = (ViewGroup)view;
                    LayoutTransition lt = new LayoutTransition();

                    float endLocation = view.getHeight();
                    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
                    float dp = endLocation / (metrics.densityDpi / 160f);

                    AnimatorSet animator = new AnimatorSet();
                    ObjectAnimator moveBanner = ObjectAnimator.ofFloat(null, View.TRANSLATION_Y, 0, dp);
                    ObjectAnimator fadeBanner = ObjectAnimator.ofFloat(null, View.ALPHA, 1, 0);
                    animator.playTogether(moveBanner, fadeBanner);

                    lt.setAnimator(LayoutTransition.DISAPPEARING, animator);
                    bannerContainer.setLayoutTransition(lt);
                    View banner = (View)view.findViewById(R.id.banner);
                    bannerContainer.removeView(banner);
                }
            });

            View banner = view.findViewById(R.id.banner);
            banner.setVisibility(View.VISIBLE);

        }


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

        LinearLayout layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma1Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_1_consumable", 1, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "1");
            }
        });

        layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma5Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_5_consumable", 5, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "5");
            }
        });

        layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma10Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_10_consumable", 10, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "10");
            }
        });

        textView = (TextView) view.findViewById(R.id.buttonHelpFurther);
        final LinearLayout expanded = (LinearLayout) view.findViewById(R.id.layoutExpandedDonateAgain);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.scrollView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expanded.setVisibility(View.VISIBLE);
                scroll.post(new Runnable() {
                    @Override
                    public void run() {
                        scroll.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
        updatePremiumUi();

        requestRefresh();
    }

    private BroadcastReceiver refreshReceiver;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            updatePremiumUi();
        }
    }


    private void updatePremiumUi() {
        if (((MaterialSettingsActivity)getActivity()).isPremium()) {
            View againView = (View) getActivity().findViewById(R.id.layoutDonateAgain);
            againView.setVisibility(View.VISIBLE);
            View donateView = (View) getActivity().findViewById(R.id.layoutDonate);
            donateView.setVisibility(View.GONE);
        }
    }

    private void loadStatsFromSource(final View view) {
        final UnbounceStatsCollection stats = UnbounceStatsCollection.getInstance();
        final Context c = getActivity();
        String duration = stats.getDurationAllowedFormatted(c, UnbounceStatsCollection.STAT_CURRENT);
        //Wakelocks
        TextView textView = (TextView)view.findViewById(R.id.textLocalWakeTimeAllowed);
        textView.setText(duration);
        textView = (TextView)view.findViewById(R.id.textRunningSince);
        textView.setText(stats.getRunningSinceFormatted());
        textView = (TextView)view.findViewById(R.id.textLocalWakeAcquired);
        textView.setText(String.valueOf(stats.getTotalAllowedWakelockCount(c, UnbounceStatsCollection.STAT_CURRENT)));
        textView = (TextView)view.findViewById(R.id.textLocalWakeBlocked);
        textView.setText(String.valueOf(stats.getTotalBlockWakelockCount(c, UnbounceStatsCollection.STAT_CURRENT)));
        textView = (TextView)view.findViewById(R.id.textLocalWakeTimeBlocked);
        textView.setText(stats.getDurationBlockedFormatted(c, UnbounceStatsCollection.STAT_CURRENT));

        //Alarms
        textView = (TextView)view.findViewById(R.id.textLocalAlarmsAcquired);
        textView.setText(String.valueOf(stats.getTotalAllowedAlarmCount(c, UnbounceStatsCollection.STAT_CURRENT)));
        textView = (TextView)view.findViewById(R.id.textLocalAlarmsBlocked);
        textView.setText(String.valueOf(stats.getTotalBlockAlarmCount(c, UnbounceStatsCollection.STAT_CURRENT)));

        //Global wakelocks.
        //Kick off a refresh

        SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        if (prefs.getBoolean("global_participation", true)) {
            stats.getStatsFromNetwork(c, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                TextView textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationAllowed);
                textView.setText(stats.getDurationAllowedFormatted(c, UnbounceStatsCollection.STAT_GLOBAL));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockAllowed);
                textView.setText(String.valueOf(stats.getTotalAllowedWakelockCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockBlocked);
                textView.setText(String.valueOf(stats.getTotalBlockWakelockCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationBlocked);
                textView.setText(stats.getDurationBlockedFormatted(c, UnbounceStatsCollection.STAT_GLOBAL));

                //Global Alarms
                textView = (TextView)view.findViewById(R.id.textGlobalAlarmAllowed);
                textView.setText(String.valueOf(stats.getTotalAllowedAlarmCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalAlarmBlocked);
                textView.setText(String.valueOf(stats.getTotalBlockAlarmCount(c, UnbounceStatsCollection.STAT_GLOBAL)));

                    }
        });
        } else {
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockBlocked);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationBlocked);
            textView.setText(getResources().getString(R.string.stat_disabled));

            //Global Alarms
            textView = (TextView)view.findViewById(R.id.textGlobalAlarmAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalAlarmBlocked);
            textView.setText(getResources().getString(R.string.stat_disabled));

        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            requestRefresh();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void requestRefresh() {
        Intent intent = new Intent(XposedReceiver.REFRESH_ACTION);
        try {
            getActivity().sendBroadcast(intent);
        } catch (IllegalStateException ise) {

        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
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
        public void onHomeSetTitle(String id);
    }

}
