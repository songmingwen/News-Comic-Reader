package com.song.sunset.widget;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.song.sunset.adapters.base.HeaderViewRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2018/3/5 0005.
 * E-mail: z53520@qq.com
 */

public class RecyclerViewWithHF extends RecyclerView {

    private List<View> mHeaderViews, mFooterViews;

    private boolean mIsHeaderFullScreen = true, mIsFooterScreen = true;

    public RecyclerViewWithHF(Context context) {
        this(context,null);
    }

    public RecyclerViewWithHF(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RecyclerViewWithHF(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setHeaderFullScreen(boolean isFull) {

        if (this.mIsHeaderFullScreen != isFull) {
            this.mIsHeaderFullScreen = isFull;

            if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                ((HeaderViewRecyclerAdapter) getAdapter()).notifyHeaderChanged();
            }
        }
    }

    public void setFooterFullScreen(boolean isFull) {

        if (this.mIsHeaderFullScreen != isFull) {
            this.mIsHeaderFullScreen = isFull;
            if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                ((HeaderViewRecyclerAdapter) getAdapter()).notifyFooterChanged();
            }
        }
    }

    public void addHeaderView(View headerView) {

        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            ((HeaderViewRecyclerAdapter) getAdapter()).addHeaderView(headerView);
        } else {

            initHeaderListIfNecessary();
            mHeaderViews.add(headerView);
        }

    }

    public void addFooterView(View footerView) {

        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            ((HeaderViewRecyclerAdapter) getAdapter()).addFooterView(footerView);
        } else {
            initFooterListIfNecessary();
            mFooterViews.add(footerView);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {

        Adapter decoratingAdapter = null;

        //remove old  inited observer.
        Adapter oldAdapter = getAdapter();
        if (oldAdapter instanceof HeaderViewRecyclerAdapter) {
            ((HeaderViewRecyclerAdapter) oldAdapter).unRegisterInnerObservable();
        }

        //adaptive user adapter into header-footer-adapter if needed.
        if (adapter instanceof HeaderViewRecyclerAdapter) {
            decoratingAdapter = adapter;
        } else if (adapter != null) {

            initHeaderListIfNecessary();
            initFooterListIfNecessary();
            decoratingAdapter = new HeaderViewRecyclerAdapter(mHeaderViews, mFooterViews, adapter);
        }

        super.setAdapter(decoratingAdapter);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {

        initSpanSizeIfNecessary(layout);
        super.setLayoutManager(layout);
    }

    /**
     * 对于GridLayoutManager的展示，默认：header、footer应该是占全屏的.
     */
    private void initSpanSizeIfNecessary(LayoutManager layout) {
        if (layout instanceof GridLayoutManager) {

            GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
            GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
                        HeaderViewRecyclerAdapter adapter = (HeaderViewRecyclerAdapter) getAdapter();
                        if (mIsHeaderFullScreen && adapter.isHeaderPosition(position)) {
                            return ((GridLayoutManager) getLayoutManager()).getSpanCount();
                        } else if (mIsFooterScreen && adapter.isFooterPosition(position)) {
                            return ((GridLayoutManager) getLayoutManager()).getSpanCount();

                        }
                    }
                    return 1;
                }
            };
            gridLayoutManager.setSpanSizeLookup(spanSizeLookup);

        }
    }


    private void initHeaderListIfNecessary() {

        if (mHeaderViews == null) {
            mHeaderViews = new ArrayList<>();
        }
    }

    private void initFooterListIfNecessary() {

        if (mFooterViews == null) {
            mFooterViews = new ArrayList<>();
        }
    }

    public int getHeaderViewsCount() {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).getHeaderViewsCount();
        }
        return 0;
    }

    public int getFooterViewsCount() {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).getFooterViewsCount();
        }
        return 0;
    }

    public boolean removeHeaderView(View headerView) {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).removeHeaderView(headerView);
        }
        return false;
    }

    public boolean removeFooterView(View footerView) {
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            return ((HeaderViewRecyclerAdapter) getAdapter()).removeFooterView(footerView);
        }
        return false;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        /*if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            ((HeaderViewRecyclerAdapter) getAdapter()).registerAdapterDataObserver();
        }*/
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (getAdapter() instanceof HeaderViewRecyclerAdapter) {
            ((HeaderViewRecyclerAdapter) getAdapter()).unRegisterInnerObservable();
        }
    }
}
