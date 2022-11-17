package com.song.sunset.phoenix.api;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Desc:    芒果接口
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2022/11/16 10:43
 */
public interface MGTVApi {

    @GET("back/send/im")
    Observable<Void> sendMsgSingle(
            @Query("uid") String sendUid,
            @Query("did") String sendDid,
            @Query("tuid") String receiveUid,
            @Query("content") String content);

}
