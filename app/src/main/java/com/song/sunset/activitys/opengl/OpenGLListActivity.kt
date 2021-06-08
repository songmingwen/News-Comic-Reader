package com.song.sunset.activitys.opengl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.activitys.opengl.render.*
import com.song.sunset.activitys.opengl.teach.OpenGLTeachListActivity
import com.song.sunset.activitys.temp.addButton
import kotlinx.android.synthetic.main.activity_function_list.*

class OpenGLListActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OpenGLListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list)

        ll_function_container.apply { addButtonList() }
    }

    private fun LinearLayout.addButtonList() {
        addButton("first") { BaseRenderActivity.start(this@OpenGLListActivity, RenderFirstActivity::class.java) }
        addButton("render3D") { BaseRenderActivity.start(this@OpenGLListActivity, Render3DActivity::class.java) }
        addButton("renderTexture") { BaseRenderActivity.start(this@OpenGLListActivity, RenderTextureActivity::class.java) }
        addButton("renderAir") { BaseRenderActivity.start(this@OpenGLListActivity, RenderAirHockeyActivity::class.java) }
        addButton("OpenGLTeach") { OpenGLTeachListActivity.start(this@OpenGLListActivity) }
    }
}
