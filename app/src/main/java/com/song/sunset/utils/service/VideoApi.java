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
    String baseUrl = "http://api.iclient.ifeng.com/";
    //http://api.iclient.ifeng.com/ifengvideoList?page=1&gv=5.4.1&av=5.4.1&uid=865982024584370&deviceid=865982024584370&proid=ifengnews&os=android_23&df=androidphone&vt=5&screen=1080x1920&publishid=9023&nw=wifi
    //http://api.iclient.ifeng.com/ifengvideoList?page=1&listtype=list&typeid=clientvideo_9&gv=5.4.1&av=5.4.1&uid=865982024584370&deviceid=865982024584370&proid=ifengnews&os=android_23&df=androidphone&vt=5&screen=1080x1920&publishid=9023&nw=wifi
    @GET("ifengvideoList")
    Observable<List<VideoBean>> queryFirstVideoRDByGetObservable(
            @Query("page") int page);

    @GET("ifengvideoList")
    Observable<List<VideoBean>> queryVideoRDByGetObservable(
            @Query("page") int page,
            @Query("listtype") String listtype,
            @Query("typeid") String typeid);
}
