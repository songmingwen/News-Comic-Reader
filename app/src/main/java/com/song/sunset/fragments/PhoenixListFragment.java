package com.song.sunset.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.song.sunset.R;
import com.song.sunset.adapters.PhoenixListAdapter;
import com.song.sunset.phoenix.bean.PhoenixChannelBean;
import com.song.sunset.phoenix.bean.PhoenixNewsListBean;
import com.song.sunset.base.bean.PageEntity;
import com.song.sunset.fragments.base.RVLoadableFragment;
import com.song.sunset.base.AppConfig;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.base.net.Net;
import com.song.sunset.base.rxjava.RxUtil;
import com.song.sunset.phoenix.api.PhoenixNewsApi;
import com.song.sunset.base.api.WholeApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.Observable;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
@Route(path = "/song/phoenix/list")
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
        Observable<List<PhoenixNewsListBean>> observable = Net
                .createService(PhoenixNewsApi.class, WholeApi.PHOENIX_NEWS_BASE_URL, "time", System.currentTimeMillis() + "")
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
