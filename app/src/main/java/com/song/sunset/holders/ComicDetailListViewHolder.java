package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.song.sunset.R;

/**
 * Created by Song on 2016/8/29 0029.
 * Email:z53520@qq.com
 */
public class ComicDetailListViewHolder extends RecyclerView.ViewHolder {

    public RecyclerView recyclerView;

    public ComicDetailListViewHolder(View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.id_comic_detail_header_recycler);
    }
}
