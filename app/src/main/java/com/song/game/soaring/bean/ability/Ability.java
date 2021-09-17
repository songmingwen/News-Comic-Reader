package com.song.game.soaring.bean.ability;

import java.io.Serializable;

/**
 * Desc:    个人能力,不包含装备加成
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 11:30
 */
public class Ability implements Serializable {

    /*** 血量 */
    public float health;

    /*** 精神力 */
    public float spells;

    /*** 物攻 */
    public float physicalAttack;

    /*** 法攻 */
    public float spellsAttack;

    /*** 敏捷 */
    public float agility;

    /*** 物理防御 */
    public float physicalDefensed;

    /*** 法术防御 */
    public float spellsDefensed;

    /*** 物理命中要害概率 */
    public float physicalCriticalRate;

    /*** 法术命中要害概率 */
    public float spellsCriticalRate;

    /*** 血量恢复率 */
    public float healthRecoverRate;

    /*** 精神力恢复率 */
    public float spellsRecoverRate;

    /*** 命中率 */
    public float hitRate;

    /*** 闪避率 */
    public float dodgeRate;

    /*** 气运 */
    public float fortune;

}
