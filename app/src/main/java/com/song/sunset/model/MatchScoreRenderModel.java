package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.MatchScoreViewHolder;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

class MatchScoreRenderModel implements IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        MatchScoreViewHolder viewHolder = (MatchScoreViewHolder) holder;
        IfengRenderModel.setBaseLiveWithScore(ifengChannelBean, viewHolder);
    }
}
