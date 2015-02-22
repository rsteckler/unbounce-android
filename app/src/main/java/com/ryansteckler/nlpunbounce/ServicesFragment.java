package com.ryansteckler.nlpunbounce;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.ryansteckler.nlpunbounce.adapters.ServicesAdapter;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.ServiceStats;
import com.ryansteckler.nlpunbounce.models.UnbounceStatsCollection;

/**
 * Created by rsteckler on 10/21/14.
 */
public class ServicesFragment extends ListFragment implements ServiceDetailFragment.FragmentClearListener {

    private final static String ARG_TASKER_MODE = "taskerMode";
    private OnFragmentInteractionListener mListener;
    private ServicesAdapter mAdapter;
    private boolean mReloadOnShow = false;
    private boolean mTaskerMode = false;
    private int mSortBy = SortWakeLocks.SORT_COUNT;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ServicesFragment() {
    }

    public static ServicesFragment newInstance() {
        return newInstance(false);
    }

    public static ServicesFragment newInstance(boolean taskerMode) {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_TASKER_MODE, taskerMode);
        fragment.setArguments(args);
        return fragment;
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
            mListener.onServicesSetTitle(getResources().getString(R.string.title_services));

        mAdapter.sort(mSortBy);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.list, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Filter the list
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //If they pushed the sort toggle, switch the icon from duration<->count
        if (id == R.id.action_sort) {
            if (mSortBy == SortWakeLocks.SORT_COUNT) {
                mSortBy = SortWakeLocks.SORT_ALPHA;
            } else if (mSortBy == SortWakeLocks.SORT_ALPHA) {
                mSortBy = SortWakeLocks.SORT_PACKAGE;
            } else if (mSortBy == SortWakeLocks.SORT_PACKAGE) {
                mSortBy = SortWakeLocks.SORT_COUNT;
            }

            getActivity().invalidateOptionsMenu();

            //Do the re-sort here
            mAdapter.sort(mSortBy);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem sortItem = menu.findItem(R.id.action_sort);
        if (sortItem != null) {
            if (mSortBy == SortWakeLocks.SORT_COUNT) {
                sortItem.setIcon(R.drawable.ic_action_sort_by_size);
                sortItem.setTitle(R.string.action_sort_by_count);
            } else if (mSortBy == SortWakeLocks.SORT_ALPHA) {
                sortItem.setIcon(R.drawable.ic_sort_alpha);
                sortItem.setTitle(R.string.action_sort_by_alpha);
            } else if (mSortBy == SortWakeLocks.SORT_PACKAGE) {
                sortItem.setIcon(R.drawable.ic_sort_pack);
                sortItem.setTitle(R.string.action_sort_by_package);
            }
        }

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ThemeHelper.onActivityCreateSetTheme(this.getActivity());
        if (getArguments() != null) {
            mTaskerMode = getArguments().getBoolean(ARG_TASKER_MODE);
        }

        setHasOptionsMenu(true);

        mAdapter = new ServicesAdapter(getActivity(), UnbounceStatsCollection.getInstance().toServiceArrayList(getActivity()));
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
        TypedValue backgroundValue = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        boolean success = theme.resolveAttribute(R.attr.listItemDownService, backgroundValue, true);
        Drawable backgroundColor = getResources().getDrawable(backgroundValue.resourceId);

        v.setBackground(backgroundColor);

        //Not great form, but the animation to show the details view takes 400ms.  We'll set our background
        //color back to normal once the animation finishes.  I wish there was a more elegant way to avoid
        //a timer.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        v.setBackgroundResource(R.drawable.list_background_service);
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
        ListView list = (ListView) getActivity().findViewById(android.R.id.list);
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
        ServiceDetailFragment newFrag = (ServiceDetailFragment) new ServiceDetailFragment().newInstance(startBounds.top, finalBounds.top, startBounds.bottom, finalBounds.bottom, (ServiceStats) mAdapter.getItem(position), mTaskerMode);
        newFrag.attachClearListener(this);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.expand_in, R.animator.noop, R.animator.noop, R.animator.expand_out)
                .hide(this)
                .add(R.id.container, newFrag, "service_detail")
                .addToBackStack(null)
                .commit();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Remember the scroll pos so we can reinstate it
        if (!hidden) {
            if (mListener != null) {
                mListener.onServicesSetTitle(getResources().getString(R.string.title_services));
                mListener.onSetTaskerTitle(getResources().getString(R.string.tasker_choose_service));
            }
            if (mReloadOnShow) {
                mReloadOnShow = false;
                //We may have had a change in the data for this wakelock (such as the user resetting the counters).
                //Try updating it.
                mAdapter = new ServicesAdapter(getActivity(), UnbounceStatsCollection.getInstance().toServiceArrayList(getActivity()));
                mAdapter.sort(mSortBy);
                setListAdapter(mAdapter);
            }
        }

    }

    @Override
    public void onCleared() {
        mReloadOnShow = true;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onServicesSetTitle(String id);

        public void onSetTaskerTitle(String title);
    }

}
