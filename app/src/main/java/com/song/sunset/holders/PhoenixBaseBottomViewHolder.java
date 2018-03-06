package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */

public class PhoenixBaseBottomViewHolder extends RecyclerView.ViewHolder {

    public TextView title, txSource, commentCount, picCount, updateTime;
    public SimpleDraweeView imgSource;

    public PhoenixBaseBottomViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        txSource = itemView.findViewById(R.id.tx_source);
        commentCount = itemView.findViewById(R.id.comment_count);
        picCount = itemView.findViewById(R.id.pic_count);
        updateTime = itemView.findViewById(R.id.update_time);
        imgSource = itemView.findViewById(R.id.img_source);
    }
}
