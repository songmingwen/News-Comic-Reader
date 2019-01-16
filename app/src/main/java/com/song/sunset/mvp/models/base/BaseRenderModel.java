package com.song.sunset.mvp.models.base;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Song on 2017/4/18 0018.
 * E-mail: z53520@qq.com
 */

public interface BaseRenderModel<Bean> {

    void render(RecyclerView.ViewHolder holder, Bean bean);

}
