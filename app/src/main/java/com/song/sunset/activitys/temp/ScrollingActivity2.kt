package com.song.sunset.activitys.temp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginStart
import com.google.android.material.appbar.AppBarLayout
import com.song.sunset.R
import com.song.sunset.utils.ViewUtil
import kotlinx.android.synthetic.main.activity_scrolling2.*

class ScrollingActivity2 : AppCompatActivity() {

    companion object {
        fun start(activity: Activity) {
            activity.startActivity(Intent(activity, ScrollingActivity2::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling2)
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            motion_layout.progress = -verticalOffset * 1.0F / 400
        })

    }
}