package com.song.sunset.utils.api;

import com.song.sunset.beans.ComicRankListBean;
import com.song.sunset.beans.ComicReadChapterBean;
import com.song.sunset.beans.ComicSearchResultBean;
import com.song.sunset.beans.basebeans.BaseBean;
import com.song.sunset.beans.ComicClassifyBean;
import com.song.sunset.beans.ComicDetailBean;
import com.song.sunset.beans.ComicListBean;
import com.song.sunset.beans.ComicReadBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Song on 2016/9/18 0018.
 * Email:z53520@qq.com
 */
public interface U17ComicApi {

    /**
     * 此处若不添加max-age信息，会统一在OfflineCacheControlInterceptor类中添加默认的max-age
     */
    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("comic/detail_static_new")
    Call<BaseBean<ComicDetailBean>> queryComicDetailRDByCall(
            @Query("comicid") int comicid);

    @Headers("Cache-Control: public, max-age=120")
    @GET("comic/detail_static_new")
    Observable<BaseBean<ComicDetailBean>> queryComicDetailRDByObservable(
            @Query("comicid") int comicid);

    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("list/commonComicList")
    Call<BaseBean<ComicListBean>> queryComicListRDByCall(
            @Query("page") int page,
            @Query("argName") String argName,
            @Query("argValue") int argValue);

    @GET("list/commonComicList")
    Observable<BaseBean<ComicListBean>> queryComicListRDByObservable(
            @Query("page") int page,
            @Query("argName") String argName,
            @Query("argValue") int argValue);

    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("comic/chapterlist")
    Call<BaseBean<List<ComicReadBean>>> queryComicReadRDByCall(
            @Query("comicid") String comicid);

    @GET("comic/chapterlist")
    Observable<BaseBean<List<ComicReadBean>>> queryComicReadRDByObservable(
            @Query("comicid") String comicid);

    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("sort/mobileCateList")
    Call<BaseBean<ComicClassifyBean>> queryComicClassifyBeanByCall(
            @Query("version") int version);

    @GET("sort/mobileCateList")
    Observable<BaseBean<ComicClassifyBean>> queryComicClassifyBeanByObservable(
            @Query("version") int version);

    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("rank/list")
    Call<BaseBean<ComicRankListBean>> queryComicRankListBeanByCall();

    @GET("rank/list")
    Observable<BaseBean<ComicRankListBean>> queryComicRankListBeanByObservable();

    @GET("comic/chapterNew")
    Observable<BaseBean<ComicReadChapterBean>> queryNewComicReadRDByObservable(
            @Query("chapter_id") String chapterId);

    @GET("search/relative")
    Observable<BaseBean<List<ComicSearchResultBean>>> queryComicSearchResultRDByObservable(
            @Query("inputText") String inputText);
}
