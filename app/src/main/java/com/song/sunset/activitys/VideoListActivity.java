package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.song.sunset.R;
import com.song.sunset.adapters.RankingPagerAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.fragments.VideoListHelperFragment;
import com.song.sunset.fragments.VideoListPlayFragment;
import com.song.sunset.receivers.SunsetWidget;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.api.PhoenixNewsApi;
import com.song.sunset.utils.api.WholeApi;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class VideoListActivity extends AppCompatActivity {
    public static final String TV_URL = "tv_url";
    public static final String TV_NAME = "tv_name";
    public static final String CH_TYPE = "chType";
    public static final String TYPE_ID = "typeId";
    public static final String NAME = "name";
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private ProgressLayout progressLayout;
    private RankingPagerAdapter<Fragment> rankingPagerAdapter;
    private boolean fromWidget = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            fromWidget = getIntent().getBooleanExtra(SunsetWidget.FROM, false);
        }
        setContentView(R.layout.activity_video);

        progressLayout = (ProgressLayout)findViewById(R.id.progress);
        progressLayout.showLoading();

        viewPager = (ViewPager) findViewById(R.id.ranking_view_pager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.ranking_sliding_layout);
        rankingPagerAdapter = new RankingPagerAdapter<>(getSupportFragmentManager());
        viewPager.setAdapter(rankingPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (rankingPagerAdapter == null) return;

                Fragment fragment = rankingPagerAdapter.getItem(0);

                if (fragment instanceof VideoListPlayFragment) {
                    VideoListPlayFragment videoListPlayFragment = (VideoListPlayFragment) fragment;
                    if (position == 0) {
                        videoListPlayFragment.restartVideo();
                    } else {
                        videoListPlayFragment.pauseVideo();
                    }
                }
            }
        });
        slidingTabLayout.setViewPager(viewPager);

        loadNetData();
    }

    private void loadNetData() {
        Observable<List<VideoBean>> observable = RetrofitService.createApi(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryFirstVideoObservable(1);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoBean>() {
            @Override
            public void onSuccess(VideoBean videoBean) {
                List<VideoBean.TypesBean> list = videoBean.getTypes();

                List<Fragment> fragmentList = new ArrayList<>();

                List<String> titleList = new ArrayList<>();

                for (VideoBean.TypesBean item : list) {
                    Bundle bundle = new Bundle();
                    bundle.putString(CH_TYPE, item.getChType());
                    bundle.putString(TYPE_ID, item.getId());
                    bundle.putString(NAME, item.getName());
                    if (TextUtils.equals(item.getName(), "凤凰卫视") || TextUtils.equals(item.getName(), "音频") || TextUtils.equals(item.getName(), "直播")) {
                        continue;
                    }
                    if (TextUtils.equals(item.getName(), "美食")) {
                        VideoListPlayFragment fragment = (VideoListPlayFragment) Fragment.instantiate(VideoListActivity.this, VideoListPlayFragment.class.getName(), bundle);
                        fragmentList.add(0, fragment);
                        titleList.add(0, item.getName());
                    } else {
                        VideoListHelperFragment fragment = (VideoListHelperFragment) Fragment.instantiate(VideoListActivity.this, VideoListHelperFragment.class.getName(), bundle);
                        fragmentList.add(fragment);
                        titleList.add(item.getName());
                    }
                }

                if (fragmentList.size() <= 6) {
                    int newWidth = ViewUtil.px2dip(ViewUtil.getScreenWidth() / (float) fragmentList.size());
                    slidingTabLayout.setTabWidth(newWidth);
                }

                rankingPagerAdapter.setFragmentList(fragmentList, titleList);
                slidingTabLayout.notifyDataSetChanged();

                progressLayout.showContent();
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

    public static void start(Context context, String tvUrl, String tvName) {
        Intent intent = new Intent(context, VideoListActivity.class);
        intent.putExtra(TV_NAME, tvName);
        intent.putExtra(TV_URL, tvUrl);
        context.startActivity(intent);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, VideoListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (fromWidget) {
            this.startActivity(new Intent(this, MainActivity.class));
        }
        super.onBackPressed();
    }
}
