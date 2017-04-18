package com.song.sunset.model.base;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;

public abstract class IfengBaseRenderModel implements BaseRenderModel<IfengChannelBean>{

    public abstract void render(RecyclerView.ViewHolder holder, IfengChannelBean bean);

}
