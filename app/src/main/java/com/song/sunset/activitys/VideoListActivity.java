package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.dou361.ijkplayer.widget.PlayerView;
import com.flyco.tablayout.SlidingTabLayout;
import com.song.sunset.R;
import com.song.sunset.adapters.RankingPagerAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.fragments.VideoListFragment;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;
import com.song.sunset.utils.loadingmanager.OnLoadingAndRetryListener;
import com.song.sunset.utils.retrofit.ObservableTool;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitService;
import com.song.sunset.utils.service.VideoApi;

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
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private RankingPagerAdapter rankingPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

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

        viewPager = (ViewPager) findViewById(R.id.ranking_view_pager);
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.ranking_sliding_layout);
        rankingPagerAdapter = new RankingPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(rankingPagerAdapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalizedposition = Math.abs(Math.abs(position) - 1);
                page.setAlpha(normalizedposition);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        slidingTabLayout.setViewPager(viewPager);

        loadNetData();
    }

    private void loadNetData() {
        Observable<List<VideoBean>> observable = RetrofitService.createApi(VideoApi.class, VideoApi.baseUrl).queryFirstVideoRDByGetObservable(1);
        ObservableTool.videoSubscribe(observable, new RetrofitCallback<VideoBean>() {
            @Override
            public void onSuccess(VideoBean videoBean) {
                List<VideoBean.TypesBean> list = videoBean.getTypes();

                List<VideoListFragment> fragmentList = new ArrayList<>();

                List<String> titleList = new ArrayList<>();

                for (VideoBean.TypesBean item : list) {
                    Bundle bundle = new Bundle();
                    bundle.putString("chType", item.getChType());
                    bundle.putString("typeid", item.getId());
                    bundle.putString("name", item.getName());
                    VideoListFragment fragment = (VideoListFragment) Fragment.instantiate(VideoListActivity.this, VideoListFragment.class.getName(), bundle);
                    fragmentList.add(fragment);
                    titleList.add(item.getName());
                }

                if (fragmentList.size() <= 6) {
                    int newWidth = ViewUtil.px2dip(ViewUtil.getScreenWidth() / (float) fragmentList.size());
                    slidingTabLayout.setTabWidth(newWidth);
                }

                rankingPagerAdapter.setFragmentList(fragmentList, titleList);
                slidingTabLayout.notifyDataSetChanged();

                mLoadingAndRetryManager.showContent();
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                mLoadingAndRetryManager.showRetry();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //屏幕旋转隐藏或显示状态栏
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            ScreenUtils.fullscreen(this, true);
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            ScreenUtils.fullscreen(this, false);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
}
