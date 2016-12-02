package com.song.sunset.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.song.sunset.R;
import com.song.sunset.activitys.ComicDetailActivity;
import com.song.sunset.beans.ComicLocalCollection;
import com.song.sunset.holders.ComicListViewHolder;
import com.song.sunset.utils.ViewUtil;
import com.song.sunset.utils.fresco.FrescoUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by z5352_000 on 2016/10/29 0029.
 */
public class CollectionComicAdapter extends RecyclerView.Adapter<ComicListViewHolder> {
    private Context context;
    private List<ComicLocalCollection> data;

    public CollectionComicAdapter(Context context) {
        this.context = context;
        if (data == null) {
            data = new ArrayList<>();
        }
    }

    @Override
    public ComicListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ComicListViewHolder(LayoutInflater.from(context).inflate(R.layout.comiclist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ComicListViewHolder holder, final int position) {
        if (data != null) {
            int realWidth = (ViewUtil.getScreenWidth() - ViewUtil.dip2px(56)) / 3;
            int realHeight = realWidth * 143 / 113;
            holder.comicName.setText(data.get(position).getName());
            FrescoUtil.setFrescoCoverImage(holder.simpleDraweeView, data.get(position).getCover(), realWidth, realHeight);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ComicDetailActivity.start(context, (int) data.get(position).getComicId());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (data != null && data.size() > 0) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void setData(List<ComicLocalCollection> data) {
        if (data != null && data.size() > 0) {
            if (this.data != null && this.data.size() > 0) {
                this.data.clear();
            }
            this.data.addAll(data);
            notifyDataSetChanged();
        }
    }
}