package com.song.sunset.holders;

import android.view.View;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.song.sunset.R;

/**
 * Created by Song on 2017/3/31 0031.
 * E-mail: z53520@qq.com
 */
public class BigImageViewHolder extends PhoenixBaseBottomViewHolder {

    public SimpleDraweeView image;
    public ImageView videoImage;

    public BigImageViewHolder(View inflate) {
        super(inflate);
        image = (SimpleDraweeView) inflate.findViewById(R.id.phoenix_image);
        videoImage = (ImageView) inflate.findViewById(R.id.img_title_image_video);
    }
}
