package com.song.game.soaring.bean.attribute;

import android.util.Log;

import com.song.game.soaring.bean.character.Character;
import com.song.game.soaring.bean.condition.Condition;

/**
 * Desc:    物理暴击
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/16 10:54
 */
public class PhysicalCriticalRate implements Attribute {
    @Override
    public float getAttribute(Character character) {
        float rate = character.talent.physicalCriticalRateIncrease;
        if (character.condition != null) {
            for (Condition cond : character.condition) {
                rate += cond.physicalCriticalRateIncrease;
            }
        }
        if (character.equipment != null && character.equipment.weapon != null) {
            rate += character.equipment.weapon.physicalCriticalRate;
        }
        Log.i("soaring_attr"+this.getClass().getSimpleName(), "value = " + rate);
        return rate;
    }
}