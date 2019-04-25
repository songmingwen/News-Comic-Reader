package com.song.kotlin.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.util.Log
import com.song.sunset.R
import com.song.sunset.activitys.base.BaseActivity
import com.song.sunset.activitys.temp.FrescoProcessorActivity
import com.song.sunset.activitys.temp.FrescoXMLActivity
import com.song.sunset.activitys.temp.FunctionListActivity
import com.song.sunset.beans.MusicInfo
import com.song.sunset.services.impl.BinderPoolImpl
import com.song.sunset.services.impl.MusicCallBackListenerImpl
import com.song.sunset.services.impl.MusicGetterImpl
import com.song.sunset.services.managers.BinderPool
import com.song.sunset.utils.preinstall.DefaultPreinstallHandler
import com.song.sunset.utils.preinstall.HuaweiPreinstallHandler
import com.song.sunset.utils.preinstall.VivoPreinstallHandler
import com.song.sunset.utils.preinstall.XiaomiPreinstallHandler
import com.song.sunset.widget.fireworks.BitmapProvider
import kotlinx.android.synthetic.main.activity_function_list.*

class FunctionListKotlinActivity : BaseActivity() {

    companion object {
        private val TAG = FunctionListActivity::class.java.name
    }

    fun start(context: Context) {
        val starter = Intent(context, FunctionListKotlinActivity::class.java)
        context.startActivity(starter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_list)
        show_fresco_xml.setOnClickListener { showFrescoXML() }
        show_fresco_processor.setOnClickListener { showFrescoProcessor() }
        show_fireworks.setOnClickListener { showFireWorks() }
    }

    private fun showFrescoXML() {
        FrescoXMLActivity.start(this)
    }

    private fun showFrescoProcessor() {
        FrescoProcessorActivity.start(this)
    }

    private fun showFireWorks() {
        fireworks_layout.provider = getFireworksProvider()
        if (fireworks_layout == null) {
            return
        }
        val position = IntArray(2)
        fireworks_layout.getLocationOnScreen(position)
        val x: Int = position[0] + fireworks_layout.width / 2
        val y: Int = position[1]
        fireworks_layout.launch(x, y)
    }

    private fun getFireworksProvider(): BitmapProvider.Provider {
        return BitmapProvider.Builder(this)
                .setDrawableArray(
                        intArrayOf(R.drawable.fireworks_emoji001, R.drawable.fireworks_emoji002, R.drawable.fireworks_emoji003,
                                R.drawable.fireworks_emoji004, R.drawable.fireworks_emoji005, R.drawable.fireworks_emoji006,
                                R.drawable.fireworks_emoji007, R.drawable.fireworks_emoji008, R.drawable.fireworks_emoji009,
                                R.drawable.fireworks_emoji010, R.drawable.fireworks_emoji011, R.drawable.fireworks_emoji012,
                                R.drawable.fireworks_emoji013, R.drawable.fireworks_emoji014, R.drawable.fireworks_emoji015,
                                R.drawable.fireworks_emoji016, R.drawable.fireworks_emoji017, R.drawable.fireworks_emoji018,
                                R.drawable.fireworks_emoji019, R.drawable.fireworks_emoji020, R.drawable.fireworks_emoji021))
                .build()
    }

    private val mIMusicCallBackListener = object : MusicCallBackListenerImpl() {
        @Throws(RemoteException::class)
        override fun success(list: List<MusicInfo>) {
            super.success(list)
            Log.i(MusicCallBackListenerImpl.TAG + "MainActivity", list.toString())
        }

        @Throws(RemoteException::class)
        override fun failure() {
            Log.i(MusicCallBackListenerImpl.TAG + "MainActivity", "get music failure")
        }
    }

    private fun useBinderPool() {
        val iBinder = BinderPool.getInstance().queryBinder(BinderPoolImpl.BINDER_GET_MUSIC)
        val iMusicGetter = MusicGetterImpl.asInterface(iBinder)
        try {
            iMusicGetter.getMusicList(mIMusicCallBackListener)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private fun testChain() {
        val huawei = HuaweiPreinstallHandler()
        val xiaomi = XiaomiPreinstallHandler()
        val vivo = VivoPreinstallHandler()
        val default = DefaultPreinstallHandler()
        huawei.setNextHandler(xiaomi)
        xiaomi.setNextHandler(vivo)
        vivo.setNextHandler(default)
        Log.e(TAG, huawei.preinstallInfo)
    }

}
