package com.song.sunset.design

import com.song.sunset.design.behavioral.command.ConcreteCommand
import com.song.sunset.design.behavioral.command.Invoker
import com.song.sunset.design.behavioral.command.Receiver
import com.song.sunset.design.behavioral.command.audioplayer.*
import org.junit.Test

/**
 * Desc:    优点： 1、降低了系统耦合度。 2、新的命令可以很容易添加到系统中去。
 *          缺点：使用命令模式可能会导致某些系统有过多的具体命令类。
 *          使用场景：认为是命令的地方都可以使用命令模式，比如： 1、GUI 中每一个按钮都是一条命令。 2、模拟 CMD。
 *          注意事项：系统需要支持命令的撤销(Undo)操作和恢复(Redo)操作，也可以考虑使用命令模式，见命令模式的扩展。

命令模式结构示意图:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/5/13 10:20
 */
class CommandTest {

    @Test
    fun command() {
        println("---------------------")
        val receiver = Receiver()
        val command = ConcreteCommand(receiver)
        val invoker = Invoker(command)
        invoker.action()
    }

    @Test
    fun audioPlayer() {
        println("---------------------")
        val audioPlayer = AudioPlayer("爱在西元前")
        val playCommand = PlayCommand(audioPlayer)
        val rewindCommand = RewindCommand(audioPlayer)
        val stopCommand = StopCommand(audioPlayer)
        val keypad = Keypad(playCommand, rewindCommand, stopCommand)
        keypad.play()
        keypad.rewind()
        keypad.stop()
    }

    @Test
    fun macroCommand() {
        println("下面是自动倒带并播放的宏命令")
        val macroSaveControlCommand = MacroSaveControlCommand()
        val audioPlayer = AudioPlayer("七里香")
        val playCommand = PlayCommand(audioPlayer)
        val rewindCommand = RewindCommand(audioPlayer)
        val stopCommand = StopCommand(audioPlayer)
        macroSaveControlCommand.add(rewindCommand)
        macroSaveControlCommand.add(stopCommand)
        macroSaveControlCommand.add(playCommand)
        macroSaveControlCommand.execute()
    }
}