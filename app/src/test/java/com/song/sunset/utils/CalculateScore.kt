package com.song.sunset.utils

import org.junit.Test

/**
 * Created by Song.
 */
class CalculateScore {

    /**
     * 值越小分数越高
     */
    @Test
    fun printLowToMoreScore() {
        print("最终得分 = ${
            getLowToMoreScore(
                    1.65f,
                    1.1f, 5f,
                    7.5f, 8.5f)
        }")
    }

    /**
     * 值越大分数越高
     */
    @Test
    fun printMoreToMoreScore() {
        print("最终得分 = ${
            getMoreToMoreScore(
                    2.95f,
                    2f, 3f,
                    7.5f, 8.5f)
        }")
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