package com.song.sunset.activitys

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.song.sunset.R

/**
 * @author songmingwen
 * @since 2018/7/27
 */
class LearnKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        Log.e("", sum(10, 20).toString())

        var string = "abcde"
        string = string.replace("ab", "cd")
        Log.e("", string)

        Log.e("", max(10, 20).toString())

    }

    private fun sum(a: Int, b: Int): Int = a + b

    private fun max(a: Int,b:Int):Int = if (a>b) a else b

}