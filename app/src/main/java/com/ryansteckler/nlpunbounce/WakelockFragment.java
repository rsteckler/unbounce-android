package com.ryansteckler.nlpunbounce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.ListFragment;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.ryansteckler.nlpunbounce.com.ryansteckler.nlpunbounce.adapters.WakelocksAdapter;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p />
 * <p />
 * Activities containing this fragment MUST implement the {@link OnWakelockFragmentInteractionListener}
 * interface.
 */
public class WakelockFragment extends ListFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnWakelockFragmentInteractionListener mListener;

    private WakelocksAdapter mAdapter;

    private Animator mCurrentAnimator;

    // TODO: Rename and change types of parameters
    public static WakelockFragment newInstance(int sectionNumber) {
        WakelockFragment fragment = new WakelockFragment();
        Bundle args = new Bundle();
        args.putInt(MaterialSettingsActivity.ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WakelockFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_wakelock_list, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
        mAdapter = new WakelocksAdapter(getActivity(), new ArrayList<WakeLockStatsCombined>(BlockReceiver.mWakeLockStats.values()));
        setListAdapter(mAdapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MaterialSettingsActivity) activity).onSectionAttached(
                getArguments().getInt(MaterialSettingsActivity.ARG_SECTION_NUMBER));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, final View v, int position, long id) {
        super.onListItemClick(l, v, position, id);;
        v.setBackgroundColor(Color.parseColor("#0CBB9B"));
        final Drawable oldDrawable = null;
        //Not great form, but the animation to show the details view takes 400ms.  We'll set our background
        //color back to normal once the animation finishes.  I wish there was a more elegant way to avoid
        //a timer.
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        v.setBackgroundResource(R.drawable.list_background);
                    }
                },
                400);

        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onDetailInteraction(((WakeLockStatsCombined) mAdapter.getItem(position)).getName());
        }

        //Switch to detail view.
        switchToDetail(position);

    }

    private void switchToDetail(int position) {
        ListView list = (ListView)getActivity().findViewById(android.R.id.list);
        View listItem = list.getChildAt(position);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        listItem.getGlobalVisibleRect(startBounds);
        list.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        FragmentManager fragmentManager = getFragmentManager();
        WakelockDetailFragment newFrag = WakelockDetailFragment.newInstance(startBounds.top, finalBounds.top, startBounds.bottom, finalBounds.bottom);
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.expand_in, android.R.animator.fade_out, android.R.animator.fade_in, R.animator.expand_out)
                .add(R.id.container, newFrag, "wakelock_detail")
                .addToBackStack(null)
                .commit();

        WakeLockStatsCombined curStat = (WakeLockStatsCombined)mAdapter.getItem(position);
        getActivity().getActionBar().setTitle(curStat.getName());

    }
    private boolean mSortByTime = true;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.wakelocks, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort) {
            mSortByTime = !mSortByTime;
            getActivity().invalidateOptionsMenu();
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
    public interface OnWakelockFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onDetailInteraction(String id);
    }

}
