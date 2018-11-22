package com.song.sunset.utils;


import java.util.Arrays;

/**
 * Created by Song.
 */
public class Test {

    @org.junit.Test
    public void test() {
        int[] a = {1, 2, 3, 4, 5, 6, 7};
        trans(a);
        System.out.print(Arrays.toString(a));//结果{2,4,6,1,3,5,7}
    }

    private void trans(int[] a) {
        // TODO 此处答题

    }
}