package com.song.sunset.mvp.models;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;

import com.song.sunset.beans.PhoenixChannelBean;

/**
 * Created by Song on 2017/4/7 0007.
 * E-mail: z53520@qq.com
 */

public class PhoenixDefaultAdapterModel extends PhoenixBaseAdapterModel {

    @Override
    public int getViewType(PhoenixChannelBean phoenixChannelBean){
        return super.getViewType(phoenixChannelBean);
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int viewType){
        return super.getViewHolder(context,parent,viewType);
    }

    @Override
    public void render(Context context, int itemViewType, RecyclerView.ViewHolder holder, PhoenixChannelBean phoenixChannelBean){
        super.render(context,itemViewType,holder, phoenixChannelBean);
    }
}
