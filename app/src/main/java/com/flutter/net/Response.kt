package com.flutter.net

import com.song.sunset.utils.JsonUtil
import okhttp3.Headers

/**
 * @author songmingwen
 * @description
 * @since 2020/4/30
 */
data class Response(val code: Int, val msg: String, val headers: Headers, val data: String) {

    fun transformNetData2Map() : Map<String, Any> {
        val map = HashMap<String, Any>()
        map["code"] = code
        map["msg"] = msg
        map["data"] = data
        map["headers"] = JsonUtil.gsonToString(headers.toMultimap())
        return map
    }
}

