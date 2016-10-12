package com.song.sunset.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.TVActivity;
import com.song.sunset.beans.TV;
import com.song.sunset.holders.TVListViewHolder;

import java.util.List;
import java.util.Map;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class TVListAdapter extends BaseRecyclerViewAdapter<TV, TVListViewHolder> {
    private Context context;

    public TVListAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected TVListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new TVListViewHolder(LayoutInflater.from(context).inflate(R.layout.tv_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        TV tv = getData().get(position);
        TVListViewHolder tvListViewHolder = (TVListViewHolder) holder;
        tvListViewHolder.textView.setText(tv.getTvName());
    }

    @Override
    protected void onItemClick(View view, int position) {
        TVActivity.start(context, getData().get(position).getTvUrl(), getData().get(position).getTvName());
    }
}
