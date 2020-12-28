package com.song.sunset.activitys.temp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.adapters.VP2PagerAdapter;
import com.song.sunset.beans.ComicRankListBean;
import com.song.sunset.beans.PageItem;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.ComicBaseListFragment;
import com.song.sunset.fragments.ComicGenericListFragment;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.retrofit.Net;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.rxjava.RxUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import io.reactivex.Observable;

import static com.song.sunset.activitys.ComicListActivity.ARG_NAME;
import static com.song.sunset.activitys.ComicListActivity.ARG_VALUE;

public class ScrollingActivity extends BaseActivity {

    private static final String BUNDLE_KEY_PAGE_INDEX = "page_index";
    private VP2PagerAdapter rankingPagerAdapter;
    private int mCurrPos = 0;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ScrollingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadNetData();
    }

    private void initView(List<PageItem> pageItems) {

        ViewPager2 rankingViewPager = findViewById(R.id.ranking_view_pager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        rankingPagerAdapter = new VP2PagerAdapter(this);
        rankingPagerAdapter.setFragmentList(pageItems);
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.setOffscreenPageLimit(1);
        rankingViewPager.setCurrentItem(mCurrPos);
        rankingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mCurrPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        TabLayoutMediator tabLayoutMediator =
                new TabLayoutMediator(tabLayout, rankingViewPager, false,
                        (tab, position) -> tab.setText(rankingPagerAdapter.getPageTitle(position)));
        tabLayoutMediator.attach();
    }

    private void loadNetData() {
        Observable<BaseBean<ComicRankListBean>> observable = Net.createService(U17ComicApi.class).queryComicRankListBeanByObservable();
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicRankListBean>() {
            @Override
            public void onSuccess(ComicRankListBean comicRankListBean) {
                List<ComicRankListBean.RankinglistBean> rankingTypeItemList = comicRankListBean.getRankinglist();

                List<PageItem> pageItems = new ArrayList<>();
                for (ComicRankListBean.RankinglistBean rankingTypeItem : rankingTypeItemList) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME, rankingTypeItem.getArgName());
                    bundle.putInt(ARG_VALUE, Integer.parseInt(rankingTypeItem.getArgValue()));
                    pageItems.add(new PageItem(ComicBaseListFragment.class, rankingTypeItem.getTitle(), bundle));
                }

                initView(pageItems);
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt(BUNDLE_KEY_PAGE_INDEX, mCurrPos);
    }
}
