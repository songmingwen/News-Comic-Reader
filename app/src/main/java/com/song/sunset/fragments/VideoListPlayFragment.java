package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.activitys.VideoListActivity;
import com.song.sunset.adapters.VideoListAdapter;
import com.song.sunset.beans.VideoBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.interfaces.LoadingMoreListener;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.api.PhoenixNewsApi;
import com.song.sunset.utils.api.WholeApi;
import com.song.sunset.widget.VideoAutoPlayRecyclerView;
import com.song.video.SimplePlayerLayout;

import java.util.List;

import rx.Observable;

/**
 * Created by Song on 2017/4/27 0027.
 * E-mail: z53520@qq.com
 */

public class VideoListPlayFragment extends BaseFragment implements LoadingMoreListener,
        VideoListAdapter.OnItemClickListener, VideoAutoPlayRecyclerView.VideoListPlayListener {

    private String typeId = "";
    private VideoAutoPlayRecyclerView recyclerView;
    private VideoListAdapter mAdapter;
    private int currentPage = 1;
    private boolean isLoading, isRefreshing = false, first, shouldPlay;
    private ProgressLayout progressLayout;
    private RelativeLayout progressBar;

    private SimplePlayerLayout mPlayer;

    private View.OnClickListener errorClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getDataFromRetrofit2(1);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first = true;
        shouldPlay = true;
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            typeId = bundle.getString(VideoListActivity.TYPE_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_play_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressLayout = (ProgressLayout) view.findViewById(R.id.progress__);
        progressLayout.showLoading();

        progressBar = (RelativeLayout) view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        recyclerView = (VideoAutoPlayRecyclerView) view.findViewById(R.id.rv_video_list);

        mAdapter = new VideoListAdapter(getActivity());
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLoadingMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVideoListener(this);
        getDataFromRetrofit2(currentPage);
    }


    @Override
    public void onResume() {
        if (mPlayer != null && getUserVisibleHint() && shouldPlay) {
            mPlayer.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (mPlayer != null) {
            mPlayer.onPause();
        }
        super.onPause();
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoBean>> observable = RetrofitFactory.createApi(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryVideoObservable(page, "list", typeId);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoBean>() {
            @Override
            public void onSuccess(VideoBean videoBean) {
                progressLayout.showContent();
                List<VideoBean.ItemBean> videoBeanList = videoBean.getItem();
                if (isRefreshing) {
                    currentPage = 1;
                    isRefreshing = false;
                    if (mAdapter != null) {
                        mAdapter.setData(videoBeanList);
                    }
                } else {
                    if (isLoading) {
                        isLoading = false;
                    }
                    if (mAdapter != null) {
                        mAdapter.addDatas(videoBeanList);
                        showProgress(false);
                    }
                }
                if (first) {
                    first = false;
                    recyclerView.smoothScrollBy(0, 5);
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (isRefreshing) {
                    isRefreshing = false;
                } else {
                    currentPage--;
                    if (isLoading) {
                        isLoading = false;
                        showProgress(false);
                    } else {
                        progressLayout.showError(getResources().getDrawable(R.drawable.icon_new_style_failure), "连接失败",
                                "无法建立连接",
                                "点击重试", errorClickListener, null);
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        currentPage = 1;
        isLoading = false;
        isRefreshing = false;
    }

    @Override
    public void onDestroy() {
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onLoadingMore() {
        if (isLoading) {
            return;
        }
        showProgress(true);
        currentPage++;
        isLoading = true;
        getDataFromRetrofit2(currentPage);
    }

    public void showProgress(boolean flag) {
        progressBar.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    @Override
    public void playVideo(SimplePlayerLayout player, int position) {
        mPlayer = player;
        if (mPlayer == null || !shouldPlay) return;
        VideoBean.ItemBean mItemBean = mAdapter.getData().get(position);
        mPlayer.setCover(mItemBean.getImage());
        mPlayer.setTitle(mItemBean.getTitle());
        mPlayer.play(mItemBean.getVideo_url());
    }

    @Override
    public void stopVideo() {
        if (mPlayer != null) {
            mPlayer.onDestroy();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (recyclerView == null) return;
        recyclerView.setClickViewToCenter(position);
    }

    public void restartVideo() {
        if (mPlayer == null) return;
        shouldPlay = true;
        mPlayer.start();
    }

    public void pauseVideo() {
        if (mPlayer == null) return;
        if (mPlayer.getPlayState() == SimplePlayerLayout.STATUS_PAUSE) return;
        shouldPlay = false;
        mPlayer.pause();
    }
}
