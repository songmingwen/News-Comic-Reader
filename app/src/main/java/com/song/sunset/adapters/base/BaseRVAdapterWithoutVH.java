package com.song.sunset.adapters.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Song on 2017/4/18 0018.
 * E-mail: z53520@qq.com
 */

public abstract class BaseRVAdapterWithoutVH<Bean> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Bean> mList;
    private static final int BOTTOM = -1;
    private static final int TOP = 0;

    public BaseRVAdapterWithoutVH() {
        if (mList == null) {
            mList = new ArrayList<>();
        }
    }

    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        if (mList != null) return mList.size();
        else return 0;
    }

    /**
     * 添加beanList数据，此操作会清除之前设置的数据
     *
     * @param beanList 要添加的数据
     */
    public void setData(List<Bean> beanList) {
        if (beanList != null) {
            if (mList == null) {
                mList = new ArrayList<>();
            }
            mList.clear();
            mList.addAll(beanList);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加item数据
     *
     * @param position 添加数据的起始位置；当位置为-1时，数据添加到末尾
     * @param item     要添加的数据
     */
    public void addData(int position, Bean item) {
        if (mList != null && item != null) {
            if (position <= BOTTOM) {
                mList.add(item);
                notifyItemInserted(mList.size());
            } else {
                mList.add(position, item);
                notifyItemInserted(position);
            }
        }
    }

    /**
     * 添加beanList数据
     *
     * @param position 添加数据的起始位置；当位置为-1时，数据添加到末尾
     * @param beanList 要添加的数据
     */
    public void addListData(int position, List<Bean> beanList) {
        if (mList != null && beanList != null) {
            if (position <= BOTTOM) {
                mList.addAll(beanList);
                notifyItemRangeInserted(mList.size() - beanList.size(), beanList.size());
            } else {
                mList.addAll(position, beanList);
                notifyItemRangeInserted(position, beanList.size());
            }
        }
    }

    public void addDataAtTop(Bean item) {
        addData(TOP, item);
    }

    public void addDataAtBottom(Bean item) {
        addData(BOTTOM, item);
    }

    public void addDataAtTop(List<Bean> beanList) {
        addListData(TOP, beanList);
    }

    public void addDataAtBottom(List<Bean> beanList) {
        addListData(BOTTOM, beanList);
    }

    public List<Bean> getData() {
        return mList;
    }
}
