package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.song.sunset.R;
import com.song.sunset.utils.ViewUtil;

/**
 * Created by songmw3 on 2016/12/21.
 */
public class VideoListViewHolder extends RecyclerView.ViewHolder {

    public ImageView cover;
    public TextView videoName;

    public VideoListViewHolder(View itemView) {
        super(itemView);
        cover = (ImageView) itemView.findViewById(R.id.video_drawee);
        videoName = (TextView) itemView.findViewById(R.id.video_text);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = ViewUtil.getScreenWidth() * 9 / 16;
        cover.setLayoutParams(params);
    }
}
