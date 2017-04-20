package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class LongImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        LongImageViewHolder viewHolder = (LongImageViewHolder) holder;
        viewHolder.title.setText(phoenixChannelBean.getTitle());
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
    }
}
