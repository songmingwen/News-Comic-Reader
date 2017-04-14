package com.song.sunset.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.song.sunset.beans.IfengChannelBean;

/**
 * Created by Song on 2017/4/7 0007.
 * E-mail: z53520@qq.com
 */

public class IfengDefaultAdapterModel extends IfengBaseAdapterModel {

    @Override
    public int getViewType(IfengChannelBean ifengChannelBean){
        return super.getViewType(ifengChannelBean);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int viewType){
        return super.getViewHolder(context,parent,viewType);
    }

    @Override
    public void render(Context context, int itemViewType, RecyclerView.ViewHolder holder, IfengChannelBean ifengChannelBean){
        super.render(context,itemViewType,holder,ifengChannelBean);
    }
}
