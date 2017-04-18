package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.LongImageViewHolder;
import com.song.sunset.model.base.IfengBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class LongImageRenderModel extends IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        LongImageViewHolder viewHolder = (LongImageViewHolder) holder;
        viewHolder.title.setText(ifengChannelBean.getTitle());
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
    }
}
