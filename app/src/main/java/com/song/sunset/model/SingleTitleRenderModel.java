package com.song.sunset.model;

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;
import com.song.sunset.holders.SingleTitleViewHolder;
import com.song.sunset.model.base.IfengBaseRenderModel;

/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

public class SingleTitleRenderModel extends IfengBaseRenderModel {

    @Override
    public void render(RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean) {
        SingleTitleViewHolder viewHolder = (SingleTitleViewHolder) holder;
        IfengRenderModel.setTitleAndBottomData(ifengChannelBean, viewHolder);
    }
}
