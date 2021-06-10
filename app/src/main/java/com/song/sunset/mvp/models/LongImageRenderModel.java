package com.song.sunset.mvp.models;

import androidx.recyclerview.widget.RecyclerView;

import com.song.sunset.phoenix.bean.PhoenixChannelBean;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.FrescoUtil;

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
