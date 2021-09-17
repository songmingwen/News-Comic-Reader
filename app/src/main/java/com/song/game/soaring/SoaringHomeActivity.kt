package com.song.game.soaring

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_soaring.*

@Route(path = "/song/soaring/home")
class SoaringHomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soaring)
        retry.setOnClickListener { showTalent() }
    }

    private fun showTalent() {
        ARouter.getInstance().build("/song/soaring/talent").navigation()
    }

}