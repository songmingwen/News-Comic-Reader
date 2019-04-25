package com.song.sunset.utils.preinstall

import android.text.TextUtils

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
class HuaweiPreinstallHandler : PreinstallHandler {

    private var mNextPreinstallHandler: PreinstallHandler? = null

    override val preinstallInfo: String
        get() {
            val info = huaweiPreinstallSystemInfo
            return if (!TextUtils.isEmpty(info)) {
                info
            } else {
                if (mNextPreinstallHandler != null) {
                    mNextPreinstallHandler!!.preinstallInfo
                } else {
                    ""
                }
            }
        }

    private val huaweiPreinstallSystemInfo: String
        get() {
            try {
                val clazz = Class.forName("android.os.SystemProperties")
                val method = clazz.getDeclaredMethod("get", String::class.java, String::class.java)
                return method.invoke(clazz, HUAWEI_PREINSTALL_KEY, "") as String
            } catch (e: Exception) {
            }

            return ""
        }

    override fun setNextHandler(preinstallHandler: PreinstallHandler) {
        mNextPreinstallHandler = preinstallHandler
    }

    companion object {

        const val HUAWEI_PREINSTALL_KEY = "ro.huawei.channel.zhihu"//华为预装约定的key值不得超过30字节，value目前是huawei_preinstall。value要小于60字节
    }
}
