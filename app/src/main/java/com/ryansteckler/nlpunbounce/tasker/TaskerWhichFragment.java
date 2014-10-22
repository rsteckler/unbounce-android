package com.ryansteckler.nlpunbounce.tasker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ryansteckler.nlpunbounce.AlarmsFragment;
import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.WakelocksFragment;

/**
 * Created by rsteckler on 9/28/14.
 */
public class TaskerWhichFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TaskerWhichFragment newInstance() {
        TaskerWhichFragment fragment = new TaskerWhichFragment();
        return fragment;
    }

    public TaskerWhichFragment() {
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button button = (Button) view.findViewById(R.id.buttonTaskerWakelock);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WakelocksFragment newFragment = WakelocksFragment.newInstance(true);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(TaskerWhichFragment.this)
                    .add(R.id.container, newFragment)
                    .addToBackStack(null)
                    .commit();

                if (mListener != null)
                    mListener.onTaskerWhichSetTitle(getResources().getString(R.string.tasker_choose_wakelock));
            }
        });

        button = (Button) view.findViewById(R.id.buttonTaskerAlarm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                    .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out)
                    .hide(TaskerWhichFragment.this)
                    .add(R.id.container, AlarmsFragment.newInstance(true))
                    .addToBackStack(null)
                    .commit();

                if (mListener != null)
                    mListener.onTaskerWhichSetTitle(getResources().getString(R.string.tasker_choose_alarm));
            }
        });

        button = (Button) view.findViewById(R.id.buttonTaskerReset);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onTaskerResetSelected();
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tasker_which, container, false);
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden)
        {
            //Set the title again.
            if (mListener != null) {
                mListener.onTaskerWhichSetTitle(getResources().getString(R.string.tasker_welcome));
            }
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
        public void onTaskerResetSelected();
        public void onTaskerWhichSetTitle(String title);
    }


}
