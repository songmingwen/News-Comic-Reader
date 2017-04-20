package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.SlideImageViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class SlideImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        SlideImageViewHolder viewHolder = (SlideImageViewHolder) holder;
        PhoenixRenderModel.setSlideImage(phoenixChannelBean, PhoenixRenderModel.getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
