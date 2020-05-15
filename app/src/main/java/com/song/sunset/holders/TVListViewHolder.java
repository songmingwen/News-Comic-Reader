package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.song.sunset.R;

/**
 * Created by Song on 2016/9/1 0001.
 * Email:z53520@qq.com
 */
public class TVListViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public FrameLayout content;

    public TVListViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.id_tv_list_text);
        content = itemView.findViewById(R.id.content_container);
    }
}
