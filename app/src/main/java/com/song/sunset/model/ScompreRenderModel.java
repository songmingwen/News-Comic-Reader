package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.ScompreViewViewHolder;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

class ScompreRenderModel implements IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        ScompreViewViewHolder viewHolder = (ScompreViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        viewHolder.state.setText(IfengRenderModel.getLiveState(ifengChannelBean));
        viewHolder.title.setText(ifengChannelBean.getTitle());
    }
}
