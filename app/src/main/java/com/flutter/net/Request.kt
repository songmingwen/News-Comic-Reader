package com.flutter.net

import com.song.sunset.utils.api.WholeApi
import io.flutter.plugin.common.MethodCall
import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.RequestBody
import okhttp3.internal.Util

/**
 * @author songmingwen
 * @description
 * @since 2020/4/30
 */
data class Request(val type: String, val url: String,
                   val header: Map<String, String>?,
                   val query: Map<String, String>?,
                   val body: Map<String, String>?
)

fun requestParser(call: MethodCall): Request {
    return Request(
            call.method!!,
            call.argument<String>("url")!!,
            call.argument<HashMap<String, String>>("header"),
            call.argument<HashMap<String, String>>("query"),
            call.argument<HashMap<String, String>>("body")
    )
}

fun buildRequest(request: Request): okhttp3.Request {
    return okhttp3.Request.Builder()
            .parseUrl(request.url, request.query)
            .parseHeader(request.header)
            .parseMethod(request.type, request.body)
            .build()
}

private fun okhttp3.Request.Builder.parseUrl(
        url: String,
        query: Map<String, Any>?
): okhttp3.Request.Builder {
    val pos = Util.skipLeadingAsciiWhitespace(url, 0, url.length)
    val hasScheme = when {
        url.startsWith("https:", pos, ignoreCase = true) -> true
        url.startsWith("http:", pos, ignoreCase = true) -> true
        else -> false
    }
    // 如果 Url 不包含 Scheme，默认添加 ApiEnv.getInstance().baseUrl
    var finalUrl = if (hasScheme) {
        url
    } else {
        WholeApi.COMIC_NEW_BASE_URL + url
    }

    val httpBuilder = HttpUrl.parse(finalUrl)?.newBuilder()
    if (httpBuilder != null && query != null) {
        for ((key, value) in query) {
            httpBuilder.addQueryParameter(key, value.toString())
        }
        finalUrl = httpBuilder.toString()
    }

    url(finalUrl)
    return this
}

private fun okhttp3.Request.Builder.parseHeader(header: Map<String, String>?): okhttp3.Request.Builder {
    if (header != null) {
        for ((key, value) in header) {
            header(key, value)
        }
    }
    return this
}

private fun okhttp3.Request.Builder.parseMethod(
        type: String,
        body: Map<String, Any>?
) = when (type) {
    "get" -> get()
    "post" -> post(buildFormBody(body))
    "put" -> put(buildFormBody(body))
    "delete" -> delete(Util.EMPTY_REQUEST)
    else -> this
}

private fun buildFormBody(body: Map<String, Any>?): RequestBody {
    return FormBody.Builder()
            .addAll(body)
            .build()
}

private fun FormBody.Builder.addAll(body: Map<String, Any>?): FormBody.Builder {
    if (body != null) {
        for ((key, value) in body) {
            add(key, value.toString())
        }
    }
    return this
}
