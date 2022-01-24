package com.song.game.wuxia

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.song.sunset.R
import com.song.sunset.base.activity.BaseActivity

@Route(path = "/song/wuxia/characteristic")
class WuXiaCharacteristicActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wuxia_characteristic)
    }

}