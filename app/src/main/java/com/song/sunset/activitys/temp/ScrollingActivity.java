package com.song.sunset.activitys.temp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import io.reactivex.Observable;

import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.adapters.RankingPagerAdapter;
import com.song.sunset.beans.ComicRankListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.ComicGenericListFragment;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.widget.RankViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.song.sunset.activitys.ComicListActivity.ARG_NAME;
import static com.song.sunset.activitys.ComicListActivity.ARG_VALUE;

public class ScrollingActivity extends BaseActivity {

    private static final String BUNDLE_KEY_PAGE_INDEX = "page_index";
    private SlidingTabLayout rankingSlidingTabLayout;
    private RankingPagerAdapter<ComicGenericListFragment> rankingPagerAdapter;
    private int mCurrPos = 0;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ScrollingActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
        loadNetData();
    }

    private void initView() {

        RankViewPager rankingViewPager = (RankViewPager) findViewById(R.id.ranking_view_pager);
        rankingSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.ranking_sliding_layout);
        rankingPagerAdapter = new RankingPagerAdapter<>(getSupportFragmentManager());
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.setOffscreenPageLimit(2);
        rankingViewPager.setCurrentItem(mCurrPos);
        rankingViewPager.setPageTransformer(false, (page, position) -> {
            final float normalizedPosition = Math.abs(Math.abs(position) - 1);
            float realRotate = normalizedPosition / 10 + 0.9f;
            page.setAlpha(realRotate);
            page.setScaleX(realRotate);
            page.setScaleY(realRotate);
        });
        rankingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        rankingSlidingTabLayout.setViewPager(rankingViewPager);
    }

    private void loadNetData() {
        Observable<BaseBean<ComicRankListBean>> observable = RetrofitFactory.createApi(U17ComicApi.class).queryComicRankListBeanByObservable();
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicRankListBean>() {
            @Override
            public void onSuccess(ComicRankListBean comicRankListBean) {
                List<ComicRankListBean.RankinglistBean> rankingTypeItemList = comicRankListBean.getRankinglist();

                List<ComicGenericListFragment> fragmentList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();

                for (ComicRankListBean.RankinglistBean rankingTypeItem : rankingTypeItemList) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME, rankingTypeItem.getArgName());
                    bundle.putInt(ARG_VALUE, Integer.parseInt(rankingTypeItem.getArgValue()));
                    ComicGenericListFragment fragment = (ComicGenericListFragment) Fragment.instantiate(ScrollingActivity.this, ComicGenericListFragment.class.getName(), bundle);

                    fragmentList.add(fragment);
                    titleList.add(rankingTypeItem.getTitle());
                }

                if (fragmentList.size() <= 6) {
                    int newWidth = ViewUtil.px2dip(ViewUtil.getScreenWidth() / (float) fragmentList.size());
                    rankingSlidingTabLayout.setTabWidth(newWidth);
                }

                rankingPagerAdapter.setFragmentList(fragmentList, titleList);
                rankingSlidingTabLayout.notifyDataSetChanged();
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
