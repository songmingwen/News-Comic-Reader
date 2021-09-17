package com.song.game.soaring

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity

@Route(path = "/song/soaring/life")
class SoaringLifeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soaring_life)

    }

}