package com.song.sunset.base.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.song.sunset.base.R;
import com.song.sunset.base.bean.PageEntity;
import com.song.sunset.base.holder.DefaultLoadMoreProgressHolder;
import com.song.sunset.base.holder.DefaultRefreshEmptyHolder;
import com.song.sunset.widget.PullRefreshLayout;
import com.zhihu.android.sugaradapter.SugarAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.CallSuper;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
public abstract class BasePagingFragment<T extends PageEntity> extends BaseFragment {
    private static final String TAG = "BasePagingFragment";
    protected PullRefreshLayout mSwipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected SugarAdapter mAdapter;
    protected boolean mIsLoading;

    @NonNull
    private List<Object> mDataList = new ArrayList<>();
    private boolean mIsFirstRefresh = true;
    private PageEntity<T> mPaging;

    private Object mRefreshEmptyItem;
    private Object mRefreshErrorItem;
    private Object mLoadMoreErrorItem;
    private Object mLoadMoreEndItem;
    private Object mLoadMoreProgressItem;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataList = new ArrayList<>();
        mAdapter = addHolders(SugarAdapter.Builder.with(mDataList))
                .add(DefaultRefreshEmptyHolder.class)
//                .add(DefaultLoadMoreEndHolder.class)
                .add(DefaultLoadMoreProgressHolder.class)
//                .add(DefaultLoadMoreErrorHolder.class)
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return providePagingRootView(inflater, container);
    }

    /**
     * 如果要修改修改根布局，必须重新设置 mSwipeRefreshLayout mRecyclerView！
     */
    protected View providePagingRootView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_base_paging, container, false);
        mSwipeRefreshLayout = view.findViewById(R.id.refresh);
        mRecyclerView = view.findViewById(R.id.recycler);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setLayoutManager(mLayoutManager = new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (provideItemDecoration() != null) {
            mRecyclerView.addItemDecoration(provideItemDecoration());
        }
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                BasePagingFragment.this.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                BasePagingFragment.this.onScrolled(recyclerView, dx, dy);
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh(true));

        refresh(false);
    }

    @CallSuper
    protected void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            onBottom();
        }
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            listStateIdle();
        }
    }

    @CallSuper
    protected void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (isHandleScrollEvent() || dy == 0) {
            return;
        }
        if (isScrollingTriggerLoadingMore() && canLoadMore()) {
            loadMore(mPaging);
        }
    }

    /**
     * 只对 LinearLayoutManager 生效，其他 LayoutManager 请重写此方法
     */
    protected boolean isScrollingTriggerLoadingMore() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        int totalItemCount = layoutManager.getItemCount();
        int lastVisibleItemPosition;

        if (LinearLayoutManager.class.isInstance(layoutManager)) {
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            lastVisibleItemPosition = 0;
        }
        return totalItemCount > 0 && (totalItemCount - lastVisibleItemPosition - 1 <= getScrollLoadMoreThreshold());
    }

    /**
     * 滑动时不足 5 条触发加载下一页
     */
    @IntRange(from = 0)
    protected int getScrollLoadMoreThreshold() {
        return 3;
    }

    public void refresh(boolean pFromUser) {
        if (mSwipeRefreshLayout == null) {
            return;
        }
        if (checkFragmentDetached() || mIsLoading) {
            return;
        }
        if (mIsFirstRefresh) {
            mIsFirstRefresh = false;
            beforeFirstRefresh();
        }
        mIsLoading = true;
        setRefreshing(true);
        onRefresh(pFromUser);
    }

    /**
     * 在第一次刷新之前调用，只会调用一次
     */
    protected void beforeFirstRefresh() {

    }

    /**
     * 刷新完成（包含接口 4XX 5XX）。
     * 如果不想判断 if(response.isSuccess())，直接调这个，如果明确知道请求成功直接调用 {@link #postRefreshSucceed}。
     * 对于非接口异常(比如处理数据出错)，请主动调用 {@link #postRefreshFailed(Throwable)}
     *
     * @param response Response
     */
    @UiThread
    protected final void postRefreshCompleted(@Nullable Response<T> response) {
        if (checkFragmentDetached()) return;

        if (response != null && response.isSuccessful()) {
            postRefreshSucceed(response.body());
        } else {
            String errorMsg = null;
            if (response != null) {
                ResponseBody errorBody = response.errorBody();
                errorMsg = errorBody.toString();
            }
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = "ApiError";
            }
            postRefreshFailed(new ApiException(errorMsg));
        }
    }

    /**
     * 刷新成功。内部会处理空的情况
     *
     * @param result Response body
     */
    @UiThread
    protected void postRefreshSucceed(@Nullable T result) {
        if (checkFragmentDetached()) return;

        clearLoadingEmptyAndError();

        boolean isEmpty = result == null
                || result.getData() == null
                || result.getData().isEmpty();
        mPaging = isEmpty ? null : result;

        List newDataList = null;

        if (isEmpty) {
            addItemAfterClearAll(mRefreshEmptyItem = buildRefreshEmptyItem());
        } else {
            if (getHeaderCount() == 0) {
                mDataList.clear();
                newDataList = result.getData();
                mDataList.addAll(newDataList);
                mAdapter.notifyDataSetChanged();
            } else {
                removeDataRangeFromList(getHeaderCount(), mDataList.size() - getHeaderCount());
                insertDataRangeToList(getHeaderCount(), result.getData());
            }
            // 刷新完成要调用 listStateIdle，不然会漏掉依赖 onScroll 的 listStateIdle 逻辑
            if (getView() != null) {
                getView().post(this::listStateIdle);
            }
        }
        // 如果第一页就拉完了，就显示底部「没有更多了」
        if (mPaging != null && !mPaging.isHasMore()) {
            insertDataItemToList(mDataList.size(), mLoadMoreEndItem = buildLoadMoreEndItem());
        } else if (newDataList != null) {
            // 检查是否因为首页数据过少导致一页都没显示全，如果是则自动拉下一页（必须 post 一下，不然 View 还没渲染完没法判断）
            if (getView() != null) {
                getView().post(() -> {
                    if (isScrollingTriggerLoadingMore() && canLoadMore()) {
                        loadMore(mPaging);
                    }
                });
            }
        }
    }

    /**
     * 刷新异常
     *
     * @param throwable Throwable
     */
    @UiThread
    protected void postRefreshFailed(@Nullable Throwable throwable) {
        if (checkFragmentDetached()) return;

        clearLoadingEmptyAndError();

        String errorMsg = getErrorMsg(throwable);

        // 如果之前有数据就 Toast，如果没有数据就显示出错页面
        if (mPaging == null) {
            addItemAfterClearAll(mRefreshErrorItem = buildRefreshErrorItem(errorMsg));
        } else if (!TextUtils.isEmpty(errorMsg)) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }

        if (throwable != null) throwable.printStackTrace();
    }

    /**
     * 明确知道是 API 出错可以调这个
     */
    @UiThread
    protected final void postRefreshFailed(@Nullable ResponseBody errorBody) {
        postRefreshFailed(new ApiException(" api error"));
    }

    public final void loadMore(@NonNull PageEntity<T> paging) {
        // 所有刷新操作全放主线程，避免多线程状态判断不同步
        mRecyclerView.post(() -> {
            if (checkFragmentDetached() || mIsLoading) return;
            mIsLoading = true;

            insertDataItemToList(mDataList.size(), mLoadMoreProgressItem = buildLoadMoreProgressItem());

            onLoadMore(paging);
        });
    }

    /**
     * 会自动处理失败
     */
    @UiThread
    protected final void postLoadMoreCompleted(@Nullable Response<T> response) {
        if (checkFragmentDetached()) return;

        if (response != null && response.isSuccessful()) {
            postLoadMoreSucceed(response.body());
        } else {
            String errorMsg = null;
            if (response != null) {
                ResponseBody errorBody = response.errorBody();
                errorMsg = errorBody.toString();
            }
            if (TextUtils.isEmpty(errorMsg)) {
                errorMsg = "Api Error";
            }
            postLoadMoreFailed(new ApiException(errorMsg));
        }
    }

    @UiThread
    protected void postLoadMoreFailed(@Nullable Throwable throwable) {
        if (checkFragmentDetached()) return;

        clearLoadingEmptyAndError();

        insertDataItemToList(mDataList.size(), mLoadMoreErrorItem = buildLoadMoreErrorItem(throwable));

        if (throwable != null) throwable.printStackTrace();
    }

    @UiThread
    protected final void postLoadMoreFailed(@Nullable ResponseBody errorBody) {
        postLoadMoreFailed(new ApiException("api error" + errorBody.toString()));
    }

    @UiThread
    protected void postLoadMoreSucceed(@Nullable T result) {
        if (checkFragmentDetached()) return;

        clearLoadingEmptyAndError();

        if (result != null && result.getData() != null) {
            mPaging = result;
            insertDataRangeToList(mDataList.size(), result.getData());

            if (!mPaging.isHasMore()) {
                insertDataItemToList(mDataList.size(), mLoadMoreEndItem = buildLoadMoreEndItem());
            }
            if (getView() != null) {
                getView().post(this::listStateIdle);
            }
        }
    }

    private boolean checkFragmentDetached() {
        return getActivity() == null || !isAdded();
    }

    @Nullable
    protected Object buildRefreshEmptyItem() {
        return new DefaultRefreshEmptyHolder.EmptyInfo(R.string.without_content, R.mipmap.ic_launcher,
                getEmptyViewHeight());
    }

    @Nullable
    protected Object buildRefreshErrorItem(String errorMsg) {
        return new DefaultRefreshEmptyHolder.EmptyInfo(errorMsg,
                R.mipmap.ic_launcher, getEmptyViewHeight(), R.string.loading_failure,
                v -> {
                    removeDataItemFromList(mRefreshErrorItem);
                    mRefreshErrorItem = null;
                    refresh(true);
                });
    }

    @Nullable
    protected Object buildLoadMoreProgressItem() {
        return new DefaultLoadMoreProgressHolder.Data();
    }

    @Nullable
    protected Object buildLoadMoreEndItem() {
        return new DefaultLoadMoreProgressHolder.Data();
    }

    @Nullable
    protected Object buildLoadMoreErrorItem(@Nullable Throwable throwable) {
        return new DefaultLoadMoreProgressHolder.Data();
    }

    protected int getEmptyViewHeight() {
        return getRecyclerView().getHeight()
                - getRecyclerView().getPaddingTop()
                - getRecyclerView().getPaddingBottom();
    }

    private void addItemAfterClearAll(@Nullable Object data) {
        if (data == null) return;

        if (getHeaderCount() == 0) {
            // 没有头部的话直接显示占位页面，不要移入动画
            mDataList.clear();
            mDataList.add(data);
            mAdapter.notifyDataSetChanged();
        } else {
            // 保留自定义 Header
            removeDataRangeFromList(getHeaderCount(), mDataList.size() - getHeaderCount());
            insertDataItemToList(getHeaderCount(), data);
        }
    }

    /**
     * 请求完成时需要清除全部异常、过渡状态
     */
    protected void clearLoadingEmptyAndError() {

        setRefreshing(false);

        mIsLoading = false;
        if (mLoadMoreProgressItem != null) {
            removeDataItemFromList(mLoadMoreProgressItem, true);
            mLoadMoreProgressItem = null;
        }
        if (isEmptyShowing()) {
            removeDataItemFromList(mRefreshEmptyItem);
            mRefreshEmptyItem = null;
        }
        if (isErrorShowing()) {
            removeDataItemFromList(mRefreshErrorItem);
            mRefreshErrorItem = null;
        }
        if (isFooterErrorShowing()) {
            removeDataItemFromList(mLoadMoreErrorItem, true);
            mLoadMoreErrorItem = null;
        }
        if (isFooterEndShowing()) {
            removeDataItemFromList(mLoadMoreEndItem, true);
            mLoadMoreEndItem = null;
        }
    }

    protected final void setRefreshing(boolean refreshing) {
        // mRecyclerView layout 的同时做 mSwipeRefreshLayout 的 loading 消失动画会卡顿
        if (mRecyclerView == null || mSwipeRefreshLayout == null) {
            return;
        }
        mRecyclerView.post(() -> mSwipeRefreshLayout.setRefreshing(refreshing));
    }

    protected void onRefresh(boolean fromUser) {
        // 发起数据请求
    }

    protected void onLoadMore(@Nullable PageEntity<T> paging) {
        // 发起数据请求
    }

    /**
     * 如果有不需要参与列表数据拉取逻辑的 Header 需要指定数量，不然会被列表刷新时清掉.
     */
    protected int getHeaderCount() {
        return 0;
    }

    // <editor-fold desc="/*一些列表更新的操作*/">
    protected final void insertDataItemToList(int index, Object data) {
        if (index > mDataList.size() || data == null) {
            return;
        }
        mDataList.add(index, data);
        mAdapter.notifyItemInserted(index);
    }

    protected final void removeDataItemFromList(Object target) {
        removeDataItemFromList(target, false);
    }

    // 移除 footer 从后面遍历稍微提高一点速度
    protected final void removeDataItemFromList(Object target, boolean fromLast) {
        if (fromLast) {

            for (int i = mDataList.size() - 1; i >= 0; i--) {
                Object data = mDataList.get(i);
                if (data == target) {
                    mDataList.remove(i);
                    mAdapter.notifyItemRemoved(i);
                    break;
                }
            }
        } else {
            for (int i = 0; i < mDataList.size(); i++) {
                Object data = mDataList.get(i);
                if (data == target) {
                    mDataList.remove(i);
                    mAdapter.notifyItemRemoved(i);
                    break;
                }
            }
        }
    }

    protected final void insertDataRangeToList(int start, List dataSet) {
        if (start >= 0 && start <= mDataList.size() && dataSet != null && !dataSet.isEmpty()) {
            mDataList.addAll(start, dataSet);
            mAdapter.notifyItemRangeInserted(start, dataSet.size());
        }
    }

    protected final void removeDataRangeFromList(int start, int count) {
        if (start >= 0 && start < mDataList.size() && start + count <= mDataList.size()) {
            mDataList.subList(start, start + count).clear();
            mAdapter.notifyItemRangeRemoved(start, count);
        }
    }
    // </editor-fold>

    /**
     * 触发时机：
     * 1.列表滑动状态为 SCROLL_STATE_IDLE 时；
     * 2.列表数据刷新完成时
     */
    protected void listStateIdle() {

    }

    @Nullable
    protected List getVisibleData() {
        if (getContext() == null && mRecyclerView == null) {
            return null;
        }

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();

            int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
            int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

            if (firstVisibleItem < 0 || lastVisibleItem < 0 || (firstVisibleItem > lastVisibleItem + 1) || (
                    (lastVisibleItem + 1) > mAdapter.getItemCount())) {
                return null;
            }
            return mAdapter.getList().subList(firstVisibleItem, lastVisibleItem + 1);
        }

        return null;
    }

    protected RecyclerView.ItemDecoration provideItemDecoration() {
        return null;
    }

    protected abstract SugarAdapter.Builder addHolders(@NonNull SugarAdapter.Builder builder);

    // 是否自己处理上滑加载更多逻辑
    protected boolean isHandleScrollEvent() {
        return false;
    }

    protected void onBottom() {

    }

    @NonNull
    public List<Object> getDataList() {
        return mDataList;
    }

    private String getErrorMsg(@Nullable Throwable throwable) {
        return (throwable instanceof ApiException
                || throwable != null && throwable.getMessage() != null)
                ? throwable.getMessage()
                : "error message";
    }

    protected boolean canLoadMore() {
        return !mIsLoading && mPaging != null && !isEmptyShowing()
                && !mDataList.isEmpty() && !isFooterErrorShowing() && !isFooterEndShowing();
    }

    public void onTopReturn() {
        if (this.mRecyclerView == null) {
            return;
        }

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            int item = ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (item > 10) {
                mRecyclerView.scrollToPosition(10);
            }
            mRecyclerView.smoothScrollToPosition(0);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public PageEntity getPaging() {
        return mPaging;
    }

    public boolean isLoading() {
        return mIsLoading;
    }

    protected boolean isFooterErrorShowing() {
        return mLoadMoreErrorItem != null;
    }

    protected boolean isEmptyShowing() {
        return mRefreshEmptyItem != null;
    }

    protected boolean isFooterEndShowing() {
        return mLoadMoreEndItem != null;
    }

    protected boolean isErrorShowing() {
        return mRefreshErrorItem != null;
    }

    protected void setPaging(PageEntity paging) {
        this.mPaging = paging;
    }

    protected static class ApiException extends Throwable {
        String errorMsg;

        ApiException(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        @Override
        public String getMessage() {
            return errorMsg;
        }
    }
}
