package com.song.sunset.utils.service;

import com.song.sunset.beans.VideoBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by songmw3 on 2016/12/21.
 */

public interface VideoApi {

    @GET("ifengvideoList")
    Observable<List<VideoBean>> queryFirstVideoRDByGetObservable(
            @Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoBean>> queryVideoRDByGetObservable(
            @Query("page") int page,
            @Query("listtype") String listtype,
            @Query("typeid") String typeid);
}
