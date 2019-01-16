package com.song.sunset.mvp.models;

import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.song.sunset.beans.PhoenixChannelBean;
import com.song.sunset.holders.TitleImageViewHolder;
import com.song.sunset.mvp.models.base.PhoenixBaseRenderModel;
import com.song.sunset.utils.fresco.FrescoUtil;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class TitleImageRenderModel extends PhoenixBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean) {
        TitleImageViewHolder viewHolder = (TitleImageViewHolder) holder;
        viewHolder.videoImage.setVisibility(TextUtils.equals(phoenixChannelBean.getType(), "phvideo") ? View.VISIBLE : View.GONE);
        FrescoUtil.setFrescoImage(viewHolder.image, phoenixChannelBean.getThumbnail());
        PhoenixRenderModel.setTitleAndBottomData(phoenixChannelBean, viewHolder);
    }
}
