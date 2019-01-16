package com.song.sunset.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.song.sunset.R;
import com.song.sunset.activitys.VideoListActivity;
import com.song.sunset.adapters.VideoListAdapter;
import com.song.sunset.beans.VideoDetailBean;
import com.song.sunset.beans.VideoListsBean;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.interfaces.LoadingMoreListener;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.api.PhoenixNewsApi;
import com.song.sunset.utils.api.WholeApi;
import com.song.sunset.widget.VideoAutoPlayRecyclerView;
import com.song.video.NormalVideoPlayer;
import com.song.video.VideoManager;
import com.song.video.NormalVideoController;

import java.util.List;

import io.reactivex.Observable;

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

    private View.OnClickListener errorClickListener = v -> getDataFromRetrofit2(1);

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
        progressLayout = view.findViewById(R.id.progress__);
        progressLayout.showLoading();

        progressBar = view.findViewById(R.id.id_loading_more_progress);
        showProgress(false);

        recyclerView = view.findViewById(R.id.rv_video_list);

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
        VideoManager.instance().resumeNormalVideoPlayer();
        super.onResume();
    }

    @Override
    public void onPause() {
        VideoManager.instance().suspendNormalVideoPlayer();
        super.onPause();
    }

    public void getDataFromRetrofit2(int page) {
        Observable<List<VideoListsBean>> observable = RetrofitFactory.createApi(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL).queryVideoObservable(page, "list", typeId);
        RxUtil.phoenixNewsSubscribe(observable, new RetrofitCallback<VideoListsBean>() {
            @Override
            public void onSuccess(VideoListsBean videoBean) {
                progressLayout.showContent();
                List<VideoDetailBean> videoBeanList = videoBean.getItem();
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
                        mAdapter.addDataAtBottom(videoBeanList);
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
        VideoManager.instance().releaseNormalVideoPlayer();
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
    public void playVideo(NormalVideoPlayer player, int position) {
        if (player == null || !shouldPlay) return;
        VideoDetailBean mItemBean = mAdapter.getData().get(position);

        NormalVideoController controller = new NormalVideoController(getContext());
        player.setController(controller);

        controller.setTitle(mItemBean.getTitle());
        controller.setLenght(mItemBean.getDuration() * 1000);
        Glide.with(getContext())
                .load(mItemBean.getImage())
                .apply(RequestOptions.placeholderOf(R.mipmap.logo))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(controller.imageView());
        player.setUp(mItemBean.getVideo_url(), null);
        player.start();
    }

    @Override
    public void stopVideo() {
        VideoManager.instance().releaseNormalVideoPlayer();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (recyclerView == null) return;
        recyclerView.setClickViewToCenter(position);
    }

    public void restartVideo() {
        if (VideoManager.instance().getCurrentNormalVideoPlayer() != null) {
            VideoManager.instance().resumeNormalVideoPlayer();
        } else {
            recyclerView.resetCurrentPlayerPosition();
            recyclerView.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE);
        }
    }

    public void pauseVideo() {
        VideoManager.instance().suspendNormalVideoPlayer();
    }
}
