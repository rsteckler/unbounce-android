package com.ryansteckler.nlpunbounce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsteckler on 11/10/15.
 */
public class RegexDialogFragment  extends android.app.DialogFragment{

    String mDefaultValue = "";
    String mDefaultSeconds = "";
    String mDefaultSetName = "wakelock";

    public static RegexDialogFragment newInstance(String defaultValue, String defaultSeconds, String defaultSetName) {
        RegexDialogFragment f = new RegexDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("defaultValue", defaultValue);
        args.putString("defaultSeconds", defaultSeconds);
        args.putString("defaultSetName", defaultSetName);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        mDefaultValue = "";
        mDefaultSeconds = "240";
        mDefaultSetName = "wakelock";
        if (savedInstance != null) {
            mDefaultValue = savedInstance.getString("defaultValue");
            mDefaultSeconds = savedInstance.getString("defaultSeconds");
            mDefaultSetName = savedInstance.getString("defaultSetName");
        } else if (getArguments() != null) {
            mDefaultValue = getArguments().getString("defaultValue");
            mDefaultSeconds = getArguments().getString("defaultSeconds");
            mDefaultSetName = getArguments().getString("defaultSetName");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Custom Regex");

        final TextView regexLabel = new TextView(getActivity());
        regexLabel.setText("Enter the Regex to match");
        int paddingE = getResources().getDimensionPixelOffset(R.dimen.card_external_padding);
        int paddingI = getResources().getDimensionPixelOffset(R.dimen.card_internal_padding);
        regexLabel.setPadding(paddingI, paddingI, paddingI, 0);

        // Use an EditText view to get user input.
        final EditText input = new EditText(getActivity());
        input.setId(0);
        input.setSingleLine(true);
        input.setPadding(paddingI, 0, paddingI, 0);

        final TextView secondsLabel = new TextView(getActivity());
        secondsLabel.setText("Enter the interval in seconds");
        secondsLabel.setPadding(paddingI, paddingE, paddingI, 0);

        final EditText secondsEdit = new EditText(getActivity());
        secondsEdit.setSingleLine(true);
        secondsEdit.setInputType(InputType.TYPE_CLASS_NUMBER);
        secondsEdit.setPadding(paddingI, 0, paddingI, paddingI);

        LinearLayout parentView = new LinearLayout(getActivity());
        parentView.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams LLParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentView.setLayoutParams(LLParams);
        parentView.addView(regexLabel);
        parentView.addView(input);
        parentView.addView(secondsLabel);
        parentView.addView(secondsEdit);

        builder.setView(parentView);

        if (mDefaultValue != null) {
            input.setText(mDefaultValue);
        }

        if (mDefaultSeconds != null) {
            secondsEdit.setText(mDefaultSeconds.toString());
        }

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                String seconds = secondsEdit.getText().toString();
                //Set value in Prefs and reload them for the list.
                SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
                Set<String> sampleSet = new HashSet<String>();
                Set<String> set = new HashSet<String>(prefs.getStringSet(mDefaultSetName + "_regex_set", sampleSet));
                if (mDefaultValue != "" ) {
                    set.remove(mDefaultValue + "$$||$$" + mDefaultSeconds);
                }
                set.add(value + "$$||$$" + seconds);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putStringSet(mDefaultSetName + "_regex_set", set);
                edit.commit();
                Fragment regexFrag = getTargetFragment();
                if (regexFrag instanceof WakelockRegexFragment) {
                    ((WakelockRegexFragment) (regexFrag)).reload();
                } else if (regexFrag instanceof AlarmRegexFragment) {
                    ((AlarmRegexFragment) (regexFrag)).reload();
                }
                return;
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        return builder.create();

    }

}
