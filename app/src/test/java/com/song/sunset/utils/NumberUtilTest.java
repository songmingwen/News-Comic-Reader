package com.song.sunset.utils;

import com.kotlin.LearnKotlin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Song on 2016/12/14.
 */
public class NumberUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void convertToint() throws Exception {
        List<String> ls = new ArrayList<>();
        List<Integer> li = new ArrayList<>();
        System.out.println(ls.getClass() == li.getClass());
    }

    @Test
    public void convertTolong() throws Exception {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue;
                }
                System.out.println("1-j=" + j);
            }
        }
        w:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    continue w;
                }
                System.out.println("2--j=" + j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    break;
                }
                System.out.println("3---j=" + j);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2) {
                    return;
                }
                System.out.println("4----j=" + j);
            }
        }
    }

    @Test
    public void convertTofloat() throws Exception {
        assertEquals(NumberUtil.convertTofloat("77", 0F), 77F, 0);
    }

    @Test
    public void convertTodouble() throws Exception {
        assertEquals(NumberUtil.convertTodouble("77", 0D), 77D, 0);
    }

    @Test
    public void convertToInteger() throws Exception {
        assertEquals(NumberUtil.convertToInteger("77"), Integer.valueOf(77));
    }

    @Test
    public void convertToLong() throws Exception {
        assertEquals(NumberUtil.convertToLong("77"), Long.valueOf(77));
    }

    @Test
    public void convertToFloat() throws Exception {
        assertEquals(NumberUtil.convertToFloat("77"), Float.valueOf(77));
    }

    @Test
    public void convertToDouble() throws Exception {
        assertEquals(NumberUtil.convertToDouble("77"), Double.valueOf(77));
    }

}