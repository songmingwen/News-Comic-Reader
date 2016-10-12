package com.song.sunset.adapters;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.song.sunset.fragments.ComicListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */

public class RankingPagerAdapter extends FragmentPagerAdapter {

    private List<ComicListFragment> fragments = new ArrayList<>();

    private List<String> titleList = new ArrayList<>();

    public RankingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragmentList(List<ComicListFragment> fragmentList, List<String> titleList) {
        this.fragments.clear();
        this.titleList.clear();
        if (fragmentList != null && fragmentList.size() > 0)
            fragments.addAll(fragmentList);
        if (titleList != null && titleList.size() > 0) {
            this.titleList.addAll(titleList);
        }
        notifyDataSetChanged();
    }

    public List<ComicListFragment> getFragments() {
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
    public ComicListFragment getItem(int position) {
        ComicListFragment fragment = null;
        if (fragments != null) {
            fragment = fragments.get(position);
        }
        return fragment;
    }
}