package com.song.sunset.utils;


import java.util.Arrays;

/**
 * @author Song
 * @description 测试算法：自然数数组元素重新排序，使数组的偶数在奇数的前面
 */
public class Test {

    @org.junit.Test
    public void test() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        trans(a);
        System.out.print(Arrays.toString(a));//结果{2,4,6,1,3,5,7},不要求顺序{6,2,4,7,5,3,1}也可以。
    }

    private void trans(int[] array) {
        // TODO 此处答题
    }
}