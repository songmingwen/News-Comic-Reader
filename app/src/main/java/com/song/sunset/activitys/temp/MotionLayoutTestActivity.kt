package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ScaleDrawable
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_motion_glee.glee_big_barrage_view
import kotlinx.android.synthetic.main.activity_motion_glee.one
import kotlinx.android.synthetic.main.activity_motion_glee.progress_bar
import kotlinx.android.synthetic.main.activity_motion_glee.rippleHide
import kotlinx.android.synthetic.main.activity_motion_glee.rippleShow
import kotlinx.android.synthetic.main.activity_motion_glee.three
import kotlinx.android.synthetic.main.activity_motion_glee.two


/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2024/5/10
 */

class MotionLayoutTestActivity : BaseActivity() {
    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MotionLayoutTestActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_glee)

        val drawable = progress_bar.progressDrawable

        if (drawable is LayerDrawable) {
            val progressDrawable = drawable.getDrawable(1)
            if (progressDrawable is ScaleDrawable) {
                progressDrawable.setTint(Color.BLUE)
            }
        }

        progress_bar.progress = 75

        one.setOnClickListener { glee_big_barrage_view.startFirstAnim() }
        two.setOnClickListener { glee_big_barrage_view.startSecondAnim() }
        three.setOnClickListener { glee_big_barrage_view.startThirdAnim() }
        rippleShow.setOnClickListener { glee_big_barrage_view.showRippleAnim() }
        rippleHide.setOnClickListener { glee_big_barrage_view.stopRippleAnim() }
    }
}