package com.song.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.song.sunset.R
import java.util.ArrayList

/**
 * @author songmingwen
 * @since 2018/7/27
 */
class LearnKotlin : AppCompatActivity() {

    lateinit var late: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        late = ""

        Log.e("", sum(10, 20).toString())

        var string = "abcde"
        string = string.replace("ab", "cd")
        Log.e("", string)

        Log.e("", max(10, 20).toString())

        val x = sum(1, 2)
        when (x) {
            0 -> println("x==0")
            1 -> println("x==1")
            sum(1, 2) -> println("x==3")
            in 1..10 -> println("between 1 and 10")
            is Int -> println("int")
            else -> println("x==else")
        }

        val list = ArrayList<Int>()
        list.add(2)
        list.add(3)
        list.add(4)
        list.add(5)
        list.add(6)
        list.add(7)
        list.add(8)
        for (it in list) {
            println(it)
        }
        for (it in 1..7) {
            println(it)
        }
        for (index in list.indices) {
            println(list[index])
        }
        for ((index, value) in list.withIndex()) {
            println("index=$index,value=$value")
        }

        var classA = ClassA(1)

    }

    private fun sum(a: Int, b: Int): Int = a + b

    private fun max(a: Int, b: Int): Int = if (a > b) a else b

    companion object {
        fun checkType(x: Any) = when (x) {
            is String -> println("string")
            is Int -> println("int")
            is Boolean -> println("boolean")
            is Long -> println("long")
            else -> println("else") //作为表达式必须有 else 分支，作为语句，下面（checkType2）函数中 else 可以省略
        }

        fun checkType2(x: Any) {
            when (x) {
                is String -> println("string")
                is Int -> println("int")
                is Boolean -> println("boolean")
                is Long -> println("long")
            }
        }
    }
}