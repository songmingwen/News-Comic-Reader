package com.song.game.soaring.bean.talent;

import java.io.Serializable;

/**
 * Desc:    天赋：一项或多项能力加强
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/10 16:47
 */
public class Talent implements Serializable {

    public boolean selected;

    public String name;

    public String desc;

    public int level;

    /*** 血量加强 */
    public float healthIncrease;

    /*** 精神力加强 */
    public float spellsIncrease;

    /*** 物攻加强 */
    public float physicalAttackIncrease;

    /*** 法攻加强 */
    public float spellsAttackIncrease;

    /*** 敏捷加强 */
    public float agilityIncrease;

    /*** 物理防御加强 */
    public float physicalDefensedIncrease;

    /*** 法术防御加强 */
    public float spellsDefensedIncrease;

    /*** 物理命中要害概率加强 */
    public float physicalCriticalRateIncrease;

    /*** 法术命中要害概率加强 */
    public float spellsCriticalRateIncrease;

    /*** 血量恢复率加强 */
    public float healthRecoverRateIncrease;

    /*** 精神力恢复率加强 */
    public float spellsRecoverRateIncrease;

    /*** 命中率 */
    public float hitRateIncrease;

    /*** 闪避率 */
    public float dodgeRateIncrease;

    /*** 气运加强 */
    public float fortuneIncrease;

}
