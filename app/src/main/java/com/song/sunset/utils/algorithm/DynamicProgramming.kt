package com.song.sunset.utils.algorithm

/**
 * Desc:    动态规划：Dynamic Programming
 *          动态规划四个关键组成部分
 *          1、确定状态：研究最优策略的最后一步，化为子问题；
 *          2、转移方程：根据子问题的定义直接得到；
 *          3、初始条件和边界情况；
 *          4、计算顺序：利用之前的计算结果；
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/6/23 17:51
 */
object DynamicProgramming {

    /**
     * 在给定的硬币币值数组中选取一定数量的银币，使其总金额等于给定的 target 值。
     * 各币值的数量无限制
     * @param coin 硬币币值数组如：{2，5，7}
     * @param target 目标金额
     */
    fun getMixCoin(coin: IntArray, target: Int): Int {
        val f = IntArray(target + 1)
        f[0] = 0

        for (i in 1..target) {
//            print("i = $i")
            f[i] = Int.MAX_VALUE

            coin.forEach { value ->
//                print(", value = $value")
                //前一个目标值
                val preTarget = i - value
                if (i >= value && f[preTarget] != Int.MAX_VALUE) {
                    f[i] = (f[preTarget] + 1).coerceAtMost(f[i])
//                    print(", f[$i]=" + f[i])
                }
            }

//            println()
        }
        if (f[target] == Int.MAX_VALUE) {
            f[target] = -1
        }
        return f[target]
    }

    /**
     * 购买总金额为 target 的商品时，有若干优惠券可以选择（单一优惠券数量无限且可以叠加使用）。
     * 找出最少应额外付款金额
     * @param coupon 优惠券数组如：{5，8}
     * @param target 商品总价
     * @return 额外应付款金额
     *
     * 额外付款 = 0
     * ...
     * 额外付款 =(最小优惠券 -1)
     *
     *
     *
     */
    fun getPayMin(coupon: IntArray, target: Int): Int {
        coupon.sort()
        //最多额外付款为 最小优惠券 -1
        val min = coupon.first() - 1
        for (index in 0..min) {
            if (getMixCoin(coupon, target - index) != -1) {
                return index
            }
        }
        return min
    }

}