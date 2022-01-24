package com.song.game.wuxia.utils

import android.util.Log
import com.song.game.wuxia.bean.character.Character
import com.song.game.wuxia.bean.wuxue.Wuxue
import kotlin.random.Random

/**
 * Desc:    伤害计算工具
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 17:18
 */
object DamageUtil {

    fun getDamage(attack: Character, defence: Character, wuxue: Wuxue?,
                  isPhysicalCritical: Boolean, isSpellsCritical: Boolean): Int {

        //命中的话再计算伤害,分为物理伤害、法术伤害
        val physAtt = attack.physicalAttack + (wuxue?.physicalAttack ?: 0f)
        val phyDef = defence.physicalDefence
        Log.i("wuxia_attr", "物理伤害值：$physAtt")
        Log.i("wuxia_attr", "物理防御值：$phyDef")
        val speAtt = attack.spellsAttack + (wuxue?.spellsAttack ?: 0f)
        val speDef = defence.spellsDefence
        var phyDam = physAtt - phyDef
        var speDam = speAtt - speDef
        if (isPhysicalCritical) {
            Log.i("wuxia_attr", "物理命中要害")
            phyDam *= 2.5f
        }
        if (isSpellsCritical) {
            speDam *= 2.5f
        }
        Log.i("wuxia_attr", "物理实际伤害：$phyDam")
        phyDam = if (phyDam < 0) 0F else phyDam
        speDam = if (speDam < 0) 0F else speDam

        //为了使伤害具有浮动性，最终伤害上下浮动 10%
        val finalRate = (1 + (Random.nextFloat() - 0.5f) / 5)
        Log.i("wuxia_attr", "物理实际伤害浮动：$finalRate")
        return ((phyDam + speDam) * finalRate).toInt()
    }

    fun isHit(attack: Character, defence: Character, wuxue: Wuxue?): Boolean {
        val attackAgility = attack.agility
        val defenceAgility = defence.agility
        val agilityRate = attackAgility / defenceAgility
        var realHitRate = agilityRate * attack.hitRate - defence.dodgeRate
        realHitRate += wuxue?.hitRate ?: 0f
        Log.i("wuxia_attr", "最终命中率：$realHitRate")
        return (realHitRate - Random.nextFloat()) > 0
    }

    fun isPhysicalCritical(attack: Character, defence: Character, wuxue: Wuxue?): Boolean {
        var criticalRate = attack.physicalCriticalRate
        criticalRate += wuxue?.physicalCriticalRate ?: 0f
        Log.i("wuxia_attr", "物理暴击率：$criticalRate")
        return (criticalRate - Random.nextFloat()) > 0
    }

    fun isSpellsCritical(attack: Character, defence: Character, wuxue: Wuxue?): Boolean {
        var criticalRate = attack.spellsCriticalRate
        criticalRate += wuxue?.spellsCriticalRate ?: 0f
        return (criticalRate - Random.nextFloat()) > 0
    }
}