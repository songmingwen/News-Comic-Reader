package com.song.sunset.activitys.opengl.teach

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.activitys.opengl.render.BaseRenderActivity
import com.song.sunset.addButton
import com.song.sunset.beans.FuncItem
import com.song.sunset.holders.FunctionItemHolder
import com.zhihu.android.sugaradapter.SugarAdapter
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

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SugarAdapter.Builder
            .with(getFunctionList())
            .add(FunctionItemHolder::class.java)
            .build()
    }

    private fun getFunctionList(): List<FuncItem> {
        val list = ArrayList<FuncItem>()
        list.add(FuncItem("One") { BaseRenderActivity.start(this, TeachOneActivity::class.java) })
        list.add(FuncItem("Two") { BaseRenderActivity.start(this, TeachTwoActivity::class.java) })
        return list
    }
}
