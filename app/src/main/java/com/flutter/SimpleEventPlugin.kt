package com.flutter

import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.song.sunset.base.AppConfig
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel

/**
 * @author songmingwen
 * @description
 * @since 2020/12/30
 */
class SimpleEventPlugin : FlutterPlugin, EventChannel.StreamHandler {

    companion object {
        private const val PLUGIN_NAME_ROUTER = "plugins.flutter.song.sensor"
        private const val TAG = "sensor_event"

        fun registerWith(registrar: BinaryMessenger) {
            val channel = EventChannel(registrar, PLUGIN_NAME_ROUTER)
            val instance = SimpleEventPlugin()
            channel.setStreamHandler(instance)
        }
    }

    private lateinit var mAccelerometerListener: SensorEventListener

    private lateinit var sensorManager: SensorManager

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        registerWith(binding.binaryMessenger)
        Log.i("SimpleEventPlugin", "onAttachedToEngine")
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        Log.i(TAG, "native_onDetachedFromEngine")
        onCancel(null)
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Log.i(TAG, "native_onListen")

        sensorManager = AppConfig.getApp().getSystemService(SENSOR_SERVICE) as SensorManager
        val sensorType: Int = Sensor.TYPE_ACCELEROMETER

        mAccelerometerListener = object : SensorEventListener {

            override fun onSensorChanged(sensorEvent: SensorEvent) {
                if (sensorEvent.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                    Log.i(TAG, "native_onSensorChanged")

                    //图解中已经解释三个值的含义
                    val X_lateral = sensorEvent.values[0]
                    val Y_longitudinal = sensorEvent.values[1]
                    val Z_vertical = sensorEvent.values[2]
                    events?.success("X = $X_lateral; Y = $Y_longitudinal; Z = $Z_vertical")
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.i(TAG, "native_onAccuracyChanged")
            }
        }

        sensorManager.registerListener(mAccelerometerListener, sensorManager.getDefaultSensor(sensorType), SensorManager.SENSOR_DELAY_NORMAL)

    }

    override fun onCancel(arguments: Any?) {
        Log.i(TAG, "native_cancel")
        sensorManager.unregisterListener(mAccelerometerListener)
    }

}