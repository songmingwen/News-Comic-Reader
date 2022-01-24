package com.song.game.wuxia.bean.attribute;

import com.song.game.wuxia.bean.character.Character;

/**
 * Desc:    精神力
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/16 10:48
 */
public class Spells implements Attribute {
    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
//        Log.i("wuxia_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getBaseValue(Character character) {
        return character.ability.spells;
    }

    private float getRate(Character character) {
        return character.talent.spellsIncrease;
    }
}
