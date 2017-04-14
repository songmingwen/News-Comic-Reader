package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.SlideImage2ViewHolder;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

class SlideImage2RenderModel implements IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SlideImage2ViewHolder viewHolder = (SlideImage2ViewHolder) holder;
        IfengRenderModel.setSlideImage(ifengChannelBean, IfengRenderModel.getSimpleDraweeViews(viewHolder.image1, viewHolder.image2, viewHolder.image3));
        IfengRenderModel.setTitleAndBottomData(ifengChannelBean, viewHolder);
    }
}
