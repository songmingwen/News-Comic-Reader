package com.song.game.soaring.bean.character;

import com.song.game.soaring.bean.ability.Ability;
import com.song.game.soaring.bean.attribute.Agility;
import com.song.game.soaring.bean.attribute.PhysicalAttack;
import com.song.game.soaring.bean.attribute.Attribute;
import com.song.game.soaring.bean.attribute.PhysicalDefence;
import com.song.game.soaring.bean.attribute.DodgeRate;
import com.song.game.soaring.bean.attribute.Health;
import com.song.game.soaring.bean.attribute.HitRate;
import com.song.game.soaring.bean.attribute.PhysicalCriticalRate;
import com.song.game.soaring.bean.attribute.Spells;
import com.song.game.soaring.bean.attribute.SpellsAttack;
import com.song.game.soaring.bean.attribute.SpellsCriticalRate;
import com.song.game.soaring.bean.attribute.SpellsDefence;
import com.song.game.soaring.bean.condition.Condition;
import com.song.game.soaring.bean.equipment.Equipment;
import com.song.game.soaring.bean.talent.Talent;

import java.io.Serializable;
import java.util.List;

/**
 * Desc:    人物
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 17:15
 */
public class Character implements Serializable {

    public String name;

    public String desc;

    /*** 当前血量 */
    public float currentHealth;

    /*** 当前精神力 */
    public float currentSpells;

    public Ability ability;

    public Talent talent;

    public Equipment equipment;

    public List<Condition> condition;

    /*** 获取最高血量 */
    public float getHealth(){
        return getAttribute(new Health());
    }

    /*** 获取最高精神力 */
    public float getSpells(){
        return getAttribute(new Spells());
    }

    /*** 获取物理攻击力 */
    public float getPhysicalAttack(){
        return getAttribute(new PhysicalAttack());
    }

    /*** 获取物理防御力 */
    public float getPhysicalDefence(){
        return getAttribute(new PhysicalDefence());
    }

    /*** 获取法术攻击力 */
    public float getSpellsAttack(){
        return getAttribute(new SpellsAttack());
    }

    /*** 获取法术防御力 */
    public float getSpellsDefence(){
        return getAttribute(new SpellsDefence());
    }

    /*** 获取敏捷值 */
    public float getAgility() {
        return getAttribute(new Agility());
    }

    /*** 获取命中率 */
    public float getHitRate() {
        return getAttribute(new HitRate());
    }

    /*** 获取物理暴击率 */
    public float getPhysicalCriticalRate() {
        return getAttribute(new PhysicalCriticalRate());
    }

    /*** 获取法术暴击率 */
    public float getSpellsCriticalRate() {
        return getAttribute(new SpellsCriticalRate());
    }

    /*** 获取闪避率 */
    public float getDodgeRate() {
        return getAttribute(new DodgeRate());
    }

    private float getAttribute(Attribute attribute) {
        return (float) attribute.getAttribute(this);
    }

}
