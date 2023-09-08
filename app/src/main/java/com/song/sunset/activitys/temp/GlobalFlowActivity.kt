package com.song.sunset.activitys.temp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import com.song.sunset.R
import com.song.sunset.addButton
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.widget.GlobalFlowView
import com.tencent.mmkv.MMKV
import kotlinx.android.synthetic.main.activity_global_flow.*

class GlobalFlowActivity : BaseActivity() {

    companion object {
        const val TAG = "GlobalFlowActivity"

        fun start(context: Context) {
            context.startActivity(Intent(context, GlobalFlowActivity::class.java))
        }

        fun showGlobalFlowView(activity: Activity) {
            val globalFlowView = GlobalFlowView(activity)

            val decorView = activity.window.decorView
            val contentView = decorView.findViewById<ViewGroup>(android.R.id.content)

            for (index in 0 until contentView.childCount) {
                if (index != 0) {
                    contentView.removeViewAt(index)
                }
            }

            contentView.addView(globalFlowView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT))

            contentView.post { globalFlowView.initPosition() }
        }

        fun hideView(activity: Activity) {
            val decorView = activity.window.decorView
            val contentView = decorView.findViewById<ViewGroup>(android.R.id.content)

            for (index in 0 until contentView.childCount) {
                if (contentView.getChildAt(index) is GlobalFlowView) {
                    contentView.removeViewAt(index)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_flow)

        ll_function_container.apply {
            addButtonList()
        }
        Log.i("A -> B", "B : onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("A -> B", "B : onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("A -> B", "B : onResume")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("A -> B", "B : onRestart")
    }

    override fun onPause() {
        super.onPause()
        Log.i("A -> B", "B : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("A -> B", "B : onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("A -> B", "B : onDestroy")
    }

    private fun LinearLayout.addButtonList() {
        addButton("显示浮层") {
            val store = MMKV.defaultMMKV()
            store.putBoolean("show_global_flow", true)
            showGlobalFlowView(this@GlobalFlowActivity)
        }
        addButton("隐藏浮层") {
            val store = MMKV.defaultMMKV()
            store.putBoolean("show_global_flow", false)
            hideView(this@GlobalFlowActivity)
        }
        addButton("dialog") {
            AlertDialog.Builder(this@GlobalFlowActivity)
                    .setTitle("标题")
                    .setMessage("详情")
                    .create().show()
        }
    }

}
