package com.song.sunset.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.song.sunset.R;
import com.song.sunset.adapters.PhoenixListAdapter;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.beans.PhoenixNewsListBean;
import com.song.sunset.beans.basebeans.PageEntity;
import com.song.sunset.fragments.base.BaseFragment;
import com.song.sunset.fragments.base.RVLoadableFragment;
import com.song.sunset.interfaces.LoadingMoreListener;
import com.song.sunset.utils.AppConfig;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.RetrofitFactory;
import com.song.sunset.utils.rxjava.RxUtil;
import com.song.sunset.utils.api.PhoenixNewsApi;
import com.song.sunset.utils.api.WholeApi;
import com.song.sunset.widget.LoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import rx.Observable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */

public class PhoenixListFragment extends RVLoadableFragment<PhoenixListAdapter, PhoenixNewsListBean> {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadFirstPage() {
        getDataFromRetrofit2(PhoenixNewsApi.DOWN);
    }

    @Override
    protected void refreshMore() {
        getDataFromRetrofit2(PhoenixNewsApi.DOWN);
    }

    @Override
    protected void loadMore(int pageNum) {
        getDataFromRetrofit2(PhoenixNewsApi.UP);
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_phoenix_list;
    }

    @Override
    protected ProgressLayout getWrapper(View rootView) {
        return rootView.findViewById(R.id.progress);
    }

    @Override
    protected PtrFrameLayout getPtrLayout(View rootView) {
        return rootView.findViewById(R.id.id_phoenix_list_swipe_refresh);
    }

    @Override
    protected PhoenixListAdapter getAdapter() {
        return new PhoenixListAdapter(getActivity());
    }

    @Override
    public RecyclerView getRecyclerView(View rootView) {
        return rootView.findViewById(R.id.id_recyclerview_phoenix_list);
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 1) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return ViewUtil.getScreenHeigth() / 3;
            }
        };
    }

    @Override
    public <BeanData extends PageEntity> void addData(boolean atTop, BeanData bean) {
        List<PhoenixChannelBean> removeRepeatedList = getRemoveRepeatList(bean.getData());
        if (atTop) {
            mInnerAdapter.addDataAtTop(removeRepeatedList);
            redirectPosition(ViewUtil.dip2px(7) - mRecyclerView.getHeight());
        } else {
            mInnerAdapter.addDataAtBottom(removeRepeatedList);
            if (!mPresenter.isFirstLoading()) {
                redirectPosition(mRecyclerView.getHeight() - ViewUtil.dip2px(7) - ViewUtil.dip2px(60));
            }
        }
    }

    private void getDataFromRetrofit2(String action) {
        long start = System.currentTimeMillis();
        Observable<List<PhoenixNewsListBean>> observable = RetrofitFactory
                .createApi(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL, "time", System.currentTimeMillis() + "")
                .queryPhoenixListObservable(action);
        long end = System.currentTimeMillis();
        Log.i("time = ", (end - start) + "millis");
        RxUtil.phoenixNewsSubscribe(observable, this);
    }

    private void redirectPosition(final int dy) {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollBy(0, dy, new DecelerateInterpolator());
            }
        }, AppConfig.REFRESH_CLOSE_TIME);
    }

    @NonNull
    private List<PhoenixChannelBean> getRemoveRepeatList(List<PhoenixChannelBean> phoenixChannelBeanList) {
        List<PhoenixChannelBean> total = mInnerAdapter.getData();

        HashSet<String> set = new HashSet<>();
        for (PhoenixChannelBean item : total) {
            set.add(item.getDocumentId());
        }

        List<PhoenixChannelBean> removeRepeatedList = new ArrayList<>();
        for (PhoenixChannelBean item : phoenixChannelBeanList) {
            if (set.add(item.getDocumentId())) {
                removeRepeatedList.add(item);
            }
        }
        return removeRepeatedList;
    }
}
