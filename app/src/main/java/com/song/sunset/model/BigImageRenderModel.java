package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.BigImageViewHolder;
import com.song.sunset.model.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class BigImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        BigImageViewHolder viewHolder = (BigImageViewHolder) holder;
        viewHolder.videoImage.setVisibility(TextUtils.equals(phoenixChannelBean.getType(), "phvideo") ? View.VISIBLE : View.GONE);
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
