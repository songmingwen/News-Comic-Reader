package com.song.sunset.mvp.models;

import androidx.recyclerview.widget.RecyclerView;

import com.song.sunset.phoenix.bean.PhoenixChannelBean;
import com.song.sunset.holders.ScompreViewViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class ScompreRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        ScompreViewViewHolder viewHolder = (ScompreViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
        viewHolder.state.setText(PhoenixRenderModel.getLiveState(phoenixChannelBean));
        viewHolder.title.setText(phoenixChannelBean.getTitle());
    }
}
