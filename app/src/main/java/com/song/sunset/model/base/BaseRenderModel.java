package com.song.sunset.model.base;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Song on 2017/4/18 0018.
 * E-mail: z53520@qq.com
 */

public interface BaseRenderModel<Bean> {

    void render(RecyclerView.ViewHolder holder, Bean bean);

}
