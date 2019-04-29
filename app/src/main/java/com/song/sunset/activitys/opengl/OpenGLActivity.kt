package com.song.sunset.activitys.opengl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.activitys.opengl.render.*

class OpenGLActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OpenGLActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_gl)
    }

    fun first(view: View) {
        BaseRenderActivity.start(this, RenderFirstActivity::class.java)
    }

    fun render3D(view: View) {
        BaseRenderActivity.start(this, Render3DActivity::class.java)
    }

    fun renderTexture(view: View) {
        BaseRenderActivity.start(this, RenderTextureActivity::class.java)
    }

    fun renderAir(view: View) {
        BaseRenderActivity.start(this, RenderAirHockeyActivity::class.java)
    }
}
