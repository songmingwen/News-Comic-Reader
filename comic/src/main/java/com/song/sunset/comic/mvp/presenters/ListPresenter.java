package com.song.sunset.comic.mvp.presenters;

import com.song.sunset.comic.api.ListCallView;
import com.song.sunset.base.bean.PageEntity;
import com.song.sunset.comic.mvp.models.ListModel;

/**
 * Created by Song on 2017/11/14 0014.
 * E-mail: z53520@qq.com
 */

public class ListPresenter {
    private ListCallView view;
    private ListModel model;

    public ListPresenter(ListCallView view) {
        this.view = view;
        model = new ListModel();
    }

    /**
     * 第一次加载，或者第一次加载失败重试时会调用此方法
     */
    public void firstLoading() {
        if (!isDestroyed()) {
            model.firstLoading(view);
        }
    }

    /**
     * 请求数据成功，设置数据调用
     * @param bean
     * @param <Bean>
     */
    public <Bean extends PageEntity> void setData(Bean bean) {
        if (!isDestroyed()) {
            model.setData(view, bean);
        }
    }

    /**
     * 请求数据失败调用
     */
    public void dealWithError() {
        if (!isDestroyed()) {
            model.dealWithError(view);
        }
    }

    /**
     * 开始下拉刷新调用
     */
    public void startRefresh() {
        if (!isDestroyed()) {
            model.startRefresh(view);
        }
    }

    /**
     * 开始加载更多调用
     */
    public void loadMore() {
        if (!isDestroyed()) {
            model.loadMore(view);
        }
    }

    /**
     * 无限加载相关设置
     * @param ignoreTotalPage true 可无限加载.
     *                        false 根据后端返回总页数显示数据，当currentPage>totalPage时底部显示无数据，不在请求数据.
     *                        默认值为true.
     */
    public void setIgnoreTotalPage(boolean ignoreTotalPage) {
        if (!isDestroyed()) {
            model.setIgnoreTotalPage(ignoreTotalPage);
        }
    }

    public boolean isFirstLoading() {
        return isDestroyed() || model.isFirstLoading();
    }

    private boolean isDestroyed() {
        return model == null || view == null;
    }
}
