package com.song.sunset.comic.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.song.sunset.comic.R;
import com.song.sunset.comic.adapter.RankingPagerAdapter;
import com.song.sunset.comic.bean.ComicRankListBean;
import com.song.sunset.base.bean.BaseBean;
import com.song.sunset.base.fragment.BaseFragment;
import com.song.sunset.comic.widget.RankViewPager;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.widget.ProgressLayout;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.base.net.RetrofitCallback;
import com.song.sunset.base.net.Net;
import com.song.sunset.comic.api.U17ComicApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.song.sunset.comic.ComicListActivity.ARG_NAME;
import static com.song.sunset.comic.ComicListActivity.ARG_VALUE;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */
@Route(path = "/comic/rank")
public class ComicRankFragment extends BaseFragment {

    private static final String BUNDLE_KEY_PAGE_INDEX = "page_index";
    private SlidingTabLayout rankingSlidingTabLayout;
    private RankingPagerAdapter<ComicGenericListFragment> rankingPagerAdapter;
    private ProgressLayout progressLayout;
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
        progressLayout = view.findViewById(R.id.progress);
        progressLayout.showLoading();

        initView(view);
        loadNetData();
    }

    private void initView(View view) {
        RankViewPager rankingViewPager = view.findViewById(R.id.ranking_view_pager);
        rankingSlidingTabLayout = view.findViewById(R.id.ranking_sliding_layout);

        rankingPagerAdapter = new RankingPagerAdapter<>(getChildFragmentManager());
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
        Observable<BaseBean<ComicRankListBean>> observable = Net.INSTANCE.createService(U17ComicApi.class).queryComicRankListBeanByObservable();
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicRankListBean>() {
            @Override
            public void onSuccess(ComicRankListBean comicRankListBean) {
                progressLayout.showContent();
                List<ComicRankListBean.RankinglistBean> rankingTypeItemList = comicRankListBean.getRankinglist();

                List<ComicGenericListFragment> fragmentList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();

                for (ComicRankListBean.RankinglistBean rankingTypeItem : rankingTypeItemList) {
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_NAME, rankingTypeItem.getArgName());
                    bundle.putInt(ARG_VALUE, Integer.parseInt(rankingTypeItem.getArgValue()));
                    ComicGenericListFragment fragment = (ComicGenericListFragment) Fragment.instantiate(getActivity(), ComicGenericListFragment.class.getName(), bundle);

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
                progressLayout.showRetry(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressLayout.showLoading();
                        loadNetData();
                    }
                });
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
