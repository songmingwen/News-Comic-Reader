package com.song.sunset.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ScalePicActivity;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.holders.ComicDetailHeaderViewHolder;
import com.song.sunset.holders.ComicDetailListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    public static final int COMIC_DETAIL_TYPE = 0;
    public static final int COMIC_LIST_TYPE = 1;
    private ComicDetailBean data;
    private int color = Color.WHITE;

    public ComicDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == COMIC_DETAIL_TYPE) {
            return new ComicDetailHeaderViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_detail_header, parent, false));
        } else if (viewType == COMIC_LIST_TYPE) {
            return new ComicDetailListViewHolder(LayoutInflater.from(context).inflate(R.layout.comic_detail_list, parent, false));
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (data != null) {
            if (getItemViewType(position) == COMIC_DETAIL_TYPE) {
                ComicDetailHeaderViewHolder headViewHolder = (ComicDetailHeaderViewHolder) holder;
                FrescoUtil.setFrescoCoverImage(headViewHolder.simpleDraweeView, data.getComic().getCover(), ViewUtil.dip2px(113), ViewUtil.dip2px(143));
                headViewHolder.comicName.setText(data.getComic().getName());
                headViewHolder.authorName.setText(data.getComic().getAuthor().getName());
                headViewHolder.authorName.setTextColor(color);
                headViewHolder.comicDetailLayout.setBackgroundColor(color);

                headViewHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ScalePicActivity.start(context, data.getComic().getOri(), data.getComic().getComic_id());
                    }
                });
            } else if (getItemViewType(position) == COMIC_LIST_TYPE) {
                ComicDetailListViewHolder comicDetailListViewHolder = (ComicDetailListViewHolder) holder;
                ComicDetailListAdapter adapter = new ComicDetailListAdapter(context, data.getComic().getComic_id());
                comicDetailListViewHolder.recyclerView.setAdapter(adapter);
                comicDetailListViewHolder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                adapter.setData(data.getChapter_list());
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return COMIC_DETAIL_TYPE;
        } else if (position == 1) {
            return COMIC_LIST_TYPE;
        } else {
            return -1;
        }
    }

    public void setData(ComicDetailBean data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setColor(int color) {
        this.color = color;
        notifyDataSetChanged();
    }
}
