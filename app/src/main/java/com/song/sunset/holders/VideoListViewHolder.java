package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/12/21.
 * E-mail:z53520@qq.com
 */
public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView cover;
    public TextView videoName;

    public VideoListViewHolder(View itemView) {
        super(itemView);
        cover = (SimpleDraweeView) itemView.findViewById(R.id.video_drawee);
        videoName = (TextView) itemView.findViewById(R.id.video_text);
    }
}
