package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.ryansteckler.nlpunbounce.adapters.WakelocksAdapter;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;
import com.ryansteckler.nlpunbounce.models.WakelockStats;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class WakelocksFragment extends ListFragment implements WakelockDetailFragment.FragmentClearListener {

    private OnFragmentInteractionListener mListener;
    private WakelocksAdapter mAdapter;

    //Whether we're sorting the wakelocks list by duration or count
    private boolean mSortByTime = false;
    private boolean mReloadOnShow = false;

    private boolean mTaskerMode = false;

    private final static String ARG_TASKER_MODE = "taskerMode";

    public static WakelocksFragment newInstance() {
        return newInstance(false);
    }

    public static WakelocksFragment newInstance(boolean taskerMode) {
        WakelocksFragment fragment = new WakelocksFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelocksFragment() {
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
            mListener.onWakelocksSetTitle(getResources().getString(R.string.title_wakelocks));

        mAdapter.sort(!mSortByTime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());

        if (getArguments() != null) {
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }

        mAdapter = new WakelocksAdapter(getActivity(), UnbounceStatsCollection.getInstance().toWakelockArrayList(getActivity()));
        setListAdapter(mAdapter);

        setHasOptionsMenu(true);
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
        v.setBackgroundResource(R.drawable.list_item_down);

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
        if (listItem == null) {
            //Let this crash to google so I can get better reports.
            throw new IndexOutOfBoundsException("Tried to open item that didn't exist: " + position + ". First vis: " + list.getFirstVisiblePosition());
        }
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
        WakelockDetailFragment newFrag = WakelockDetailFragment.newInstance(startBounds.top, finalBounds.top, startBounds.bottom, finalBounds.bottom, (WakelockStats)mAdapter.getItem(position), mTaskerMode);
        newFrag.attachClearListener(this);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.expand_in, R.animator.noop, R.animator.noop, R.animator.expand_out)
                .hide(this)
                .add(R.id.container, newFrag, "wakelock_detail")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Remember the scroll pos so we can reinstate it
        if (!hidden) {
            if (mListener != null) {
                mListener.onWakelocksSetTitle(getResources().getString(R.string.title_wakelocks));
                mListener.onWakelocksSetTaskerTitle(getResources().getString(R.string.tasker_choose_wakelock));
            }
            if (mReloadOnShow) {
                mReloadOnShow = false;
                //We may have had a change in the data for this wakelock (such as the user resetting the counters).
                //Try updating it.
                mAdapter = new WakelocksAdapter(getActivity(), UnbounceStatsCollection.getInstance().toWakelockArrayList(getActivity()));
                mAdapter.sort(!mSortByTime);
                setListAdapter(mAdapter);
            }
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.wakelocks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //If they pushed the sort toggle, switch the icon from duration<->count
        if (id == R.id.action_sort) {
            mSortByTime = !mSortByTime;
            getActivity().invalidateOptionsMenu();

            //Do the re-sort here
            mAdapter.sort(!mSortByTime);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem sortItem = menu.findItem(R.id.action_sort);
        if (sortItem != null) {
            if (mSortByTime) {
                sortItem.setIcon(R.drawable.ic_action_time);
                sortItem.setTitle(R.string.action_sort_by_time);
            } else {
                sortItem.setIcon(R.drawable.ic_action_sort_by_size);
                sortItem.setTitle(R.string.action_sort_by_count);
            }
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onWakelockCleared() {
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
        public void onWakelocksSetTitle(String id);
        public void onWakelocksSetTaskerTitle(String id);
    }

}
