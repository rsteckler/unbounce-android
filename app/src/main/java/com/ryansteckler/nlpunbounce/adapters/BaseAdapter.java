package com.ryansteckler.nlpunbounce.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.ryansteckler.nlpunbounce.R;
import com.ryansteckler.nlpunbounce.helpers.SortWakeLocks;
import com.ryansteckler.nlpunbounce.helpers.ThemeHelper;
import com.ryansteckler.nlpunbounce.models.AlarmStats;
import com.ryansteckler.nlpunbounce.models.BaseStats;
import com.ryansteckler.nlpunbounce.models.EventLookup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rsteckler on 10/21/14.
 */
public abstract class BaseAdapter extends ArrayAdapter {
    protected long mLowCount = 0;
    protected long mHighCount = 0;
    protected long mScale = 0;
    private String mPrefix;

    private long mCategoryBlockedIndex = 0;
    private long mCategorySafeIndex = 0;
    private long mCategoryUnknownIndex = 0;
    private long mCategoryUnsafeIndex = 0;

    protected final static int ITEM_TYPE = 0;
    protected final static int CATEGORY_TYPE = 1;

    protected ArrayList<BaseStats> mBackingList = null;
    protected ArrayList<BaseStats> originalList;

    //Track whether we're sorting by count or duration.
    protected int mSortBy = SortWakeLocks.SORT_COUNT;

    //protected Map<String, List<BaseStats>> mapPackageIndexMap = new HashMap<String, List<BaseStats>>();

    @Override
    public int getPosition(Object item) {
        return mBackingList.indexOf(item);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public BaseAdapter(Context context, int layoutId, ArrayList<BaseStats> baseStatList, String prefix) {
        super(context, layoutId, baseStatList);
        mPrefix = prefix;
        mBackingList = baseStatList;
        originalList = new ArrayList<>(baseStatList);
        calculateScale(context, baseStatList);
        addCategories(mBackingList);
        //addPackgeBasedCategories(mBackingList);
    }

    protected void addCategories(ArrayList<BaseStats> alarmStatList) {
        mCategoryBlockedIndex = 0;
        mCategorySafeIndex = 1;
        mCategoryUnknownIndex = 2;
        mCategoryUnsafeIndex = 3;

        boolean foundSafe = false;
        boolean foundUnknown = false;
        for (BaseStats curStat : alarmStatList) {

            if (!curStat.getBlockingEnabled()) {
                foundSafe = true;
            }

            if (!foundUnknown && foundSafe && EventLookup.isSafe(curStat.getName()) == EventLookup.UNKNOWN) {
                foundUnknown = true;
            }

            if (foundUnknown && EventLookup.isSafe(curStat.getName()) == EventLookup.UNSAFE) {
                break;
            }

            if (!foundSafe)
                mCategorySafeIndex++;

            if (!foundUnknown)
                mCategoryUnknownIndex++;

            mCategoryUnsafeIndex++;

        }
    }

    private void calculateScale(Context context, ArrayList<BaseStats> baseStatList) {

        SharedPreferences prefs = context.getSharedPreferences("com.ryansteckler.nlpunbounce" + "_preferences", Context.MODE_WORLD_READABLE);

        //Get the max and min values for the red-green spectrum of counts
        Iterator<BaseStats> iter = baseStatList.iterator();
        while (iter.hasNext()) {
            BaseStats curStat = iter.next();
            if (curStat.getAllowedCount() > mHighCount)
                mHighCount = curStat.getAllowedCount();
            if (curStat.getAllowedCount() < mLowCount || mLowCount == 0)
                mLowCount = curStat.getAllowedCount();

            //Set the blocking flag
            String blockName = mPrefix + "_" + curStat.getName() + "_enabled";
            curStat.setBlockingEnabled(prefs.getBoolean(blockName, false));

        }
        mScale = mHighCount - mLowCount;

    }

    @Override
    public int getCount() {
        return mBackingList.size() + 4; //4 categories
    }

    @Override
    public Object getItem(int position) {
        int newPosition = position;
        if (position > mCategoryBlockedIndex)
            newPosition--;
        if (position > mCategorySafeIndex)
            newPosition--;
        if (position > mCategoryUnknownIndex)
            newPosition--;
        if (position > mCategoryUnsafeIndex)
            newPosition--;

        return mBackingList.get(newPosition);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == mCategoryBlockedIndex ||
                position == mCategorySafeIndex ||
                position == mCategoryUnknownIndex ||
                position == mCategoryUnsafeIndex)
            return CATEGORY_TYPE;
        else
            return ITEM_TYPE;
    }

    private static class CategoryViewHolder {
        TextView name;
    }

    protected View getCategoryView(int position, View convertView, ViewGroup parent) {
        //Take care of the category special cases.
        CategoryViewHolder categoryViewHolder = null; // view lookup cache stored in tag

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            categoryViewHolder = new CategoryViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.fragment_wakelocks_listgroup, parent, false);
            categoryViewHolder.name = (TextView) convertView.findViewById(R.id.textviewCategoryName);
            convertView.setTag(categoryViewHolder);
        } else {
            categoryViewHolder = (CategoryViewHolder) convertView.getTag();

        }

        if (position == mCategoryBlockedIndex) {
            categoryViewHolder.name.setText(R.string.category_unbounced);
        } else if (position == mCategorySafeIndex) {
            categoryViewHolder.name.setText(R.string.category_safe);
        } else if (position == mCategoryUnknownIndex) {
            categoryViewHolder.name.setText(R.string.category_unknown);
        } else if (position == mCategoryUnsafeIndex) {
            categoryViewHolder.name.setText(R.string.category_not_safe);
        } else {
            categoryViewHolder.name.setText(R.string.category_error);
        }

        return convertView;
    }

    protected float[] getBackColorFromSpectrum(BaseStats alarm) {
        float correctedStat = alarm.getAllowedCount() - mLowCount;
        //Set the background color along the reg-green spectrum based on the severity of the count.
        if (ThemeHelper.getTheme() == ThemeHelper.THEME_DEFAULT) {
            float point = 120 - ((correctedStat / mScale) * 120); //this gives us a 1-120 hue number.
            return new float[]{point, 1f, 1f};
        } else {
            float point = (((correctedStat / mScale) * 80) / 100) + 0.2f; //this gives us a 40-100 value number.
            return new float[]{1f, 0f, point};
        }
    }

    protected float[] getForeColorFromBack(float[] hsvBack) {
        if (ThemeHelper.getTheme() == ThemeHelper.THEME_DEFAULT) {
            return new float[]{0, 0, 0};
        } else {
            //Set the background color along the reg-green spectrum based on the severity of the count.
            float point = 1;
            if (hsvBack[2] > .6) {
                point = .0f;
            }
            return new float[]{291, 0f, point};
        }
    }

    protected abstract void sort(int sortBy, boolean categorize);

        @Override
    public Filter getFilter() {
        return new Filter() {
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mBackingList = (ArrayList<BaseStats>) results.values;
                sort(mSortBy, true);
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<BaseStats> filteredResults = getFilteredResults(constraint);

                results.values = filteredResults;

                return results;
            }
        };
    }

    private List<BaseStats> getFilteredResults(CharSequence constraint) {
        ArrayList<BaseStats> filteredList = new ArrayList<>();
        for (BaseStats curAlarm : originalList) {
            if (curAlarm.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                filteredList.add(curAlarm);
            }
        }
        return filteredList;
    }

}
