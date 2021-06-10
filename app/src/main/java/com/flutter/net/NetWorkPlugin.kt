package com.flutter.net

import android.annotation.SuppressLint
import android.util.Log
import androidx.annotation.NonNull
import com.song.sunset.utils.net.SchedulerTransformer
import com.song.sunset.base.net.OkHttpClient
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.Call
import okhttp3.Callback
import java.io.IOException

class NetworkPlugin : FlutterPlugin, MethodCallHandler {

    companion object{

        private const val PLUGIN_NAME_NET = "plugins.flutter.song.net"

        fun registerWith(registrar: BinaryMessenger) {
            val channel = MethodChannel(registrar, PLUGIN_NAME_NET)
            channel.setMethodCallHandler(NetworkPlugin())
        }
    }

    private val disposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }
    private val client = OkHttpClient.getInstance().createClient(null)

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        registerWith(flutterPluginBinding.binaryMessenger)
        Log.i("NetworkPlugin", "onAttachedToEngine")
    }

    @SuppressLint("CheckResult")
    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        val data = requestParser(call)

        Log.i("NetworkPlugin", "onMethodCall：$data")

        if (data.url.isEmpty()) {
            result.notImplemented()
            return
        }

        Single.create<Response> { createEmitter(data, it) }
                .compose(SchedulerTransformer())
                .subscribe({
                    result.success(it.transformNetData2Map())
                    Log.i("NetworkPlugin", "success：${it.transformNetData2Map()}")
                }) { exception ->
                    result.error("Request error.", exception.message, null)
                    Log.i("NetworkPlugin", "error：${exception.message}")
                }.add(disposable)
    }

    private fun createEmitter(data: Request, it: SingleEmitter<Response>) {
        try {
            val request = buildRequest(data)
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    it.onError(e)
                }

                override fun onResponse(call: Call, response: okhttp3.Response) {
                    it.onSuccess(Response(response.code(), response.message(), response.headers(), response.body()?.string()
                            ?: ""))
                }
            })
        } catch (e: Exception) {
            it.onError(e)
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        disposable.dispose()
    }

    private fun Disposable.add(compositeDisposable: CompositeDisposable) {
        compositeDisposable.add(this)
    }


}
