package com.song.game.wuxia.bean.attribute;

import android.util.Log;

import com.song.game.wuxia.bean.character.Character;

/**
 * Desc:    血量
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/16 9:13
 */
public class Health implements Attribute {
    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
        Log.i("wuxia_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    private float getBaseValue(Character character) {
        return character.ability.health;
    }

    private float getRate(Character character) {
        return character.talent.healthIncrease;
    }
}
