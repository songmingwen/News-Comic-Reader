package com.song.sunset.model.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Song on 2017/4/7 0007.
 * E-mail: z53520@qq.com
 */

public interface RecyclerViewAdapterModel<Bean> {
    /**
     * 根据item数据，返回视图的类型
     * @param bean item数据对象
     * @return 返回item的类型
     */
    int getViewType(Bean bean);

    /**
     * 通过视图类型创建并返回相对应的viewholder
     * @param context 上下文对象
     * @param parent    父布局
     * @param viewType  视图类型
     * @return
     */
    RecyclerView.ViewHolder getViewHolder(Context context, ViewGroup parent, int viewType);

    /**
     * 通过视图类型渲染视图
     * @param context 上下文对象
     * @param itemViewType 视图类型
     * @param holder 试图对应的viewholder
     * @param bean  item数据对象
     */
    void render(Context context, int itemViewType, RecyclerView.ViewHolder holder, final Bean bean);
}
