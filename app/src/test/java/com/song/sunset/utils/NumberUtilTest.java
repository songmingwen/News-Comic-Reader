package com.song.sunset.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by songmw3 on 2016/12/14.
 */
public class NumberUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void convertToint() throws Exception {
        assertEquals(NumberUtil.convertToint("77", 0), 77);
    }

    @Test
    public void convertTolong() throws Exception {
        assertEquals(NumberUtil.convertTolong("77", 1L), 77L);
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