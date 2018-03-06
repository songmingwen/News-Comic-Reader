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
public class LongImageViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView image;
    public TextView title;

    public LongImageViewHolder(View inflate) {
        super(inflate);
        title = itemView.findViewById(R.id.title);
        image = inflate.findViewById(R.id.phoenix_image);
    }
}
