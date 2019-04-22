package com.song.sunset.activitys.temp

import android.app.Activity
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.song.sunset.R
import com.song.sunset.utils.ScreenUtils
import android.content.ComponentName
import android.os.Build
import android.view.View
import android.widget.SeekBar
import com.song.sunset.utils.JsonUtil
import com.song.sunset.utils.SPUtils
import com.song.sunset.utils.SPUtils.SP_NEURAL_NET_WORKS
import com.song.sunset.utils.SPUtils.SP_NEURAL_NET_WORKS_PREVIEW
import com.song.sunset.widget.neural.NeuralParams
import com.song.sunset.widget.neural.NeuralWallPaperService
import kotlinx.android.synthetic.main.activity_neural_net_works.*


class NeuralNetWorksActivity : Activity(), SeekBar.OnSeekBarChangeListener {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, NeuralNetWorksActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neural_net_works)
        ScreenUtils.fullscreen(this, true)

        sb_dot_amount.setOnSeekBarChangeListener(this)
        sb_dot_speed.setOnSeekBarChangeListener(this)
        sb_connection_threshold.setOnSeekBarChangeListener(this)
        sb_dot_radius.setOnSeekBarChangeListener(this)

    }

    fun addWallPaper(view: View) {
        val str = JsonUtil.gsonToString(NeuralParams(sb_connection_threshold.progress, sb_dot_amount.progress, sb_dot_speed.progress, sb_dot_radius.progress.toFloat()))
        SPUtils.setStringByName(this, SP_NEURAL_NET_WORKS_PREVIEW, str)

        try {
            val wallPaperIntent = Intent()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                wallPaperIntent.action = WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER
                wallPaperIntent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(applicationContext.packageName, NeuralWallPaperService::class.java.name))
            } else {
                wallPaperIntent.action = WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER
            }
            startActivityForResult(wallPaperIntent, 53520)
        } catch (localException: Exception) {
            localException.printStackTrace()
        }
    }

    fun setting(view: View) {
        ll_setting_layout.visibility = View.VISIBLE
        updateText(sb_dot_amount, sb_dot_amount.progress)
        updateText(sb_dot_speed, sb_dot_speed.progress)
        updateText(sb_connection_threshold, sb_connection_threshold.progress)
        updateText(sb_dot_radius, sb_dot_radius.progress)
    }

    fun confirm(view: View) {
        ll_setting_layout.visibility = View.GONE
    }

    private fun preView() {
        nnw.setElementAmount(sb_dot_amount.progress)
        nnw.setSpeed(sb_dot_speed.progress)
        nnw.setConnectionThreshold(sb_connection_threshold.progress)
        nnw.setRadius(sb_dot_radius.progress.toFloat())
        nnw.invalidateView()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (seekBar == null) {
            return
        }
        updateText(seekBar, progress)
    }

    private fun updateText(seekBar: SeekBar, progress: Int) {
        when (seekBar.id) {
            R.id.sb_dot_amount -> txt_dot_amount.text = "圆点数量：$progress"
            R.id.sb_dot_speed -> txt_dot_speed.text = "圆点速度：$progress"
            R.id.sb_connection_threshold -> txt_connection_threshold.text = "连线阀值：$progress"
            R.id.sb_dot_radius -> txt_dot_radius.text = "圆点半径：$progress"
        }
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        preView()
    }

    override fun onBackPressed() {
        if (ll_setting_layout.visibility == View.VISIBLE) {
            ll_setting_layout.visibility = View.GONE
            return
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 53520 && resultCode == RESULT_OK) {
            val str = JsonUtil.gsonToString(NeuralParams(sb_connection_threshold.progress, sb_dot_amount.progress, sb_dot_speed.progress, sb_dot_radius.progress.toFloat()))
            SPUtils.setStringByName(this, SP_NEURAL_NET_WORKS_PREVIEW, "")
            SPUtils.setStringByName(this, SP_NEURAL_NET_WORKS, str)
        }
    }
}
