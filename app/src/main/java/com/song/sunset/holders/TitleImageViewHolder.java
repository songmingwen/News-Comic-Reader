package com.song.sunset.holders;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class TitleImageViewHolder extends IfengBaseBottomViewHolder {

    public SimpleDraweeView image;

    public TitleImageViewHolder(View inflate) {
        super(inflate);
        image = (SimpleDraweeView) inflate.findViewById(R.id.ifeng_image);
    }
}
