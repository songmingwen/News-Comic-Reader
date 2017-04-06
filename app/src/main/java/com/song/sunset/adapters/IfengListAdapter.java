package com.song.sunset.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.utils.IfengListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Song on 2017/3/30 0030.
 * E-mail: z53520@qq.com
 */
public class IfengListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IfengChannelBean> mList;
    private Activity context;

    public IfengListAdapter(Activity context) {
        this.context = context;
        if (mList == null) {
            mList = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return IfengListUtils.getViewHolder(context, parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getData() == null || getData().size() <= 0) {
            return;
        }
        IfengListUtils.render(context, getItemViewType(position), holder, getData().get(position));
    }

    @Override
    public int getItemViewType(int position) {
        if (mList == null || mList.size() <= 0) {
            return 0;
        }
        return IfengListUtils.getViewType(mList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mList != null)
            return mList.size();
        else
            return 0;
    }

    public void addDataAtTop(IfengChannelBean item) {
        addData(0, item);
    }

    public void addDataAtBottom(IfengChannelBean item) {
        addData(-1, item);
    }

    public void addData(int position, IfengChannelBean item) {
        if (mList != null && item != null) {
            if (position == -1) {
                mList.add(item);
                notifyItemInserted(mList.size());
            } else {
                mList.add(position, item);
                notifyItemInserted(position);
            }
        }
    }

    public void addTopData(List<IfengChannelBean> data) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (data != null && data.size() > 0) {
            mList.addAll(0, data);
            notifyDataSetChanged();
        }
    }

    public void addBottomData(List<IfengChannelBean> data) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        if (data != null && data.size() > 0) {
            mList.addAll(data);
            notifyDataSetChanged();
        }
    }

    public List<IfengChannelBean> getData() {
        return mList;
    }
}
