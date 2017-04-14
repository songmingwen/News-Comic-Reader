package com.song.sunset.model;


/**
 * Created by Song on 2017/4/13 0013.
 * E-mail: z53520@qq.com
 */

import android.support.v7.widget.RecyclerView;

import com.song.sunset.beans.IfengChannelBean;

public interface IfengBaseRenderModel {

    void render(RecyclerView.ViewHolder holder, IfengChannelBean bean);

}
