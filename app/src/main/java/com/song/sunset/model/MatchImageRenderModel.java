package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.MatchImageViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class MatchImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        MatchImageViewHolder viewHolder = (MatchImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.bg, phoenixChannelBean.getThumbnail());
        PhoenixRenderModel.setBaseLiveWithScore(phoenixChannelBean, viewHolder);
    }
}
