package com.song.sunset.mvp.models;

import com.song.sunset.beans.ChapterListBean;
import com.song.sunset.beans.ComicReadChapterBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.interfaces.ComicReadView;
import com.song.sunset.utils.api.U17ComicApi;
import com.song.sunset.utils.retrofit.RetrofitCallback;
import com.song.sunset.utils.retrofit.Net;
import com.song.sunset.utils.rxjava.RxUtil;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Song on 2017/8/2 0002.
 * E-mail: z53520@qq.com
 */

public class ComicReadModel {

    private ArrayList<ChapterListBean> mChapterList;
    private int mCurrentTopPosition;
    private int mCurrentBottomPosition;
    private ComicReadView mComicReadView;
    private boolean isLoadingTop;
    private boolean isLoadingBottom;

    public void setChapterList(ArrayList<ChapterListBean> chapterList, ComicReadView comicReadView) {
        this.mChapterList = chapterList;
        this.mComicReadView = comicReadView;
    }

    /**
     * 初次加载数据：如果成功返回但没有数据，认为该漫画需要收费，默认请求第一章数据
     * 如果成功返回有数据，接下来请求上一章、下一章数据。
     *
     * @param firstPosition 初次加载时的章节
     */
    public void firstLoad(int firstPosition) {
        isLoadingTop = false;
        isLoadingBottom = false;
        mCurrentTopPosition = firstPosition - 1;
        mCurrentBottomPosition = firstPosition + 1;
        Observable<BaseBean<ComicReadChapterBean>> observable =
                Net
                        .createService(U17ComicApi.class).
                        queryNewComicReadRDByObservable(mChapterList.get(firstPosition).getChapter_id());
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicReadChapterBean>() {
            @Override
            public void onSuccess(ComicReadChapterBean comicReadChapterBean) {
                if (mComicReadView != null) {
                    if (comicReadChapterBean.getImage_list() == null) {
                        firstLoad(0);
                    } else {
                        mComicReadView.loadFirstPage(true, comicReadChapterBean.getImage_list());
                        loadTop();
                        loadBottom();
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (mComicReadView != null) {
                    mComicReadView.loadFirstPage(false, null);
                }
            }
        });
    }

    /**
     * 加载上一章数据：
     */
    public void loadTop() {
        if (mCurrentTopPosition < 0) {
            isLoadingTop = false;
            return;
        }
        if (isLoadingTop) {
            return;
        }
        isLoadingTop = true;
        Observable<BaseBean<ComicReadChapterBean>> observable =
                Net.createService(U17ComicApi.class).
                        queryNewComicReadRDByObservable(mChapterList.get(mCurrentTopPosition).getChapter_id());
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicReadChapterBean>() {
            @Override
            public void onSuccess(ComicReadChapterBean comicReadChapterBean) {
                if (mComicReadView != null) {
                    if (comicReadChapterBean.getImage_list() == null) {
                        mComicReadView.loadPreviousPage(false, null);
                    } else {
                        mCurrentTopPosition = mCurrentTopPosition - 1;
                        mComicReadView.loadPreviousPage(true, comicReadChapterBean.getImage_list());
                    }
                    isLoadingTop = false;
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (mComicReadView != null) {
                    mComicReadView.loadPreviousPage(false, null);
                    isLoadingTop = false;
                }
            }
        });
    }

    /**
     * 加载下一章数据：
     */
    public void loadBottom() {
        if (mCurrentBottomPosition > mChapterList.size() - 1) {
            return;
        }
        if (isLoadingBottom) {
            return;
        }
        isLoadingBottom = true;
        Observable<BaseBean<ComicReadChapterBean>> observable =
                Net.createService(U17ComicApi.class).
                        queryNewComicReadRDByObservable(mChapterList.get(mCurrentBottomPosition).getChapter_id());
        RxUtil.comicSubscribe(observable, new RetrofitCallback<ComicReadChapterBean>() {
            @Override
            public void onSuccess(ComicReadChapterBean comicReadChapterBean) {
                if (mComicReadView != null) {
                    if (comicReadChapterBean.getImage_list() == null) {
                        mComicReadView.loadNextPage(false, null);
                    } else {
                        mCurrentBottomPosition = mCurrentBottomPosition + 1;
                        mComicReadView.loadNextPage(true, comicReadChapterBean.getImage_list());
                    }
                    isLoadingBottom = false;
                }
            }

            @Override
            public void onFailure(int errorCode, String errorMsg) {
                if (mComicReadView != null) {
                    mComicReadView.loadNextPage(false, null);
                    isLoadingBottom = false;
                }
            }
        });
    }
}
