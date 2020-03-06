package com.song.sunset.utils.api

import com.song.sunset.beans.zhihu.InterestList
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author songmingwen
 * @description 新用户引导接口类
 * @since 2018/10/17
 */


interface NewUserGuide {
    //-------------------------------- 新用户引导升级改造 以下 --------------------------------

    /**
     * 获取新用户引导标签列表
     */
    @get:GET("/member/guides/tag")
    val interestList: Observable<Response<InterestList>>

}
