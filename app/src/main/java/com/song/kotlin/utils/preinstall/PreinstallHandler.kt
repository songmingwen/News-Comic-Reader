package com.song.kotlin.utils.preinstall

/**
 * @author songmingwen
 * @description
 * @since 2018/10/9
 */
interface PreinstallHandler {

    val preinstallInfo: String

    fun setNextHandler(preinstallHandler: PreinstallHandler)
}
