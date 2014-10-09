package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.ryansteckler.nlpunbounce.adapters.AlarmsAdapter;
import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class AlarmsFragment extends ListFragment implements AlarmDetailFragment.FragmentClearListener {

    private OnFragmentInteractionListener mListener;
    private AlarmsAdapter mAdapter;

    private boolean mReloadOnShow = false;
    private boolean mTaskerMode = false;

    private final static String ARG_TASKER_MODE = "taskerMode";

    public static AlarmsFragment newInstance() {
        return newInstance(false);
    }

    public static AlarmsFragment newInstance(boolean taskerMode) {
        AlarmsFragment fragment = new AlarmsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AlarmsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mListener != null)
            mListener.onAlarmsSetTitle(getResources().getString(R.string.title_alarms));

        mAdapter.sort();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }

        mAdapter = new AlarmsAdapter(getActivity(), UnbounceStatsCollection.getInstance().toAlarmArrayList(getActivity()));
        setListAdapter(mAdapter);
    }

    @Override
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
    public void onListItemClick(ListView l, final View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //This suppresses the android system's click handler, which highlights the button, then fades it
        //back to the background, THEN starts our animation.  We just want it to fade to our desired color and stay
        //there until the animation takes over.  Looks sexier that way.
        v.setBackgroundResource(R.drawable.list_item_down); //TODO:  Wrong color for alarms

        //Not great form, but the animation to show the details view takes 400ms.  We'll set our background
        //color back to normal once the animation finishes.  I wish there was a more elegant way to avoid
        //a timer.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        v.setBackgroundResource(R.drawable.list_background);
                    }
                },
                400
        );

        //Switch to detail view.
        switchToDetail(position);
    }

    private void switchToDetail(int position) {
        //We're going for an animation from the list item, expanding to take the entire screen.
        //Start by getting the bounds of the current list item, as a starting point.
        ListView list = (ListView)getActivity().findViewById(android.R.id.list);
        View listItem = list.getChildAt(position - list.getFirstVisiblePosition());
        final Rect startBounds = new Rect();
        listItem.getGlobalVisibleRect(startBounds);

        //Now get the final bounds for the animation:  the same bounds as the parent list.
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();
        list.getGlobalVisibleRect(finalBounds, globalOffset);

        //Offset both bounds because we aren't full-screen.
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        //Spin up the new Detail fragment.  Dig the custom animations.  Also put it on the back stack
        //so we can hit the back button and come back to the list.
        FragmentManager fragmentManager = getFragmentManager();
        AlarmDetailFragment newFrag = AlarmDetailFragment.newInstance(startBounds.top, finalBounds.top, startBounds.bottom, finalBounds.bottom, (AlarmStats)mAdapter.getItem(position), mTaskerMode);
        newFrag.attachClearListener(this);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.expand_in, R.animator.noop, R.animator.noop, R.animator.expand_out)
                .hide(this)
                .add(R.id.container, newFrag, "alarm_detail")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Remember the scroll pos so we can reinstate it
        if (!hidden) {
            if (mListener != null) {
                mListener.onAlarmsSetTitle(getResources().getString(R.string.title_alarms));
                mListener.onAlarmsSetTaskerTitle(getResources().getString(R.string.tasker_choose_alarm));
            }
            if (mReloadOnShow) {
                mReloadOnShow = false;
                //We may have had a change in the data for this wakelock (such as the user resetting the counters).
                //Try updating it.
                mAdapter = new AlarmsAdapter(getActivity(), UnbounceStatsCollection.getInstance().toAlarmArrayList(getActivity()));
                mAdapter.sort();
                setListAdapter(mAdapter);
            }
        }

    }

    @Override
    public void onAlarmCleared() {
        mReloadOnShow = true;
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
        public void onAlarmsSetTitle(String id);
        public void onAlarmsSetTaskerTitle(String title);
    }

}
