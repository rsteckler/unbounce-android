package com.ryansteckler.nlpunbounce;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by rsteckler on 11/10/15.
 */
public class RegexDetailFragment extends BaseDetailFragment {

    private String mDefaultValue = "";
    private String mDefaultSeconds = "240";
    private String mDefaultSetName = "wakelock";
    private String mEnabled = "enabled";

    @Override
    public RegexDetailFragment newInstance() {
        return new RegexDetailFragment();
    }

    public RegexDetailFragment() {
        // Required empty public constructor
    }

    public static RegexDetailFragment newInstance(int startTop, int finalTop, int startBottom, int finalBottom, BaseStats stat, boolean taskerMode,
                                                  String defaultValue, String defaultSeconds, String enabled, String defaultSetName) {
        RegexDetailFragment f = new RegexDetailFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt(ARG_START_TOP, startTop);
        args.putInt(ARG_FINAL_TOP, finalTop);
        args.putInt(ARG_START_BOTTOM, startBottom);
        args.putInt(ARG_FINAL_BOTTOM, finalBottom);
        args.putSerializable(ARG_CUR_STAT, stat);
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        args.putString("defaultValue", TextUtils.isEmpty(defaultValue) ? "" : defaultValue);
        args.putString("defaultSeconds", TextUtils.isEmpty(defaultSeconds) ? "240" : defaultSeconds);
        args.putString("defaultSetName", TextUtils.isEmpty(defaultSetName) ? "wakelock" : defaultSetName);
        args.putString("defaultEnabled", TextUtils.isEmpty(enabled) ? "enabled" : enabled);
        f.setArguments(args);

        return f;
    }

    @Override
    public String getName() {
        return mDefaultValue;
    }

    public long getSeconds() {
        try {
            return Long.parseLong(mDefaultSeconds);
        } catch (NumberFormatException ex) {
            return 240;
        }
    }

    @Override
    protected void warnUnknown(final Switch onOff) {
        onOff.toggle();
        updateEnabled(onOff.isChecked());
    }

    @Override
    protected void updateEnabled(boolean b) {
        String prevBlockName = mDefaultValue + "$$||$$" + mDefaultSeconds + "$$||$$" + mEnabled;
        mEnabled = (b) ? "enabled" : "disabled";
        String blockName = mDefaultValue + "$$||$$" + mDefaultSeconds + "$$||$$" + mEnabled;

        if (!mTaskerMode) {
            SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            Set<String> sampleSet = new HashSet<String>();
            Set<String> set = new HashSet<String>(prefs.getStringSet(mDefaultSetName + "_regex_set", sampleSet));
            if (!TextUtils.isEmpty(mDefaultValue)) {
                set.remove(prevBlockName);
                set.add(blockName);
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putStringSet(mDefaultSetName + "_regex_set", set);
            editor.apply();
        }

        //Enable or disable the seconds setting.
        getView().findViewById(R.id.editRegexSeconds).setEnabled(b);
        View panel = (View)getView().findViewById(R.id.settingsPanelSeconds);
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

        TextView textView = (TextView) getView().findViewById(R.id.editRegex);
        textView.setEnabled(b);
        textView.clearFocus();
        panel = (View)getView().findViewById(R.id.settingsPanelRegex);
        panel.setBackgroundDrawable(backgroundColor);
        panel.setAlpha(b ? 1 : (float) .4);

        if (mClearListener != null)
        {
            mClearListener.onCleared();
        }
    }

    @Override
    protected void loadStatsFromSource(View view) { /* empty */ }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        mDefaultValue = "";
        mDefaultSeconds = "240";
        mDefaultSetName = "wakelock";
        mEnabled = "enabled";
        if (savedInstance != null) {
            mDefaultValue = savedInstance.getString("defaultValue");
            mDefaultSeconds = savedInstance.getString("defaultSeconds");
            mDefaultSetName = savedInstance.getString("defaultSetName");
            mEnabled = savedInstance.getString("defaultEnabled");
        } else if (getArguments() != null) {
            mDefaultValue = getArguments().getString("defaultValue");
            mDefaultSeconds = getArguments().getString("defaultSeconds");
            mDefaultSetName = getArguments().getString("defaultSetName");
            mEnabled = getArguments().getString("defaultEnabled");
        }
    }

    private String getDescriptionText(String regex) {
        ArrayList<BaseStats> events;
        if (mDefaultSetName.equals("wakelock")) {
            events = UnbounceStatsCollection.getInstance().toWakelockArrayList(getView().getContext());
        } else if (mDefaultSetName.equals("alarm")) {
            events = UnbounceStatsCollection.getInstance().toAlarmArrayList(getView().getContext());
        } else {
            return "Description unavailable";
        }

        Set<String> matchingEvents = new HashSet<>();

        for (BaseStats event : events) {
            String eventName = event.getName();
            if (eventName.matches(regex))
                matchingEvents.add(eventName);
        }

        String descriptionText = getResources().getString(R.string.desc_regex_unknown);
        if (matchingEvents.size() > 0)
            descriptionText = String.format(getResources().getString(R.string.desc_regex), matchingEvents.size(), events.size())
                + "\n\n" + TextUtils.join("\n", matchingEvents);
        return descriptionText;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView description = (TextView) view.findViewById(R.id.textViewDescription);
        description.setText(getDescriptionText(mDefaultValue));

        SharedPreferences prefs = getActivity().getSharedPreferences(RegexDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);

        final EditText editSeconds = (EditText) view.findViewById(R.id.editRegexSeconds);

        editSeconds.setText(mDefaultSeconds);
        editSeconds.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    return handleTextChange(textView, editSeconds);
                }
                return false;
            }
        });
        editSeconds.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    handleTextChange((TextView) view, editSeconds);
                }
            }
        });

        final EditText editRegex = (EditText) view.findViewById(R.id.editRegex);

        editRegex.setText(mDefaultValue);
        editRegex.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    return handleTextChange(textView, editRegex);
                }
                return false;
            }
        });
        editRegex.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    handleTextChange((TextView) view, editRegex);
                }
            }
        });

        final Switch onOff = (Switch) view.findViewById(R.id.switchStat);
        boolean enabled = mEnabled.equals("enabled");
        onOff.setChecked(enabled);

        getView().findViewById(R.id.editRegexSeconds).setEnabled(onOff.isChecked());

        View panel = (View)getView().findViewById(R.id.settingsPanelSeconds);
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

        getView().findViewById(R.id.editRegex).setEnabled(onOff.isChecked());
        panel = (View)getView().findViewById(R.id.settingsPanelRegex);
        panel.setBackgroundDrawable(backgroundColor);
        panel.setAlpha(enabled ? 1 : (float) .4);

    }

    private boolean handleTextChange(TextView textView, EditText edit) {
        try {
            String prevBlockName = mDefaultValue + "$$||$$" + mDefaultSeconds + "$$||$$" + mEnabled;
            boolean regexValid = true;
            boolean regexChanged = false;
            if (edit.getId() == R.id.editRegexSeconds) {
                mDefaultSeconds = Long.toString(Long.parseLong(textView.getText().toString()));
            } else if (edit.getId() == R.id.editRegex) {
                try {
                    Pattern.compile(textView.getText().toString());
                } catch (PatternSyntaxException ex) {
                    regexValid = false;
                }
                if (regexValid) {
                    regexChanged = true;
                    mDefaultValue = textView.getText().toString();
                    TextView description = (TextView) getView().findViewById(R.id.textViewDescription);
                    description.setText(getDescriptionText(mDefaultValue));
                } else {
                    textView.setError("Invalid regex");
                }
            }
            String blockName = mDefaultValue + "$$||$$" + mDefaultSeconds + "$$||$$" + mEnabled;

            if (blockName.equals(prevBlockName))
                return true;

            if (mClearListener != null)
            {
                mClearListener.onCleared();
            }

            if (!mTaskerMode) {
                SharedPreferences prefs = getActivity().getSharedPreferences(RegexDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);
                Set<String> sampleSet = new HashSet<String>();
                Set<String> set = new HashSet<String>(prefs.getStringSet(mDefaultSetName + "_regex_set", sampleSet));
                if (!TextUtils.isEmpty(mDefaultValue)) {
                    if (regexChanged) {
                        // check if this regex already exists
                        for (Iterator<String> i = set.iterator(); i.hasNext();) {
                            String str = i.next();
                            if (str.startsWith(mDefaultValue)) {
                                textView.setError("Duplicate regex");
                                return true;
                            }
                        }
                    }

                    set.remove(prevBlockName);
                    set.add(blockName);
                }

                SharedPreferences.Editor editor = prefs.edit();
                editor.putStringSet(mDefaultSetName + "_regex_set", set);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_regex_detail, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        //getActivity().getMenuInflater().inflate(R.menu.alarm_detail, menu);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
