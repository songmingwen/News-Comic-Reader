package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.LiveImageViewHolder;
import com.song.sunset.model.base.IfengBaseRenderModel;
import com.song.sunset.utils.DateTimeUtils;
import com.song.sunset.utils.fresco.FrescoUtil;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class LiveImageRenderModel extends IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        LiveImageViewHolder viewHolder = (LiveImageViewHolder) holder;
        FrescoUtil.setFrescoImage(viewHolder.image, ifengChannelBean.getThumbnail());
        viewHolder.title.setText(ifengChannelBean.getTitle());
        viewHolder.tag.setText(IfengRenderModel.getLiveState(ifengChannelBean));
        if (ifengChannelBean.getLiveExt() == null) {
            return;
        }
        viewHolder.time.setText(DateTimeUtils.getLiveTime(ifengChannelBean.getLiveExt().getStartTimeMillis()));
    }
}
