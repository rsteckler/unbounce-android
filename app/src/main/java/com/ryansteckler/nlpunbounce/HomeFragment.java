package com.ryansteckler.nlpunbounce;

/**
 * Created by rsteckler on 9/7/14.
 */

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.helpers.DownloadHelper;
import com.ryansteckler.nlpunbounce.helpers.LocaleHelper;
import com.ryansteckler.nlpunbounce.helpers.RootHelper;
import com.ryansteckler.nlpunbounce.helpers.SettingsHelper;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.hooks.Wakelocks;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;


import java.io.File;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeFragment extends Fragment  {

    private OnFragmentInteractionListener mListener;


    private int mSetupStep = 0;

    private int mSetupFailureStep = SETUP_FAILURE_NONE; //We're optimists  :)
    private final static int SETUP_FAILURE_NONE = 0; //We're good.  The service is running.
    private final static int SETUP_FAILURE_SERVICE = 1; //The service isn't running, but Xposed is installed.
    private final static int SETUP_FAILURE_VERSION = 2; //The service isn't running, but Xposed is installed.
    private final static int SETUP_FAILURE_XPOSED_RUNNING = 3; //Xposed isn't running ("installed")
    private final static int SETUP_FAILURE_XPOSED_INSTALL = 4; //Xposed isn't installed
    private final static int SETUP_FAILURE_ROOT = 5; //There's no root access.

    private final static String TAG = "Amplify: ";

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

        SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        String lastVersion = prefs.getString("file_version", "0");
        if (!lastVersion.equals(Wakelocks.FILE_VERSION)) {
            //Reset stats
            UnbounceStatsCollection.getInstance().recreateFiles(getActivity());
            Intent intent = new Intent(XposedReceiver.REFRESH_ACTION);
            intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);
            try {
                getActivity().sendBroadcast(intent);
            } catch (IllegalStateException ise) {

            }

        }



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
        getActivity().registerReceiver(refreshReceiver, new IntentFilter(ActivityReceiver.STATS_REFRESHED_ACTION));
        loadStatsFromSource(view);

        setupResetStatsButton(view);

        setupKarma(view);

        updatePremiumUi();

        requestRefresh();

        handleSetup(view);

    }

    private void handleSetup(final View view) {
        //All the first run stuff:
        final SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
        boolean firstRun = prefs.getBoolean("first_launch", true);

        if (!getAmplifyKernelVersion().equals(Wakelocks.VERSION) || firstRun) {

            //Show the banner
            final LinearLayout banner = (LinearLayout)view.findViewById(R.id.banner);
            banner.setVisibility(View.VISIBLE);

            //Let's find out why the service isn't running:
            if (!getAmplifyKernelVersion().equals(Wakelocks.VERSION)) {
                mSetupFailureStep = SETUP_FAILURE_VERSION;
                if (!isUnbounceServiceRunning()) {
                    mSetupFailureStep = SETUP_FAILURE_SERVICE;
                    if (!isXposedInstalled()) {
                        mSetupFailureStep = SETUP_FAILURE_XPOSED_INSTALL;
                        if (!RootHelper.isDeviceRooted()) {
                            mSetupFailureStep = SETUP_FAILURE_ROOT;
                        }
                    }
                }
            }

            //Disable navigation away from the welcome banner. //TODO:  Fade the home bar?
            getActivity().getActionBar().setHomeButtonEnabled(false);

            //Setup animations on the banner
            view.post(new Runnable() {
                @Override
                public void run() {
                    int waitForAttach = 0;
                    while (getActivity() == null && waitForAttach < 10) {
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                        }
                    }
                    ViewGroup container = (ViewGroup)getActivity().findViewById(R.id.bannerSwitcher);
                    setupBannerAnimations(container);
                    ViewGroup buttonContainer = (ViewGroup)getActivity().findViewById(R.id.welcomeButtonContainer);
                    animateButtonContainer(buttonContainer);
                }
            });

            //Blur the background and store the animation so we can reverse it when we're done
            ValueAnimator blurAnimation = blurBackground(view);

            //This progress animation drives the rest of the logic.  At different steps in the animation, we do
            //different things.  The last step takes care of "fixing" whatever problems exist.
            final ProgressBar progressChecking = (ProgressBar) view.findViewById(R.id.progressDetect);
            progressChecking.setProgress(0);

            final ValueAnimator progressAnimation = ValueAnimator.ofInt(0, 100);
            WelcomeAnimationListener welcomeListener = new WelcomeAnimationListener(banner, blurAnimation, progressChecking, progressAnimation);
            progressAnimation.addListener(welcomeListener);
            progressAnimation.addUpdateListener(welcomeListener);
            progressAnimation.setDuration(2000);
            progressAnimation.setStartDelay(200); //Create a small gap between each step, so they look discrete
            progressAnimation.setInterpolator(new LinearInterpolator());

            //Start the animations.
            blurAnimation.start();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressAnimation.start();
                }
            }, 1200); //Let the screen "come up" and blur start.  Let the user take in the screen before starting things moving.

        }
    }


    private class WelcomeAnimationListener implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {
        @Override
        public void onAnimationCancel(Animator animator) {}
        @Override
        public void onAnimationRepeat(Animator animator) {}
        @Override
        public void onAnimationStart(Animator animator) {}

        private View mParentView;
        private ValueAnimator mReverseWhenDone;
        private ProgressBar mProgressChecking;
        ValueAnimator mProgressAnimation;
        public WelcomeAnimationListener(View parentView, final ValueAnimator reverseWhenDone, ProgressBar progressChecking, ValueAnimator progressAnimation) {
            mParentView = parentView;
            mReverseWhenDone = reverseWhenDone;
            mProgressChecking = progressChecking;
            mProgressAnimation = progressAnimation;
        }

        @Override
        public void onAnimationUpdate(final ValueAnimator animator) {
            int curValue = (int) animator.getAnimatedValue();
            if (isAdded()) {
                mProgressChecking.setProgress(curValue);
                mProgressChecking.requestLayout();
            }
        }

        @Override
        public void onAnimationEnd(Animator animator) {
            //Each time the animation finishes, handle the next step
            mSetupStep++;
            Log.i(TAG, "OnAnimationEnd called.  We're on step: " + mSetupStep);

            Log.d(TAG, "Original fragment status: " + (isAdded() ? "True" : "False"));
            Log.d(TAG, "Refreshing fragment status");
            getFragmentManager().executePendingTransactions();
            Log.d(TAG, "New fragment status: " + (isAdded() ? "True" : "False"));

            if (isAdded()) {
                final TextView stepText = (TextView) mParentView.findViewById(R.id.welcomeStepText);

                if (mSetupStep == 1) {
                    Log.d(TAG, "Starting animation for step 1.");
                    Log.d(TAG, "Status of animation.isRunning (Should be false): " + mProgressAnimation.isRunning());
                    stepText.setText(getResources().getString(R.string.welcome_banner_checking_xposed));
                    mProgressChecking.setProgress(0);
                    mProgressAnimation = ValueAnimator.ofInt(0, 100);
                    mProgressAnimation.addListener(this);
                    mProgressAnimation.addUpdateListener(this);
                    mProgressAnimation.setDuration(2000);
                    mProgressAnimation.setStartDelay(200); //Create a small gap between each step, so they look discrete
                    mProgressAnimation.setInterpolator(new LinearInterpolator());
                    mProgressAnimation.start();
                    Log.d(TAG, "Started animation for step 1.");

                } else if (mSetupStep == 2) {
                    Log.i(TAG, "Starting animation for step 2.");
                    stepText.setText(getResources().getString(R.string.welcome_banner_checking_root));
                    mProgressAnimation = ValueAnimator.ofInt(0, 100);
                    mProgressAnimation.addListener(this);
                    mProgressAnimation.addUpdateListener(this);
                    mProgressAnimation.setDuration(2000);
                    mProgressAnimation.setStartDelay(200); //Create a small gap between each step, so they look discrete
                    mProgressAnimation.setInterpolator(new LinearInterpolator());
                    mProgressAnimation.start();
                    Log.d(TAG, "Started animation for step 2.");

                } else if (mSetupStep == 3) {
                    handleFinalStep();
                }
            } else {
                Log.i(TAG, "Not running animation because the fragment isn't added.");
            }
        }

        private void handleFinalStep() {

            //Setup the text on the final screen to good/bad to set user expectations
            final TextView stepText = (TextView)mParentView.findViewById(R.id.welcomeStepText);
            if (mSetupFailureStep == SETUP_FAILURE_NONE) {
                stepText.setText(getResources().getString(R.string.welcome_banner_checking_looks_great));
            } else {
                stepText.setText(getResources().getString(R.string.welcome_banner_checking_uhoh));
            }

            //This is the next button that we hide, show, and replace the text of.  Make it visible so the
            //user can move forward
            final LinearLayout nextButton = (LinearLayout)mParentView.findViewById(R.id.buttonWelcomeNext);
            nextButton.setVisibility(View.VISIBLE);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //When the user clicks the button, hide it so it doens't move during re-layout
                    nextButton.setVisibility(View.INVISIBLE);

                    //The text of the problem, and how to fix it
                    TextView problemText = (TextView) getActivity().findViewById(R.id.textWelcomeProblemDescription);
                    //The text of the next button
                    final TextView nextButtonText = (TextView) getActivity().findViewById(R.id.buttonTextWelcomeNext);

                    if (mSetupFailureStep == SETUP_FAILURE_NONE) {
                        //Everything is good!
                        handleNoFailure(problemText, nextButton);
                    } else if (mSetupFailureStep == SETUP_FAILURE_SERVICE) {
                        //Service isn't running
                        handleServiceFailure(problemText, nextButtonText, nextButton);
                    } else if (mSetupFailureStep == SETUP_FAILURE_VERSION) {
                        //Service is the wrong version
                        handleVersionFailure(problemText, nextButtonText, nextButton);
                    } else if (mSetupFailureStep == SETUP_FAILURE_XPOSED_RUNNING) {
                        //Xposed isn't running
                        handleXposedRunningFailure(problemText, nextButtonText, nextButton);
                    } else if (mSetupFailureStep == SETUP_FAILURE_XPOSED_INSTALL) {
                        //Xposed isn't installed
                        //This is the tricky one...
                        handleXposedInstalledFailure(problemText, nextButtonText, nextButton);
                    } else if (mSetupFailureStep == SETUP_FAILURE_ROOT) {
                        //The device isn't rooted
                        handleRootFailure(problemText, nextButtonText, nextButton);
                    }

                    //The views should be setup now.  Swap out the "checking" view, and swap in the "fixit" view
                    View vOut = getActivity().findViewById(R.id.welcomeDetection);
                    final View vIn = getActivity().findViewById(R.id.welcomeProblem);
                    vOut.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            vIn.setVisibility(View.VISIBLE);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    nextButton.setVisibility(View.VISIBLE);
                                }
                            }, 300);
                        }
                    }, 300);
                }
            });
        }

        private void handleRootFailure(TextView problemText, TextView nextButtonText, LinearLayout nextButton) {
            nextButtonText.setText(getResources().getString(R.string.welcome_banner_button_exit));
            String errorFormat = getResources().getString(R.string.welcome_banner_problem_root);
            String errorText = String.format(errorFormat, R.string.welcome_banner_problem_root_link);
            problemText.setText(Html.fromHtml(errorText));
            problemText.setMovementMethod(LinkMovementMethod.getInstance());
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }

        private void handleXposedInstalledFailure(TextView problemText, final TextView nextButtonText, final LinearLayout nextButton) {

            //Set the problem text.
            String errorFormat = getResources().getString(R.string.welcome_banner_problem_xposed_installed);
            String errorText = String.format(errorFormat, R.string.welcome_banner_problem_xposed_installed_link);
            problemText.setText(Html.fromHtml(errorText));
            problemText.setMovementMethod(LinkMovementMethod.getInstance());

            //Show the download view
            View welcomeDownload = getActivity().findViewById(R.id.welcomeFrameworkDownload);
            welcomeDownload.setVisibility(View.VISIBLE);

            //Set the download progress bar
            ProgressBar downloadProgress = (ProgressBar) getActivity().findViewById(R.id.progressDownloadXposed);
            downloadProgress.setProgress(0);

            //Start the download
            new DownloadHelper().startDownload(getActivity(), downloadProgress, new DownloadHelper.DownloadListener() {
                @Override
                public void onFinished(final boolean success, final String filename) {
                    //When the download is finished (which happens on a non-ui thread)
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //If the download was successful...
                            if (success) {
                                //Update the downloading text
                                TextView downloadText = (TextView)getActivity().findViewById(R.id.welcome_download_status);
                                downloadText.setText(getString(R.string.welcome_downloaded_xposed));
                                //Let them install the framework
                                nextButton.setVisibility(View.VISIBLE);
                                nextButtonText.setText(R.string.welcome_banner_button_install);
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        //Install the Xposed apk, then exit. (Ideally, install, then install/update)
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(Uri.fromFile(new File(filename)), "application/vnd.android.package-archive");
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });
                            } else {
                                TextView downloadText = (TextView)getActivity().findViewById(R.id.welcome_download_status);
                                downloadText.setText(getString(R.string.welcome_download_error_xposed));
                                nextButton.setVisibility(View.VISIBLE);
                                nextButtonText.setText(R.string.welcome_banner_button_exit);
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        getActivity().finish();
                                    }
                                });
                            }
                        }
                    });
                }
            });
        }

        private void handleXposedRunningFailure(TextView problemText, TextView nextButtonText, LinearLayout nextButton) {
            nextButtonText.setText(getActivity().getResources().getString(R.string.welcome_banner_button_fixit));
            String errorText = getResources().getString(R.string.welcome_banner_problem_xposed_running);
            problemText.setText(Html.fromHtml(errorText));

            problemText.setMovementMethod(LinkMovementMethod.getInstance());
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchXposedFramework();
                    getActivity().finish();
                }
            });
        }

        private void handleVersionFailure(TextView problemText, TextView nextButtonText, LinearLayout nextButton) {
            nextButtonText.setText(getActivity().getResources().getString(R.string.welcome_banner_button_fixit));
            String errorText = getResources().getString(R.string.welcome_banner_problem_version);
            problemText.setText(Html.fromHtml(errorText));
            problemText.setMovementMethod(LinkMovementMethod.getInstance());
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });
        }

        private void handleServiceFailure(TextView problemText, TextView nextButtonText, LinearLayout nextButton) {
            nextButtonText.setText(getActivity().getResources().getString(R.string.welcome_banner_button_fixit));
            String errorText = getResources().getString(R.string.welcome_banner_problem_service);
            problemText.setText(Html.fromHtml(errorText));
            problemText.setMovementMethod(LinkMovementMethod.getInstance());
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    launchXposedModules();
                    getActivity().finish();
                }
            });
        }

        private void handleNoFailure(TextView problemText, LinearLayout nextButton) {
            problemText.setText(getResources().getString(R.string.welcome_banner_problem_none));
            SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            SettingsHelper.resetToDefaults(prefs);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().getActionBar().setHomeButtonEnabled(true);
                    //When we're done, hide the parent view
                    mParentView.setVisibility(View.GONE);
                    mReverseWhenDone.reverse();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ImageView unblur = (ImageView) getActivity().findViewById(R.id.imageBlur);
                            unblur.setVisibility(View.GONE);
                        }
                    }, mReverseWhenDone.getDuration());
                }
            });
        }
    }

    private ValueAnimator blurBackground(View view) {
        //Blur the background
        //Show the image (now transparent)
        final ImageView imageBlur = (ImageView) view.findViewById(R.id.imageBlur);
        imageBlur.setVisibility(View.VISIBLE);
        //Fade it to opaque
        ValueAnimator blurAnimation = ValueAnimator.ofFloat(0, 0.8f);
        blurAnimation.setDuration(1000);
        blurAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        blurAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(final ValueAnimator animator) {
                float curValue = (float) animator.getAnimatedValue();
                imageBlur.setAlpha(curValue);
            }
        });
        return blurAnimation;
    }

    private void setupKarma(View view) {
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.buttonKarma1);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Catch crash
                try {
                    ((MaterialSettingsActivity) getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_2", 2, ((MaterialSettingsActivity) getActivity()).mPurchaseFinishedListener, "2");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        layout = (LinearLayout) view.findViewById(R.id.buttonKarma5);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_5", 5, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "5");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

        layout = (LinearLayout) view.findViewById(R.id.buttonKarma10);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_10", 10, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "10");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

        LinearLayout layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma1Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_1_consumable", 1, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "1");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            }
        });

        layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma5Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_5_consumable", 5, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "5");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

        layoutAgain = (LinearLayout) view.findViewById(R.id.buttonKarma10Again);
        layoutAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ((MaterialSettingsActivity)getActivity()).mHelper.launchPurchaseFlow(getActivity(), "donate_10_consumable", 10, ((MaterialSettingsActivity)getActivity()).mPurchaseFinishedListener, "10");
                } catch (IllegalStateException ise) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle(R.string.alert_noiab_title)
                            .setMessage(R.string.alert_noiab_content)
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

        TextView helpFurtherButton = (TextView) view.findViewById(R.id.buttonHelpFurther);
        final LinearLayout expanded = (LinearLayout) view.findViewById(R.id.layoutExpandedDonateAgain);
        final ScrollView scroll = (ScrollView) view.findViewById(R.id.scrollView);
        helpFurtherButton.setOnClickListener(new View.OnClickListener() {
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
    }

    private void setupResetStatsButton(final View view) {
        TextView resetStatsButton = (TextView)view.findViewById(R.id.buttonResetStats);
        resetStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View textView) {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.alert_delete_stats_title)
                        .setMessage(R.string.alert_delete_stats_content)
                        .setPositiveButton(R.string.dialog_delete, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                UnbounceStatsCollection.getInstance().resetStats(getActivity(), UnbounceStatsCollection.STAT_CURRENT);

                                Intent intent = new Intent(XposedReceiver.RESET_ACTION);
                                intent.putExtra(XposedReceiver.STAT_TYPE, UnbounceStatsCollection.STAT_CURRENT);
                                try {
                                    getActivity().sendBroadcast(intent);
                                } catch (IllegalStateException ise) {

                                }
                                loadStatsFromSource(view);
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
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private boolean isXposedInstalled() {

        PackageManager pm = getActivity().getPackageManager();

        try {
            pm.getPackageInfo("de.robv.android.xposed.installer", PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean isInstalledFromPlay() {
        String installer = getActivity().getPackageManager().getInstallerPackageName("com.ryansteckler.nlpunbounce");

        if (installer == null) {
            return false;
        }
        else {
            return installer.equals("com.android.vending");
        }
    }

    private boolean launchXposedModules() {
        Intent LaunchIntent = null;

        try {
            LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("de.robv.android.xposed.installer");
            if (LaunchIntent == null) {
                return false;
            } else {
                Intent intent = new Intent("de.robv.android.xposed.installer.OPEN_SECTION");
                intent.setPackage("de.robv.android.xposed.installer");
                intent.putExtra("section", "modules");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            if (LaunchIntent != null) {
                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LaunchIntent);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean launchXposedFramework() {
        Intent LaunchIntent = null;

        try {
            LaunchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("de.robv.android.xposed.installer");
            if (LaunchIntent == null) {
                return false;
            } else {
                Intent intent = new Intent("de.robv.android.xposed.installer.OPEN_SECTION");
                intent.setPackage("de.robv.android.xposed.installer");
                intent.putExtra("section", "install");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        } catch (Exception e) {
            if (LaunchIntent != null) {
                LaunchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(LaunchIntent);
            } else {
                return false;
            }
        }
        return true;
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
        stats.loadStats(c, true);
        String duration = stats.getWakelockDurationAllowedFormatted(c, UnbounceStatsCollection.STAT_CURRENT);
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
        textView.setText(stats.getWakelockDurationBlockedFormatted(c, UnbounceStatsCollection.STAT_CURRENT));

        //Services
        textView = (TextView)view.findViewById(R.id.textLocalServiceAcquired);
        textView.setText(String.valueOf(stats.getTotalAllowedServiceCount(c, UnbounceStatsCollection.STAT_CURRENT)));
        textView = (TextView)view.findViewById(R.id.textLocalServiceBlocked);
        textView.setText(String.valueOf(stats.getTotalBlockServiceCount(c, UnbounceStatsCollection.STAT_CURRENT)));

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
                //Global wakelocks
                TextView textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationAllowed);
                textView.setText(stats.getWakelockDurationAllowedFormatted(c, UnbounceStatsCollection.STAT_GLOBAL));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockAllowed);
                textView.setText(String.valueOf(stats.getTotalAllowedWakelockCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockBlocked);
                textView.setText(String.valueOf(stats.getTotalBlockWakelockCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationBlocked);
                textView.setText(stats.getWakelockDurationBlockedFormatted(c, UnbounceStatsCollection.STAT_GLOBAL));

                //Global services
                textView = (TextView)view.findViewById(R.id.textGlobalServiceAllowed);
                textView.setText(String.valueOf(stats.getTotalAllowedServiceCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalServiceBlocked);
                textView.setText(String.valueOf(stats.getTotalBlockServiceCount(c, UnbounceStatsCollection.STAT_GLOBAL)));

                //Global Alarms
                textView = (TextView)view.findViewById(R.id.textGlobalAlarmAllowed);
                textView.setText(String.valueOf(stats.getTotalAllowedAlarmCount(c, UnbounceStatsCollection.STAT_GLOBAL)));
                textView = (TextView)view.findViewById(R.id.textGlobalAlarmBlocked);
                textView.setText(String.valueOf(stats.getTotalBlockAlarmCount(c, UnbounceStatsCollection.STAT_GLOBAL)));

                    }
        });
        } else {
            //Global wakelocks
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockBlocked);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalWakelockDurationBlocked);
            textView.setText(getResources().getString(R.string.stat_disabled));

            //Global services
            textView = (TextView)view.findViewById(R.id.textGlobalServiceAllowed);
            textView.setText(getResources().getString(R.string.stat_disabled));
            textView = (TextView)view.findViewById(R.id.textGlobalServiceBlocked);
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

    private void animateButtonContainer(final ViewGroup container) {
        LayoutTransition lt = container.getLayoutTransition();
        if (lt == null) {
            lt = new LayoutTransition();
        }
        lt.enableTransitionType(LayoutTransition.APPEARING);
        lt.disableTransitionType(LayoutTransition.DISAPPEARING);
        lt.setDuration(300);
        container.setLayoutTransition(lt);
    }

    private void setupBannerAnimations(ViewGroup container) {
        AnimatorSet animatorDisappear = getDisappearAnimation(container);
        AnimatorSet animatorAppear = getAppearAnimation(container);

        LayoutTransition lt = container.getLayoutTransition();
        if (lt == null) {
            lt = new LayoutTransition();
        }
        lt.setAnimator(LayoutTransition.DISAPPEARING, animatorDisappear);
        lt.setAnimator(LayoutTransition.APPEARING, animatorAppear);
        lt.setStartDelay(LayoutTransition.APPEARING, 0);
        lt.setDuration(300);
        container.setLayoutTransition(lt);

    }

    private AnimatorSet getDisappearAnimation(ViewGroup container) {
        float endLocation = container.getHeight();
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = endLocation / (metrics.densityDpi / 160f);

        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator moveBanner = ObjectAnimator.ofFloat(null, View.TRANSLATION_Y, 0, dp);
        ObjectAnimator fadeBanner = ObjectAnimator.ofFloat(null, View.ALPHA, 1, 0);
        animator.playTogether(moveBanner, fadeBanner);
        return animator;
    }

    private AnimatorSet getAppearAnimation(ViewGroup container) {
        float endLocation = container.getHeight() * -1;
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = endLocation / (metrics.densityDpi / 160f);

        AnimatorSet animator = new AnimatorSet();
        ObjectAnimator moveBanner = ObjectAnimator.ofFloat(null, View.TRANSLATION_Y, dp, 0);
        ObjectAnimator fadeBanner = ObjectAnimator.ofFloat(null, View.ALPHA, 0, 1);
        animator.playTogether(moveBanner, fadeBanner);
        return animator;
    }


    public boolean isUnbounceServiceRunning() {
        //The Unbounce hook changes this to true.
        return false;
    }

    public String getAmplifyKernelVersion() {
        //The Unbounce hook changes this to true.
        return "0";
    }



    public boolean isXposedRunning() {
//        return true;
        return new File("/data/data/de.robv.android.xposed.installer/bin/XposedBridge.jar").exists();
    }


}
