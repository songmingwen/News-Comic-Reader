package com.song.sunset.activitys.opengl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.activitys.opengl.render.Render3DActivity
import com.song.sunset.activitys.opengl.render.RenderFirstActivity
import com.song.sunset.activitys.opengl.render.RenderTextureActivity

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
        RenderFirstActivity.start(this)
    }

    fun render3D(view: View) {
        Render3DActivity.start(this)
    }

    fun renderTexture(view: View) {
        RenderTextureActivity.start(this)
    }
}