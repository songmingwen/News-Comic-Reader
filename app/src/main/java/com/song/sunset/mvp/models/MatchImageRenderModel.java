package com.song.sunset.mvp.models;

import androidx.recyclerview.widget.RecyclerView;

import com.song.sunset.phoenix.bean.PhoenixChannelBean;
import com.song.sunset.holders.MatchImageViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.FrescoUtil;


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
