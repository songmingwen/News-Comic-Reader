package com.song.game.wuxia.bean.attribute;

import com.song.game.wuxia.bean.character.Character;
import com.song.game.wuxia.bean.condition.Condition;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/16 11:10
 */
public class SpellsDefence implements Attribute{
    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
//        Log.i("wuxia_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getBaseValue(Character character) {
        float defence = 0;
        defence += character.ability.spellsDefensed;
        if (character.equipment != null) {
            if (character.equipment.coat != null) {
                defence += character.equipment.coat.spellsDefensed;
            }
            if (character.equipment.pants != null) {
                defence += character.equipment.pants.spellsDefensed;
            }
            if (character.equipment.head != null) {
                defence += character.equipment.head.spellsDefensed;
            }
            if (character.equipment.shoe != null) {
                defence += character.equipment.shoe.spellsDefensed;
            }
            if (character.equipment.gloves != null) {
                defence += character.equipment.gloves.spellsDefensed;
            }
        }
        return defence;
    }

    private float getRate(Character character) {
        float rate = character.talent.spellsDefensedIncrease;
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                rate += condition.spellsDefensedIncrease;
            }
        }
        return rate;
    }
}
