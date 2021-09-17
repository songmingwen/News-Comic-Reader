package com.song.game.soaring.bean.equipment;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Desc:    等级划分
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 15:50
 */
@IntDef({EquipmentLevel.WHITE, EquipmentLevel.GREEN, EquipmentLevel.BLUE,
        EquipmentLevel.PURPLE, EquipmentLevel.RED, EquipmentLevel.GOLD})
@Retention(RetentionPolicy.SOURCE)
public @interface EquipmentLevel {
    int WHITE = 1;
    int GREEN = 2;
    int BLUE = 3;
    int PURPLE = 4;
    int RED = 5;
    int GOLD = 6;
}
