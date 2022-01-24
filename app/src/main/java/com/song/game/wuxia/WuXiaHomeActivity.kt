package com.song.game.wuxia

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity
import kotlinx.android.synthetic.main.activity_wuxia.*

@Route(path = "/song/wuxia/home")
class WuXiaHomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wuxia)
        retry.setOnClickListener { showTalent() }
    }

    private fun showTalent() {
        ARouter.getInstance().build("/song/wuxia/talent").navigation()
    }

}