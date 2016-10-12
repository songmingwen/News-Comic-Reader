package com.song.sunset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailActivity;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.holders.ComicListViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class ComicListAdapter extends BaseRecyclerViewAdapter<ComicListBean.ComicsBean, ComicListViewHolder> {
    private Context context;

    public ComicListAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comiclist_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicListBean.ComicsBean comicsBean = getData().get(position);
        ComicListViewHolder comicListViewHolder = (ComicListViewHolder) holder;

        int realWidth = (ViewUtil.getScreenWidth() - ViewUtil.dip2px(56)) / 3;
        int realHeight = realWidth * 143 / 113;

        comicListViewHolder.comicName.setText(comicsBean.getName());
        FrescoUtil.setFrescoCoverImage(comicListViewHolder.simpleDraweeView, comicsBean.getCover(), realWidth, realHeight);
    }

    @Override
    protected void onItemClick(View view, int position) {
        ComicDetailActivity.start(context, getData().get(position).getComicId());
    }
}
