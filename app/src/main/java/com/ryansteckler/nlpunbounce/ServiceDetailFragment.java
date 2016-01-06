package com.ryansteckler.nlpunbounce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.helpers.UidNameResolver;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by rsteckler on 10/20/14.
 */
public class ServiceDetailFragment extends BaseDetailFragment {

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView description = (TextView) view.findViewById(R.id.textViewDescription);
        String descriptionText = description.getText().toString();

        descriptionText = "Package Name: " + mStat.getDerivedPackageName(getActivity().getApplicationContext()) + "\n" +
                "Full Name: " + mStat.getName() + "\n\n" +
                descriptionText;

        description.setText(descriptionText);

        final ImageButton searchButton = (ImageButton) view.findViewById(R.id.btnSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the name of the item
                String itemName = mStat.getName();
                //Open the browser with that term.
                String query = null;
                try {
                    query = URLEncoder.encode(itemName, "utf-8");
                    String url = "http://www.google.com/search?q=" + query;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    startActivity(intent);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });


        SharedPreferences prefs = getActivity().getSharedPreferences(AlarmDetailFragment.class.getPackage().getName() + "_preferences", Context.MODE_WORLD_READABLE);

        final Switch onOff = (Switch) view.findViewById(R.id.switchStat);
        String blockName = "service_" + mStat.getName() + "_enabled";
        boolean enabled = prefs.getBoolean(blockName, false);
        onOff.setChecked(enabled);

    }

    @Override
    protected void warnUnknown(final Switch onOff) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.alert_unknown_service_title)
                .setMessage(R.string.alert_unknown_service_content)
                .setPositiveButton(R.string.dialog_unbounce, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        onOff.setChecked(true);
                        updateEnabled(true);
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //don't set the switch
                        onOff.setChecked(false);
                        updateEnabled(false);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    protected void updateEnabled(boolean b) {
        String blockName = "service_" + mStat.getName() + "_enabled";
        if (!mTaskerMode) {
            SharedPreferences prefs = getActivity().getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(blockName, b);
            editor.apply();
        }

        if (mClearListener != null) {
            mClearListener.onCleared();
        }
    }

    @Override
    protected void loadStatsFromSource(View view) {
        UnbounceStatsCollection coll = UnbounceStatsCollection.getInstance();
        mStat = coll.getServiceStats(getActivity(), mStat.getName());

        TextView textView = (TextView) view.findViewById(R.id.textLocalBlocked);
        textView.setText(String.valueOf(mStat.getBlockCount()));
        textView = (TextView) view.findViewById(R.id.textLocalAcquired);
        textView.setText(String.valueOf(mStat.getAllowedCount()));
    }

    @Override
    public ServiceDetailFragment newInstance() {
        return new ServiceDetailFragment();
    }

    public ServiceDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
