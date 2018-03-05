package com.song.sunset.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.song.sunset.fragments.ComicGenericListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */

public class RankingPagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private List<T> fragments = new ArrayList<>();

    private List<String> titleList = new ArrayList<>();

    public RankingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<T> fragmentList, List<String> titleList) {
        this.fragments.clear();
        this.titleList.clear();
        if (fragmentList != null && fragmentList.size() > 0)
            fragments.addAll(fragmentList);
        if (titleList != null && titleList.size() > 0) {
            this.titleList.addAll(titleList);
        }
        notifyDataSetChanged();
    }

    public List<T> getFragments() {
        return fragments;
    }

    @Override
    public int getCount() {
        int count = 0;
        if (fragments != null) {
            count = fragments.size();
        }
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public T getItem(int position) {
        T fragment = null;
        if (fragments != null) {
            fragment = fragments.get(position);
        }
        return fragment;
    }

    @Override
    public float getPageWidth(int position) {
        if (position == 0) {
            if (fragments != null &&
                    fragments.get(0) != null &&
                    fragments.get(0) instanceof ComicGenericListFragment) {
                return 1.5f;
            }
        }
        return super.getPageWidth(position);
    }
}