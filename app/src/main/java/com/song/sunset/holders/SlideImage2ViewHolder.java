package com.song.sunset.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class SlideImage2ViewHolder extends IfengBaseBottomViewHolder {

    public SimpleDraweeView image1, image2, image3;

    public SlideImage2ViewHolder(View inflate) {
        super(inflate);
        image1 = (SimpleDraweeView) inflate.findViewById(R.id.ifeng_image_1);
        image2 = (SimpleDraweeView) inflate.findViewById(R.id.ifeng_image_2);
        image3 = (SimpleDraweeView) inflate.findViewById(R.id.ifeng_image_3);
    }
}
