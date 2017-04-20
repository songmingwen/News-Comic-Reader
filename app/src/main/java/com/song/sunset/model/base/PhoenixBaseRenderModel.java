package com.song.sunset.model.base;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.PhoenixChannelBean;

public abstract class PhoenixBaseRenderModel implements BaseRenderModel<PhoenixChannelBean>{

    public abstract void render(RecyclerView.ViewHolder holder, PhoenixChannelBean bean);

}
