package com.song.sunset.comic.mvp.models;


import com.song.sunset.comic.api.ListCallView;
import com.song.sunset.base.bean.PageEntity;
import com.song.sunset.comic.widget.LoadingFooter;

/**
 * Created by Song on 2017/11/14 0014.
 * E-mail: z53520@qq.com
 */

public class ListModel {
    private boolean isFirstLoading = false;
    private boolean isRefreshing = false;
    private boolean isLoading = false;
    private boolean ignoreTotalPage = true;
    private int currentPage = 1;
    private int totalPage = Integer.MAX_VALUE;

    public void firstLoading(ListCallView view) {
        isFirstLoading = true;
        if (view != null) {
            view.firstLoading();
        }
    }

    public <Bean extends PageEntity> void setData(ListCallView view, Bean bean) {
        if (bean != null) {
            totalPage = bean.getOnePageCount();
        }
        if (view != null) {
            view.showContent();
            view.changeFooterState(LoadingFooter.State.Loading);
        }
        if (isFirstLoading) {
            if (view != null) {
                view.addData(true,bean);
                isFirstLoading = false;
            }
        } else if (isRefreshing) {
            isRefreshing = false;
            if (view != null) {
                view.addData(true, bean);
                view.refreshComplete();
            }
        } else {
            if (isLoading) {
                isLoading = false;
            }
            if (view != null) {
                view.addData(false, bean);
            }
        }
    }

    public void dealWithError(ListCallView view) {
        if (isRefreshing) {
            isRefreshing = false;
            if (view != null) {
                view.refreshComplete();
            }
        } else {
            currentPage--;
            if (isLoading) {
                isLoading = false;
                if (view != null) {
                    view.changeFooterState(LoadingFooter.State.NetWorkError);
                }
            } else {
                if (view != null) {
                    view.showError();
                }
            }
        }
    }

    public void loadMore(ListCallView view) {
        if (isLoading) {
            return;
        }
        if (ignoreTotalPage || currentPage < totalPage) {
            isLoading = true;
            currentPage++;
            if (view != null) {
                view.changeFooterState(LoadingFooter.State.Loading);
                view.loadMorePage(currentPage);
            }
        } else {
            if (view != null) {
                view.changeFooterState(LoadingFooter.State.TheEnd);
            }
        }
    }

    public void startRefresh(ListCallView view) {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        if (view != null) {
            view.refresh();
        }
    }

    public void setIgnoreTotalPage(boolean ignoreTotalPage) {
        this.ignoreTotalPage = ignoreTotalPage;
    }

    public boolean isFirstLoading() {
        return isFirstLoading;
    }
}
