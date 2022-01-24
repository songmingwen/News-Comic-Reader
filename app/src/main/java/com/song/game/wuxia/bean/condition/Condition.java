package com.song.game.wuxia.bean.condition;

import java.io.Serializable;

/**
 * Desc:    身体状态：buff 或者 debuff
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 17:42
 */
public class Condition implements Serializable {

    public String name;

    public String desc;

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
    public float fortuneIncreaseIncrease;
}
