/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.song.sunset.adapters.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 带head、foot的RecyclerView
 */
public class HeaderViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;

    private static final int TYPE_FOOTER_VIEW = Integer.MIN_VALUE + 1;

    private static final int TYPE_NORMAL_VIEW = Integer.MAX_VALUE / 2;

    private List<View> mHeaderViews;

    private List<View> mFooterViews;

    private RecyclerView.Adapter<RecyclerView.ViewHolder> mRealAdapter;

    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    /**
     * warning : constructor method if changed to be public, make true list not empty, very very important.
     */
    public HeaderViewRecyclerAdapter(@NonNull List<View> headerViews, @NonNull List<View> footerViews,
                                        @NonNull RecyclerView.Adapter<RecyclerView.ViewHolder> realAdapter) {

        this.mHeaderViews = headerViews;

        this.mFooterViews = footerViews;

        this.mRealAdapter = realAdapter;

        _init();
    }

    private void _init() {
        mRealAdapter.registerAdapterDataObserver(dataObserver);
        //registerAdapterDataObserver(dataObserver);
    }

    public void unRegisterInnerObservable() {
        //unregisterAdapterDataObserver(dataObserver);
        mRealAdapter.unregisterAdapterDataObserver(dataObserver);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        int headerViewsCountCount = getHeaderViewsCount();
        if (viewType < TYPE_HEADER_VIEW + headerViewsCountCount) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));
        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < TYPE_NORMAL_VIEW) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mRealAdapter.onCreateViewHolder(parent, viewType - TYPE_NORMAL_VIEW);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();

        if (position >= headerViewsCountCount && position < headerViewsCountCount + mRealAdapter.getItemCount()) {
            mRealAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        } else {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) layoutParams).setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + getFooterViewsCount() + mRealAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {

        int realCount = mRealAdapter.getItemCount();

        int headerViewsCountCount = getHeaderViewsCount();

        if (position < headerViewsCountCount) {

            return TYPE_HEADER_VIEW + position;
        } else if (headerViewsCountCount <= position && position < headerViewsCountCount + realCount) {

            int realItemViewType = mRealAdapter.getItemViewType(position - headerViewsCountCount);
            if (realItemViewType >= TYPE_NORMAL_VIEW) {
                throw new IllegalArgumentException("what is an strange list type!");
            }
            return realItemViewType + TYPE_NORMAL_VIEW;
        } else {
            return TYPE_FOOTER_VIEW + position - headerViewsCountCount - realCount;
        }
    }

    public boolean isHeaderPosition(int position) {
        return position >= 0 && position < getHeaderViewsCount();
    }

    public boolean isFooterPosition(int position) {

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        return position >= start && start < getItemCount();
    }

    public int getHeaderViewsCount() {
        return mHeaderViews != null ? mHeaderViews.size() : 0;
    }

    public int getFooterViewsCount() {
        return mFooterViews != null ? mFooterViews.size() : 0;
    }

    public void addHeaderView(View header) {

        if (header != null) {
            mHeaderViews.add(header);

            notifyHeaderInserted();
            //this.notifyDataSetChanged();
        }
    }

    public void addFooterView(View footer) {

        if (footer != null) {
            mFooterViews.add(footer);

            notifyFooterInserted();
            //this.notifyDataSetChanged();
        }
    }

    public boolean removeHeaderView(View view) {
        boolean removed = mHeaderViews.remove(view);
        if (removed) {
            notifyHeaderRemoved();
        }
        return removed;
    }

    public boolean removeFooterView(View view) {
        boolean removed = mFooterViews.remove(view);
        if (removed) {
            notifyFooterRemoved();
        }
        return removed;
    }


    public void notifyHeaderChanged() {
        //this.notifyDataSetChanged();

        this.notifyItemRangeInserted(0, getHeaderViewsCount());
    }

    public void notifyFooterChanged() {
//        this.notifyDataSetChanged();

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeInserted(start, getItemCount());
    }

    private void notifyHeaderInserted() {
        //this.notifyDataSetChanged();

        this.notifyItemRangeInserted(0, getHeaderViewsCount());
    }

    private void notifyFooterInserted() {
//        this.notifyDataSetChanged();

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeInserted(start, getItemCount());
    }

    private void notifyHeaderRemoved() {
        //this.notifyDataSetChanged();
        this.notifyItemRangeRemoved(0, getHeaderViewsCount());
    }

    private void notifyFooterRemoved() {
        //this.notifyDataSetChanged();

        int start = getHeaderViewsCount() + mRealAdapter.getItemCount();
        this.notifyItemRangeRemoved(start, getItemCount());
    }


    private static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
