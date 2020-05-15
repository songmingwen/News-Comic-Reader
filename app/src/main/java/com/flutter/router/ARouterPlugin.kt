package com.flutter.router

import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import kotlin.collections.HashMap

/**
 * @author songmingwen
 * @description
 * @since 2020/5/7
 */
class ARouterPlugin : FlutterPlugin, MethodChannel.MethodCallHandler {

    companion object {
        private const val PLUGIN_NAME_ROUTER = "plugins.flutter.song.arouter"

        fun registerWith(registrar: BinaryMessenger) {
            val channel = MethodChannel(registrar, PLUGIN_NAME_ROUTER)
            val instance: ARouterPlugin = ARouterPlugin()
            channel.setMethodCallHandler(instance)
        }
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        registerWith(binding.binaryMessenger)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val path = call.method
        val query = call.argument<HashMap<String, Any>>("query")
        val postcard = ARouter.getInstance().build(path)
        withExtra(query, postcard)
        postcard.navigation()
    }

    private fun withExtra(query: HashMap<String, Any>?, postcard: Postcard) {
        if (query != null) {
            for ((key, value) in query) {
                when (value) {
                    is String -> {
                        postcard.withString(key, value)
                    }
                    is Boolean -> {
                        postcard.withBoolean(key, value)
                    }
                    is Int -> {
                        postcard.withInt(key, value)
                    }
                    is Long -> {
                        postcard.withLong(key, value)
                    }
                    is Double -> {
                        postcard.withDouble(key, value)
                    }
                }
            }
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {

    }

}