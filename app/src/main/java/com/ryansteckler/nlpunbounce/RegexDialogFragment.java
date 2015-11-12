package com.ryansteckler.nlpunbounce;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rsteckler on 11/10/15.
 */
public class RegexDialogFragment  extends android.app.DialogFragment{

    String mDefaultValue = "";
    public static RegexDialogFragment newInstance(String defaultValue) {
        RegexDialogFragment f = new RegexDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("defaultValue", defaultValue);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        mDefaultValue = "";
        if (savedInstance != null) {
            mDefaultValue = savedInstance.getString("defaultValue");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Custom Regex");
        builder.setMessage("Enter the RegEx to match");

        // Use an EditText view to get user input.
        final EditText input = new EditText(getActivity());
        input.setId(0);
        if (mDefaultValue != null) {
            input.setText(mDefaultValue);
        }
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                //Set value in Prefs and reload them for the list.
                SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce_preferences", Context.MODE_WORLD_READABLE);
                Set<String> sampleSet = new HashSet<String>();
                Set<String> set = new HashSet<String>(prefs.getStringSet("wakelock_regex_set", sampleSet));
                set.add(value);

                SharedPreferences.Editor edit = prefs.edit();
                edit.putStringSet("wakelock_regex_set", set);
                edit.commit();
                ((WakelockRegexFragment) (getTargetFragment())).reload();
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
