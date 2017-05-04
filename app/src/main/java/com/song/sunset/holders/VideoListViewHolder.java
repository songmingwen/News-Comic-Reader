package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;
import com.song.sunset.utils.ViewUtil;
import com.song.video.SimplePlayerLayout;

/**
 * Created by songmw3 on 2016/12/21.
 * E-mail:z53520@qq.com
 */
public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView cover;
    public TextView videoName;
    public SimplePlayerLayout video;

    public VideoListViewHolder(View itemView) {
        super(itemView);
        cover = (SimpleDraweeView) itemView.findViewById(R.id.video_drawee);
        videoName = (TextView) itemView.findViewById(R.id.video_text);
        video = (SimplePlayerLayout) itemView.findViewById(R.id.simple_player);
    }
}
