package com.soundsmeow.apps.oaat;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.soundsmeow.apps.oaat.R;
import com.soundsmeow.apps.oaat.ui.buddies.BuddiesFragment;
import com.soundsmeow.apps.oaat.ui.stats.StatsFragment;
import com.soundsmeow.apps.oaat.ui.streak.StreakFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.title_streaks, R.string.title_stats,
            R.string.title_buddies};

    private static final int STREAKS = 0;
    private static final int STATS = 1;
    private static final int BUDDIES = 2;

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        switch(position) {
            case STREAKS:
                return StreakFragment.newInstance();
            case STATS:
                return StatsFragment.newInstance();
            case BUDDIES:
                return BuddiesFragment.newInstance();
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}