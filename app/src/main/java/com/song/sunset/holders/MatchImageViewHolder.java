package com.song.sunset.holders;

import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class MatchImageViewHolder extends MatchScoreViewHolder {

    public SimpleDraweeView bg;

    public MatchImageViewHolder(View inflate) {
        super(inflate);
        bg = (SimpleDraweeView) inflate.findViewById(R.id.ifeng_image_bg);
    }
}
