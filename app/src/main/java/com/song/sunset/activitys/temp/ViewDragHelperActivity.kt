package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_lottie.*

class ViewDragHelperActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ViewDragHelperActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_drag_helper)
    }
}
