package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_motion_layout.*

class MotionLayoutActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MotionLayoutActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        id_1.setOnClickListener { Toast.makeText(this, "1", Toast.LENGTH_SHORT).show() }
        id_2.setOnClickListener { Toast.makeText(this, "2", Toast.LENGTH_SHORT).show() }
        id_3.setOnClickListener { Toast.makeText(this, "3", Toast.LENGTH_SHORT).show() }
        id_4.setOnClickListener { Toast.makeText(this, "4", Toast.LENGTH_SHORT).show() }
        id_5.setOnClickListener { Toast.makeText(this, "5", Toast.LENGTH_SHORT).show() }
        id_6.setOnClickListener { Toast.makeText(this, "6", Toast.LENGTH_SHORT).show() }
        id_7.setOnClickListener { Toast.makeText(this, "7", Toast.LENGTH_SHORT).show() }
        id_8.setOnClickListener { Toast.makeText(this, "8", Toast.LENGTH_SHORT).show() }
        id_9.setOnClickListener { Toast.makeText(this, "9", Toast.LENGTH_SHORT).show() }

        motion_layout.setTransitionListener(object : TransitionAdapter() {

            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                val enable = progress > 0
                id_7.isEnabled = enable
                id_8.isEnabled = enable
                id_9.isEnabled = enable
            }

        })
    }
}
