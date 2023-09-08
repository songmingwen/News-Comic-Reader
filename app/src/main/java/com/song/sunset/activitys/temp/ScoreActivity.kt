package com.song.sunset.activitys.temp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.song.sunset.R
import com.sunset.room.RoomActivity
import kotlinx.android.synthetic.main.activity_score.*
import java.lang.Exception

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/9/8 10:27
 */
class ScoreActivity : AppCompatActivity() {

    companion object {

        const val TAG = "ScoreActivity"

        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ScoreActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)


        Log.i("score", "最终得分 = ${
            getLowToMoreScore(
                    0.97f,
                    0.5f, 1.1f,
                    8.5f, 9.5f)
        }")

        start.setOnClickListener { calculate() }
    }

    private fun calculate() {
        val value1 = value1.text.toString()
        val score1 = score1.text.toString()
        val value2 = value2.text.toString()
        val score2 = score2.text.toString()
        val realValue = real_value.text.toString()
        if (TextUtils.isEmpty(value1) || TextUtils.isEmpty(score1) || TextUtils.isEmpty(value2)
                || TextUtils.isEmpty(score2) || TextUtils.isEmpty(realValue)) {
            Toast.makeText(this, "请检查数值", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            val value1Float = value1.toFloat()
            val score1Float = score1.toFloat()
            val value2Float = value2.toFloat()
            val score2Float = score2.toFloat()
            val realValueFloat = realValue.toFloat()

            if (((value1Float > value2Float) && (score1Float > score2Float)) ||
                    ((value1Float < value2Float) && (score1Float < score2Float))) {
                score.text = getMoreToMoreScore(
                        realValueFloat,
                        value1Float.coerceAtMost(value2Float), value1Float.coerceAtLeast(value2Float),
                        score1Float.coerceAtMost(score2Float), score1Float.coerceAtLeast(score2Float)).toString()
            } else {
                score.text = getLowToMoreScore(
                        realValueFloat,
                        value1Float.coerceAtMost(value2Float), value1Float.coerceAtLeast(value2Float),
                        score1Float.coerceAtMost(score2Float), score1Float.coerceAtLeast(score2Float)).toString()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "请检查数值", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 值越小分数越高
     * < 0.5s	        9.5≤得分
     * = 0.5s-1.1s	    8.5≤得分<9.5
     * = 1.1s-5s	    7.5≤得分<8.5
     * = 5s-7s	        6.5≤得分<7.5
     * > 7s	            得分<6.5
     */
    private fun getLowToMoreScore(currentValue: Float,
                                  lowValue: Float, heightValue: Float,
                                  lowScore: Float, heightScore: Float): Float {
        if (currentValue < lowValue || currentValue > heightValue) {
            return -1f
        }

        val distanceValue = heightValue - lowValue
        val moreValue = currentValue - lowValue
        val distanceScore = heightScore - lowScore
        return heightScore - ((moreValue / distanceValue) * distanceScore)
    }

    /**
     * 值越大分数越高
     * > 6s	        9.5≤得分
     * = 4s-5s	    8.5≤得分<9.5
     * = 3s-4s	    7.5≤得分<8.5
     * = 2s-3s      6.5≤得分<7.5
     * < 1s         得分<6.5
     */
    private fun getMoreToMoreScore(currentValue: Float,
                                   lowValue: Float, heightValue: Float,
                                   lowScore: Float, heightScore: Float): Float {
        if (currentValue < lowValue || currentValue > heightValue) {
            return -1f
        }

        val distanceValue = heightValue - lowValue
        val moreValue = currentValue - lowValue
        val distanceScore = heightScore - lowScore
        return ((moreValue / distanceValue) * distanceScore) + lowScore
    }

}