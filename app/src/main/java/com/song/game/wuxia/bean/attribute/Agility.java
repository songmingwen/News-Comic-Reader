package com.song.game.wuxia.bean.attribute;

import android.util.Log;

import com.song.game.wuxia.bean.character.Character;
import com.song.game.wuxia.bean.condition.Condition;

/**
 * Desc:    敏捷值
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/15 17:39
 */
public class Agility implements Attribute {

    @Override
    public float getAttribute(Character character) {
        float value = getBaseValue(character) * (1 + getRate(character));
        Log.i("wuxia_attr"+this.getClass().getSimpleName(), "value = " + value);
        return value;
    }

    /*** 获取敏捷值系数 */
    private float getRate(Character character) {
        float agilityRate = 0;
        if (character.talent != null) {
            agilityRate += character.talent.agilityIncrease;
        }
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                agilityRate += condition.agilityIncrease;
            }
        }
        return agilityRate;
    }

    /*** 获取敏捷值基础值 */
    private float getBaseValue(Character character) {
        float agility = 0;
        agility += character.ability.agility;
        if (character.equipment.shoe != null) {
            agility += character.equipment.shoe.agility;
        }
        return agility;
    }
}
