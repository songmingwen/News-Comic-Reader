package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.MatchScoreViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class MatchScoreRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        MatchScoreViewHolder viewHolder = (MatchScoreViewHolder) holder;
        PhoenixRenderModel.setBaseLiveWithScore(phoenixChannelBean, viewHolder);
    }
}
