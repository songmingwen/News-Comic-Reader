package com.song.sunset.activitys.opengl.teach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.activitys.opengl.render.BaseRenderActivity
import com.song.sunset.addButton
import kotlinx.android.synthetic.main.activity_function_list.*

class OpenGLTeachListActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, OpenGLTeachListActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list)

        ll_function_container.apply {
            addButtonList()
        }
    }

    private fun LinearLayout.addButtonList() {
        addButton("One"){ BaseRenderActivity.start(this@OpenGLTeachListActivity, TeachOneActivity::class.java) }
        addButton("Two"){ BaseRenderActivity.start(this@OpenGLTeachListActivity, TeachTwoActivity::class.java) }
    }

}
