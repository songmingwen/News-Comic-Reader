package com.song.scankit

import android.Manifest
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.utils.SnackBarUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_qrcode.*

class QRCodeActivity : BaseActivity() {

    var lightOpen = false
    var manualIntervention = false

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, QRCodeActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        qr_code.setOnQRCodeReadListener { text, _ ->
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            qr_code.stopCamera()
        }

        qr_code.setQRDecodingEnabled(true)
        qr_code.setAutofocusInterval(2000L)
//        qr_code.setFrontCamera()
        qr_code.setBackCamera()

        ll_flash_content.setOnClickListener {
            manualIntervention = true
            lightOpen = !lightOpen
            resetImage()
            qr_code.setTorchEnabled(lightOpen)
        }

        lightSensorListener()

    }

    private fun lightSensorListener() {
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if (!hasLightSensor(sensorManager)) return
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) ?: return
        sensorManager.registerListener(object : SensorEventListener {

            override fun onSensorChanged(event: SensorEvent?) {
                if (!manualIntervention && event?.sensor?.type == Sensor.TYPE_LIGHT) {
                    val lightValue = event.values[0]
                    if (lightValue < 16) {
                        lightOpen = true
                        resetImage()
                        qr_code.setTorchEnabled(true)
                    }
                    if (lightValue > 256) {
                        lightOpen = false
                        resetImage()
                        qr_code.setTorchEnabled(false)
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }

        }, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    private fun resetImage() {
        img_flashlight.setImageResource(if (lightOpen) R.drawable.flashlight_open else R.drawable.flashlight_off)
    }

    private fun hasLightSensor(sensorManager: SensorManager): Boolean {
        return sensorManager.getSensorList(Sensor.TYPE_ALL)
                .any { it.type == Sensor.TYPE_LIGHT }
    }

    override fun onStart() {
        super.onStart()
        startScan()
    }

    private fun startScan() {
        var disposable = RxPermissions(this)
                .request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) {
                        qr_code.startCamera()
                    } else {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                            SnackBarUtils.show(qr_code, "请授权相机使用权限后使用扫码功能",
                                    Snackbar.LENGTH_INDEFINITE, "授权") { startScan() }
                        } else {
                            SnackBarUtils.show(qr_code, "您永久禁止了该权限，如需授权请到应用设置中主动打开相机权限",
                                    Snackbar.LENGTH_INDEFINITE, "知道了") { finish() }
                        }
                    }
                }
    }

    override fun onPause() {
        super.onPause()
        qr_code.stopCamera()
    }
}
