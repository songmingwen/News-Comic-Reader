package com.song.game.soaring.bean.attribute;

import com.song.game.soaring.bean.character.Character;
import com.song.game.soaring.bean.condition.Condition;

/**
 * Desc:    SpellsAttack
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/16 11:10
 */
public class SpellsAttack implements Attribute {

    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
//        Log.i("soaring_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getRate(Character character) {
        float rate = 0;
        rate += character.talent.spellsAttackIncrease;
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                rate += condition.spellsAttackIncrease;
            }
        }
        return rate;
    }

    private float getBaseValue(Character character) {
        float value = character.ability.spellsAttack;
        if (character.equipment != null && character.equipment.weapon != null) {
            value += character.equipment.weapon.spellsAttack;
        }
        return value;
    }
}
