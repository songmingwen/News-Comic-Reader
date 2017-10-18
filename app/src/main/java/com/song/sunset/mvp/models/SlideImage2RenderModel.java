package com.song.sunset.mvp.models;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.SlideImage2ViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class SlideImage2RenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        SlideImage2ViewHolder viewHolder = (SlideImage2ViewHolder) holder;
        PhoenixRenderModel.setSlideImage(phoenixChannelBean, PhoenixRenderModel.getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
