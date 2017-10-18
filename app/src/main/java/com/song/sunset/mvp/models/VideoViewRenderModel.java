package com.song.sunset.mvp.models;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.VideoViewViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class VideoViewRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        VideoViewViewHolder viewHolder = (VideoViewViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
