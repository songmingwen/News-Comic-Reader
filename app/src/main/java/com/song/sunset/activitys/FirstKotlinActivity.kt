package com.song.sunset.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import com.song.sunset.R
import com.song.sunset.utils.DateTimeUtils
import kotlinx.android.synthetic.main.activity_first.*


class FirstKotlinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        DateTimeUtils.CURRENT_TIME = DateTimeUtils.monthDayFormat.format(DateTimeUtils.now())

        val animation = AlphaAnimation(0f, 1f)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                animation.cancel()
                startMainActivity()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        animation.duration = 1000
        animation.start()
        idFirst.animation = animation
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainKotlinActivity::class.java))
        overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        Handler().postDelayed({ this@FirstKotlinActivity.finish() }, 200)
    }
}
