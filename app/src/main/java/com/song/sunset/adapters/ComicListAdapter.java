package com.song.sunset.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailMVPActivity;
import com.song.sunset.adapters.base.BaseRecyclerViewAdapter;
import com.song.sunset.beans.ComicsBean;
import com.song.sunset.holders.ComicListViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by Song on 2016/8/27 0027.
 * Email:z53520@qq.com
 */
public class ComicListAdapter extends BaseRecyclerViewAdapter<ComicsBean, ComicListViewHolder> {
    private Context context;

    public ComicListAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ComicListViewHolder onCreatePersonalViewHolder(ViewGroup parent, int viewType) {
        return new ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_list_full_item, parent, false));
    }

    @Override
    protected void onBindPersonalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ComicsBean comicsBean = getData().get(position);
        ComicListViewHolder comicListViewHolder = (ComicListViewHolder) holder;

        int realWidth = ViewUtil.dip2px(113);
        int realHeight = ViewUtil.dip2px(143);

        comicListViewHolder.comicName.setText(comicsBean.getName());
        comicListViewHolder.comicDesc.setText(comicsBean.getDescription());
        comicListViewHolder.comicAuthor.setText(comicsBean.getAuthor());
        comicListViewHolder.comicTags.setText(getTags(comicsBean));

//        Glide.with(context).load(comicsBean.getCover()).into(comicListViewHolder.cover);
        FrescoUtil.setFrescoCoverImage(comicListViewHolder.simpleDraweeView, comicsBean.getCover(), realWidth, realHeight);
    }

    @NonNull
    private StringBuffer getTags(ComicsBean comicsBean) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String tag : comicsBean.getTags()) {
            stringBuffer.append(tag).append(" | ");
        }
        stringBuffer.delete(stringBuffer.length() - 3, stringBuffer.length() - 1);
        return stringBuffer;
    }

    @Override
    protected void onItemClick(View view, int position) {
        ComicDetailMVPActivity.start(context, getData().get(position).getComicId());
    }
}
