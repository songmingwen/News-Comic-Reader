package com.song.game.wuxia.bean.attribute;

import android.util.Log;

import com.song.game.wuxia.bean.character.Character;
import com.song.game.wuxia.bean.condition.Condition;

/**
 * Desc:    闪避
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/15 17:54
 */
public class DodgeRate implements Attribute {

    @Override
    public float getAttribute(Character character) {
        float rate = 0;
        rate += character.ability.dodgeRate;
        if (character.talent != null) {
            rate += character.talent.dodgeRateIncrease;
        }
        if (character.condition != null && !character.condition.isEmpty()) {
            for (Condition condition : character.condition) {
                rate += condition.dodgeRateIncrease;
            }
        }

        Log.i("wuxia_attr"+this.getClass().getSimpleName(), "value = " + rate);
        return rate;
    }

}
