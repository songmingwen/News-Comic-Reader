package com.song.sunset.phoenix.api;

import com.song.sunset.phoenix.bean.PhoenixNewsListBean;
import com.song.sunset.phoenix.bean.VideoListsBean;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Observable;

/**
 * Created by Song on 2017/3/30 .
 * E-mail: z53520@qq.com
 */

public interface PhoenixNewsApi {

    /**此处若不添加max-age信息，会统一在OfflineCacheControlInterceptor类中添加默认的max-age*/
    //    @Headers("Cache-Control: public, max-age=3600")
    @GET("ifengvideoList")
    Observable<List<VideoListsBean>> queryFirstVideoObservable(
            @Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoListsBean>> queryVideoObservable(
            @Query("page") int page,
            @Query("listtype") String listtype,
            @Query("typeid") String typeid);

    String UP = "up";
    String DOWN = "down";

    /**
     * 凤凰新闻头条接口
     *
     * @param action up 上拉 down 下拉
     * @return
     */
    @GET("ClientNews")
    Observable<List<PhoenixNewsListBean>> queryPhoenixListObservable(
            @Query("action") String action, @Query("time") String time);


    Observable<ResponseBody> getImage();
}
