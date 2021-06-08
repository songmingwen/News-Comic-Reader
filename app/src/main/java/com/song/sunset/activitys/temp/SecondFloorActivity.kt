package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.activitys.ComicListActivity
import com.song.sunset.base.activity.BaseActivity
import com.song.sunset.fragments.SecondFloorFragment
import com.song.sunset.utils.ScreenUtils

class SecondFloorActivity : BaseActivity() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SecondFloorActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_floor)
        ScreenUtils.fullscreen(this, true)
        val bundle = Bundle()
        bundle.putString(ComicListActivity.ARG_NAME, "sort")
        bundle.putInt(ComicListActivity.ARG_VALUE, 23)
        loadFragment(this, R.id.content, SecondFloorFragment::class.java.name, bundle)
    }
}
