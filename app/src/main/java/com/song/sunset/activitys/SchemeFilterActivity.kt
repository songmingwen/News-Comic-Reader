package com.song.sunset.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.alibaba.android.arouter.facade.callback.NavCallback
import com.alibaba.android.arouter.facade.Postcard
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.utils.BuildConfig
import org.w3c.dom.Text

/**
 * @author songmingwen
 * @since 2019/1/17
 */
class SchemeFilterActivity : BaseActivity() {

    val TAG: String by lazy { SchemeFilterActivity::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri = intent.data
        if (uri == null) {
            finish()
        }

        val aPath = buildPath(uri!!)

        if (!TextUtils.isEmpty(aPath)) {
            ARouter.getInstance()
                    .build(aPath)
                    .withBundle("bundle", getBundle(uri))
                    .navigation(this, object : NavCallback() {

                        override fun onArrival(postcard: Postcard) {
                            Log.i(TAG, "onArrival")
                            finish()
                        }

                        override fun onLost(postcard: Postcard?) {
                            startActivity(Intent(Intent.ACTION_VIEW, uri))
                            finish()
                            Log.i(TAG, "onLost")
                        }
                    })
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, uri))
            finish()
        }
    }

    /**
     * 返回 ARouter 需要的 path。如果不匹配返回 null
     */
    private fun buildPath(uri: Uri): String? {
        val scheme = uri.scheme
        val host = uri.host
        val path = uri.path
        if (TextUtils.equals("song", scheme)
                && !TextUtils.isEmpty(host) && !TextUtils.isEmpty(path)) {
            return "/$host$path"
        }
        if (TextUtils.equals("https", scheme) || TextUtils.equals("http", scheme)) {
            if (TextUtils.equals("song", host) && !TextUtils.isEmpty(path)
                    && uri.pathSegments != null && uri.pathSegments.size > 1) {
                return path
            }
        }
        return null
    }

    private fun getBundle(uri: Uri?): Bundle {
        val bundle = Bundle()
        val queryList = uri?.queryParameterNames
        if (queryList == null || queryList.isEmpty()) {
            return bundle
        }
        for (query in queryList) {
            val value = uri.getQueryParameter(query)
            bundle.putString(query, value)
        }
        return bundle
    }
}