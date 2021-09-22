package com.song.game.soaring.bean.attribute;

import android.util.Log;

import com.song.game.soaring.bean.character.Character;
import com.song.game.soaring.bean.condition.Condition;

/**
 * Desc:    防御
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/15 19:31
 */
public class PhysicalDefence implements Attribute {

    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
        Log.i("soaring_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getBaseValue(Character character) {
        float defence = 0;
        defence += character.ability.physicalDefensed;
        if (character.equipment != null) {
            if (character.equipment.coat != null) {
                defence += character.equipment.coat.physicalDefensed;
            }
            if (character.equipment.pants != null) {
                defence += character.equipment.pants.physicalDefensed;
            }
            if (character.equipment.head != null) {
                defence += character.equipment.head.physicalDefensed;
            }
            if (character.equipment.shoe != null) {
                defence += character.equipment.shoe.physicalDefensed;
            }
            if (character.equipment.gloves != null) {
                defence += character.equipment.gloves.physicalDefensed;
            }
        }
        return defence;
    }

    private float getRate(Character character) {
        float rate = character.talent.physicalDefensedIncrease;
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                rate += condition.physicalDefensedIncrease;
            }
        }
        return rate;
    }
}
