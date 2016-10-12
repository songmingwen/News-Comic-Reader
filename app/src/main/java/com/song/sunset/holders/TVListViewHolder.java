package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.song.sunset.R;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class TVListViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;

    public TVListViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.id_tv_list_text);
    }
}
