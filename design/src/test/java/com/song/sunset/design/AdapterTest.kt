package com.song.sunset.design

import com.song.sunset.design.structural.adapter.AudioPlayer
import com.song.sunset.design.structural.adapter.AudioPlayer.MP3
import com.song.sunset.design.structural.adapter.AviPlayer.AVI
import com.song.sunset.design.structural.adapter.Mp4Player.MP4
import org.junit.Test

/**
 * Desc:    适配器模式（Adapter Pattern）是作为两个不兼容的接口之间的桥梁。这种类型的设计模式属于
 *          结构型模式，它结合了两个独立接口的功能。这种模式涉及到一个单一的类，该类负责加入独立的
 *          或不兼容的接口功能。举个真实的例子，读卡器是作为内存卡和笔记本之间的适配器。您将内存卡
 *          插入读卡器，再将读卡器插入笔记本，这样就可以通过笔记本来读取内存卡。
 *
 *          使用场景：有动机地修改一个正常运行的系统的接口，这时应该考虑使用适配器模式。
 *          注意事项：适配器不是在详细设计时添加的，而是解决正在服役的项目的问题。
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/8 9:47
 */
class AdapterTest {

    @Test
    fun adapter() {
        val audioPlayer = AudioPlayer()
        audioPlayer.play(MP3, "mp3 文件")
        audioPlayer.play(MP4, "mp4 文件")
        audioPlayer.play(AVI, "avi 文件")
        audioPlayer.play("MKV", "MKV 文件")
    }
}