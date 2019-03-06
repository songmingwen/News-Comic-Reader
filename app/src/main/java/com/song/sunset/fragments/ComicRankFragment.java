package com.song.sunset.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.tabs.TabLayout;
import com.song.sunset.R;
import com.song.sunset.adapters.RankingPagerAdapter;
import com.song.sunset.beans.ComicRankListBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.widget.RankViewPager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static com.song.sunset.activitys.ComicListActivity.ARG_NAME;
import static com.song.sunset.activitys.ComicListActivity.ARG_VALUE;

/**
 * Created by Song on 2016/9/28 0028.
 * Email:z53520@qq.com
 */
@Route(path = "/song/comic/rank")
public class ComicRankFragment extends BaseFragment {

    private static final String BUNDLE_KEY_PAGE_INDEX = "page_index";
    private SlidingTabLayout rankingSlidingTabLayout;
    private RankingPagerAdapter<ComicGenericListFragment> rankingPagerAdapter;
    private ProgressLayout progressLayout;
    private int mCurrPos = 0;
    private TabLayout mTabLayout;

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

        mTabLayout = view.findViewById(R.id.tl_layout);

        rankingPagerAdapter = new RankingPagerAdapter<>(getChildFragmentManager());
        rankingViewPager.setAdapter(rankingPagerAdapter);
        rankingViewPager.setOffscreenPageLimit(2);
        rankingViewPager.setCurrentItem(mCurrPos);
        rankingViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                float realRotate = normalizedposition / 10 + 0.9f;
                page.setAlpha(realRotate);
                page.setScaleX(realRotate);
                page.setScaleY(realRotate);
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

        mTabLayout.setupWithViewPager(rankingViewPager);

        setTabWidth(mTabLayout, 0);
    }

    private void loadNetData() {
        Observable<BaseBean<ComicRankListBean>> observable = RetrofitFactory.createApi(U17ComicApi.class).queryComicRankListBeanByObservable();
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

    public static void setTabWidth(final TabLayout tabLayout, final int padding) {
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);


                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
