package com.song.sunset.mvp.views;

import com.song.sunset.widget.LoadingFooter;
import com.song.sunset.base.bean.PageEntity;

/**
 * Created by Song on 2017/11/14 0014.
 * E-mail: z53520@qq.com
 */

public interface ListCallView {
    /**
     * 第一次加载，或者第一次加载失败重试时会调用此方法
     */
    void firstLoading();

    /**
     * 加载成功后会调用此方法，用于显示内容界面
     */
    void showContent();

    /**
     * 加载失败会调用此方法，用于显示失败界面
     */
    void showError();

    /**
     * 数据加载成功后会调用此方法，用于设置数据
     * @param atTop 数据是否应该加到顶部，true：加到顶部
     * @param bean 加载成功返回的数据
     * @param <BeanData> 数据类型
     */
    <BeanData extends PageEntity> void addData(boolean atTop, BeanData bean);

    /**
     * 下拉刷新完成时会调用此方法，用于隐藏列表顶部loading条
     */
    void refreshComplete();

    /**
     * 上拉加载完成会调用此方法，用于更新底部加载状态
     * @param state
     */
    void changeFooterState(LoadingFooter.State state);

    /**
     * 出发加载更多时会调用此方法，列表到达底部或者点击底部重试按钮均会触发此方法。
     * 用于外部请求数据
     * @param currentPage 当前应该请求的页面数
     */
    void loadMorePage(int currentPage);

    /**
     * 下拉刷新会调用此方法，用于外部请求数据
     */
    void refresh();
}
