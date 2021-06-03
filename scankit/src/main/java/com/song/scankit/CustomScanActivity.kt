package com.song.scankit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.snackbar.Snackbar
import com.huawei.hms.hmsscankit.RemoteView
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.utils.ScreenUtils
import com.song.sunset.utils.SnackBarUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_custom_scan.*

@Route(path = "/scan/custom")
class CustomScanActivity : BaseActivity() {

    private var remoteView: RemoteView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_scan)

        requestPermissions(content) {
            val rect = Rect().apply {
                left = 0
                right = ScreenUtils.getScreenWidth(this@CustomScanActivity)
                top = 0
                bottom = ScreenUtils.getScreenHeight(this@CustomScanActivity)
            }
            remoteView = RemoteView.Builder().setContext(this).setBoundingBox(rect)
                    .setFormat(HmsScan.ALL_SCAN_TYPE).build()
            remoteView?.onCreate(savedInstanceState)
            remoteView?.setOnResultCallback { result ->
                if (result != null && result.isNotEmpty() && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
                    val intent = Intent()
                    intent.apply {
                        putExtra(ScanUtil.RESULT, result[0])
                    }
                    setResult(Activity.RESULT_OK, intent)
                    this.finish()
                }
            }

            // 添加 RemoteView 至布局.
            val params = FrameLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
            content.addView(remoteView, params)

        }
    }

    override fun onStart() {
        super.onStart()
        remoteView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        remoteView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        remoteView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        remoteView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        remoteView?.onStop()
    }

    private fun requestPermissions(view: View, startScan: () -> Unit) {
        val disposable = RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        startScan.invoke()
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            SnackBarUtils.show(view, "请授权相机使用权限后使用扫码功能", Snackbar.LENGTH_INDEFINITE, "授权") {
                                startScan.invoke()
                            }
                        } else {
                            SnackBarUtils.show(view, "您永久禁止了该权限，如需授权请到应用设置中主动打开相机权限",
                                    Snackbar.LENGTH_INDEFINITE, "知道了") { }
                        }
                    }
                }

        compositeDisposable.add(disposable)
    }
}