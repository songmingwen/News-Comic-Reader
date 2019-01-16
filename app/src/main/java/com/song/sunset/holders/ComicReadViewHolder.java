package com.song.sunset.holders;

import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2016/8/30 0030.
 * Email:z53520@qq.com
 */
public class ComicReadViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView simpleDraweeView;

    public ComicReadViewHolder(final View itemView) {
        super(itemView);
        simpleDraweeView = itemView.findViewById(R.id.id_comic_read_simple_drawee_view);
    }
}
