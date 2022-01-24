package com.song.game.wuxia.bean.wuxue;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc:    等级划分
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 15:50
 */
@IntDef({WuxueLevel.WHITE, WuxueLevel.GREEN, WuxueLevel.BLUE,
        WuxueLevel.PURPLE, WuxueLevel.RED, WuxueLevel.GOLD})
@Retention(RetentionPolicy.SOURCE)
public @interface WuxueLevel {
    int WHITE = 1;
    int GREEN = 2;
    int BLUE = 3;
    int PURPLE = 4;
    int RED = 5;
    int GOLD = 6;
}
