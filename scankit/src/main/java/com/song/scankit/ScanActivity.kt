package com.song.scankit

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.utils.BuildConfig
import com.song.sunset.utils.SnackBarUtils
import com.tbruyelle.rxpermissions2.RxPermissions

@Route(path = "/scan/list")
class ScanActivity : BaseActivity() {

    companion object {
        const val REQUEST_CODE_SCAN_DEFAULT_MODE = 10086
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

    }

    /**
     * onClick
     */
    fun startDefaultMode(view: View) {
        requestPermissions(view) {
            val options = HmsScanAnalyzerOptions.Creator().setHmsScanTypes(HmsScan.ALL_SCAN_TYPE).create()
            ScanUtil.startScan(this, REQUEST_CODE_SCAN_DEFAULT_MODE, options)
        }
    }

    /**
     * onClick
     */
    fun startCustomizedMode(view: View) {
        ARouter.getInstance().build("/scan/custom").navigation(this, REQUEST_CODE_SCAN_DEFAULT_MODE)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        if (requestCode == REQUEST_CODE_SCAN_DEFAULT_MODE) {
            val hmsScan: HmsScan? = data.getParcelableExtra(ScanUtil.RESULT)
            if (!TextUtils.isEmpty(hmsScan?.getOriginalValue())) {

                val value = hmsScan?.getOriginalValue()

                Toast.makeText(this, value, Toast.LENGTH_LONG).show()

                val uri = Uri.parse(value)

                val scheme = uri.scheme

                if (!TextUtils.isEmpty(scheme)) {
                    val intent = Intent(ACTION_VIEW, uri)
                    intent.`package` = BuildConfig.APPLICATION_ID
                    startActivity(intent)
                    return
                }
            }
        }
    }

}