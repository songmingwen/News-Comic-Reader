package com.song.sunset.activitys.opengl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.activitys.opengl.render.*
import com.song.sunset.activitys.opengl.teach.OpenGLTeachListActivity
import com.song.sunset.addButton
import com.song.sunset.beans.FuncItem
import com.song.sunset.holders.FunctionItemHolder
import com.zhihu.android.sugaradapter.SugarAdapter
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

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SugarAdapter.Builder
            .with(getFunctionList())
            .add(FunctionItemHolder::class.java)
            .build()
    }

    private fun getFunctionList(): List<FuncItem> {
        val list = ArrayList<FuncItem>()
        list.add(FuncItem("first") { BaseRenderActivity.start(this, RenderFirstActivity::class.java) })
        list.add(FuncItem("render3D") { BaseRenderActivity.start(this, Render3DActivity::class.java) })
        list.add(FuncItem("renderTexture") { BaseRenderActivity.start(this, RenderTextureActivity::class.java) })
        list.add(FuncItem("renderAir") { BaseRenderActivity.start(this, RenderAirHockeyActivity::class.java) })
        list.add(FuncItem("OpenGLTeach") { OpenGLTeachListActivity.start(this) })
        return list
    }
}
