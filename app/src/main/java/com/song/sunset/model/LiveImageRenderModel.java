package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.LiveImageViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.fresco.FrescoUtil;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class LiveImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        LiveImageViewHolder viewHolder = (LiveImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
        viewHolder.title.setText(phoenixChannelBean.getTitle());
        viewHolder.tag.setText(PhoenixRenderModel.getLiveState(phoenixChannelBean));
        if (phoenixChannelBean.getLiveExt() == null) {
            return;
        }
        viewHolder.time.setText(DateTimeUtils.getLiveTime(phoenixChannelBean.getLiveExt().getStartTimeMillis()));
    }
}
