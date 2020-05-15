package com.song.sunset.activitys.temp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.utils.BitmapUtil
import kotlinx.android.synthetic.main.activity_camera.*
import java.lang.Exception

/**
 * @author songmingwen
 * @description
 * @since 2020/4/23
 */

class CameraActivity : BaseActivity(), TextureView.SurfaceTextureListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CameraActivity::class.java))
        }

        fun getBackCameraId(): Int {
            return getCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
        }

        fun getFrontCameraId(): Int {
            return getCameraId(Camera.CameraInfo.CAMERA_FACING_FRONT)
        }

        private fun getCameraId(frontBack: Int): Int {
            val numberOfCameras = Camera.getNumberOfCameras()
            val info = Camera.CameraInfo()
            for (index in 0..numberOfCameras) {
                Camera.getCameraInfo(index, info)
                if (info.facing == frontBack) {
                    return index
                }
            }
            return 0
        }
    }

    private var mCamera: Camera? = null
    private var mTextureView: TextureView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        mTextureView = TextureView(this)
        mTextureView?.surfaceTextureListener = this
        frame_layout.addView(mTextureView)
        take_pic.setOnClickListener {
            mCamera?.takePicture({ },
                    { _, _ -> run {} },
                    { data, _ ->
                        run {
                            img_show.setImageBitmap(BitmapUtil.byteToBitmap(data))
                            mCamera?.startPreview()
                        }
                    })
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
        val openId = getBackCameraId()
        mCamera = Camera.open(openId)
        try {
            mCamera?.setPreviewTexture(surface)
            setCameraDisplayOrientation(this, openId, mCamera!!)
            mCamera?.startPreview()
        } catch (e: Exception) {

        }
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {

    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {

    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
        mCamera?.stopPreview()
        mCamera?.release()
        return true
    }

    /**
     * 调整成像方向，在横竖屏时保证图像永远朝上。
     */
    private fun setCameraDisplayOrientation(activity: Activity, cameraId: Int, camera: Camera) {
        val info = Camera.CameraInfo()
        Camera.getCameraInfo(cameraId, info)
        val rotation = activity.windowManager.defaultDisplay.rotation
        val degrees = when (rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> 0
        }
        var result: Int
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360
        } else {
            result = (info.orientation - degrees + 360) % 360
        }
        camera.setDisplayOrientation(result)
    }

}