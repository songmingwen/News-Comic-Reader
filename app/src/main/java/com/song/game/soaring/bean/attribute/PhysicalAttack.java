package com.song.game.soaring.bean.attribute;

import android.util.Log;

import com.song.game.soaring.bean.character.Character;
import com.song.game.soaring.bean.condition.Condition;

/**
 * Desc:    攻击
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/15 19:31
 */
public class PhysicalAttack implements Attribute {

    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
        Log.i("soaring_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getRate(Character character) {
        int rate = 0;
        rate += character.talent.physicalAttackIncrease;
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                rate += condition.physicalAttackIncrease;
            }
        }
        return rate;
    }

    private float getBaseValue(Character character) {
        float value = character.ability.physicalAttack;
        if (character.equipment != null && character.equipment.weapon != null) {
            value += character.equipment.weapon.physicalAttack;
        }
        return value;
    }
}
