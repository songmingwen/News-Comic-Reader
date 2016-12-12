package com.song.sunset.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.song.sunset.R;
import com.song.sunset.adapters.RankingPagerAdapter;
import com.song.sunset.beans.ComicRankListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.utils.retrofit.ObservableTool;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.ComicApi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static com.song.sunset.activitys.ComicListActivity.ARG_NAME;
import static com.song.sunset.activitys.ComicListActivity.ARG_VALUE;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */
public class ComicRankFragment extends BaseFragment {

    private static final String BUNDLE_KEY_PAGE_INDEX = "page_index";
    private ViewPager rankingViewPager;
    private SlidingTabLayout rankingSlidingTabLayout;
    private RankingPagerAdapter rankingPagerAdapter;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private int mCurrPos = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comic_rank_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCurrPos = savedInstanceState.getInt(BUNDLE_KEY_PAGE_INDEX, 0);
        }
        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                retryView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLoadingAndRetryManager.showLoading();
                        loadNetData();
                    }
                });
            }
        });
        mLoadingAndRetryManager.showLoading();

        initView(view);
        loadNetData();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_comic_rank_list);
//        if (savedInstanceState != null) {
//            mCurrPos = savedInstanceState.getInt(BUNDLE_KEY_PAGE_INDEX, 0);
//        }
//        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
//            @Override
//            public void setRetryEvent(View retryView) {
//                retryView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mLoadingAndRetryManager.showLoading();
//                        loadNetData();
//                    }
//                });
//            }
//        });
//        mLoadingAndRetryManager.showLoading();
//
//        initView();
//        loadNetData();
//}

    private void initView(View view) {
        rankingViewPager = (ViewPager) view.findViewById(R.id.ranking_view_pager);
        rankingSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.ranking_sliding_layout);

        rankingPagerAdapter = new RankingPagerAdapter(getChildFragmentManager());
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.setOffscreenPageLimit(2);
        rankingViewPager.setCurrentItem(mCurrPos);
        rankingViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);
            }
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
        Observable<BaseBean<ComicRankListBean>> observable = RetrofitService.createApi(ComicApi.class).queryComicRankListBeanByGetObservable();
        ObservableTool.subscribe(observable, new RetrofitCallback<ComicRankListBean>() {
            @Override
            public void onSuccess(ComicRankListBean comicRankListBean) {
                mLoadingAndRetryManager.showContent();
                List<ComicRankListBean.RankinglistBean> rankingTypeItemList = comicRankListBean.getRankinglist();

                List<ComicListFragment> fragmentList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();

                for (ComicRankListBean.RankinglistBean rankingTypeItem : rankingTypeItemList) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME, rankingTypeItem.getArgName());
                    bundle.putInt(ARG_VALUE, Integer.parseInt(rankingTypeItem.getArgValue()));
                    ComicListFragment fragment = (ComicListFragment) Fragment.instantiate(getActivity(), ComicListFragment.class.getName(), bundle);

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
                mLoadingAndRetryManager.showRetry();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_PAGE_INDEX, mCurrPos);
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, ComicRankFragment.class));
    }
}
