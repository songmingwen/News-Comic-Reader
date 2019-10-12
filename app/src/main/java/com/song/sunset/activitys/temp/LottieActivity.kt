package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import kotlinx.android.synthetic.main.activity_lottie.*

class LottieActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LottieActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        lottie.playAnimation()
    }
}
