package com.song.sunset.activitys

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.content.Context
import com.song.sunset.base.activity.BaseActivity
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.utils.ScreenUtils
import com.song.sunset.base.utils.SdCardUtil
import com.davemorrissey.labs.subscaleview.ImageSource
import android.graphics.Bitmap
import android.view.View.OnLongClickListener
import android.widget.Toast
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Handler
import android.view.View
import coil.imageLoader
import coil.request.ImageRequest
import coil.target.Target
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.utils.BitmapUtil
import com.song.sunset.base.utils.FileUtils
import kotlinx.android.synthetic.main.activity_scale_pic.*
import kotlinx.android.synthetic.main.activity_second_floor.*
import java.io.File

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
@Route(path = "/song/pic/scale")
class ScalePicActivity : BaseActivity() {
    private var picUrl: String? = null
    private var picId: String? = null
    private var hasCache = false
    private var file: File? = null
    private var mOnRetryListener: View.OnClickListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scale_pic)
        ScreenUtils.fullscreen(this, true)
        progress!!.showLoading()
        mOnRetryListener = View.OnClickListener {
            progress!!.showLoading()
            setBitmapFromNet()
        }
        if (intent != null) {
            picUrl = intent.getStringExtra(PIC_URL)
            if (picUrl != null && picUrl!!.contains("ori.")) {
                picUrl = picUrl!!.replace("ori.", "")
            }
            picId = intent.getStringExtra(PIC_ID)
        }
        initView()
        if (!setBitmapFromLocation()) {
            setBitmapFromNet()
        } else {
            txt_save_pic_tip!!.visibility = View.GONE
        }
    }

    private fun initView() {
        id_pic_activity_image.setDoubleTapZoomDuration(200)
        id_pic_activity_image.setDoubleTapZoomScale(2.5f)
        id_pic_activity_image.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE)
        id_pic_activity_image.maxScale = 5f
    }

    private fun startFadeAnim() {
        val animator = AnimatorInflater.loadAnimator(this, R.animator.anim_save_pic_tip_fade)
        animator.setTarget(txt_save_pic_tip)
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                txt_save_pic_tip!!.visibility = View.GONE
            }
        })
    }

    private fun setBitmapFromLocation(): Boolean {
        file = File(SdCardUtil.getNormalSDCardPath() + "/Sunset/SavedCover", "$picId.jpg")
        return if (file!!.exists()) {
            id_pic_activity_image!!.setImage(ImageSource.uri(Uri.fromFile(file)))
            progress!!.showContent()
            setListener(null)
            true
        } else {
            false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun setBitmapFromNet() {
        hasCache = false
        val request = ImageRequest.Builder(this)
                .data(picUrl)
                .target(object : Target {
                    override fun onStart(placeholder: Drawable?) {
                        super.onStart(placeholder)
                    }

                    override fun onError(error: Drawable?) {
                        progress!!.showError(mOnRetryListener)
                    }

                    override fun onSuccess(result: Drawable) {
                        hasCache = true
                        progress!!.showContent()
                        fadeAnimDelay()
                        val bitmap = BitmapUtil.drawableToBitmap(result)
                        id_pic_activity_image!!.setImage(ImageSource.bitmap(bitmap))
                        setListener(bitmap)
                    }
                }
                )
                .build()


        val disp = this.imageLoader.enqueue(request)
    }

    private fun fadeAnimDelay() {
        Handler().postDelayed({ startFadeAnim() }, 1000)
    }

    private fun setListener(response: Bitmap?) {
        id_pic_activity_image!!.setOnLongClickListener(OnLongClickListener {
            if (response != null) {
                if (SdCardUtil.isSdCardAvailable()) {
                    if (file != null && file!!.exists()) {
                        Toast.makeText(application, "图片已保存", Toast.LENGTH_SHORT).show()
                        return@OnLongClickListener true
                    }
                    savePic(response)
                }
            } else {
                Toast.makeText(application, "图片已保存", Toast.LENGTH_SHORT).show()
            }
            false
        })
    }

    private fun savePic(response: Bitmap) {
        object : Thread() {
            override fun run() {
                if (FileUtils.saveFile(response, "/Sunset/SavedCover", "$picId.jpg")) {
                    showResult("图片保存成功")
                } else {
                    showResult("图片保存失败")
                }
                super.run()
            }
        }.start()
    }

    private fun showResult(s: String) {
        runOnUiThread { Toast.makeText(baseContext, s, Toast.LENGTH_SHORT).show() }
    }

    companion object {
        const val PIC_URL = "pic_url"
        const val PIC_ID = "pic_id"

        @JvmStatic
        fun start(context: Context, ori: String?, comicId: String?) {
            val intent = Intent(context, ScalePicActivity::class.java)
            intent.putExtra(PIC_URL, ori)
            intent.putExtra(PIC_ID, comicId)
            context.startActivity(intent)
        }
    }
}