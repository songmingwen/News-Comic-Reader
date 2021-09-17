package com.song.game.soaring.bean.wuxue;

import java.io.Serializable;

/**
 * Desc:    武学：分物理、法术两类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 17:20
 */
public class Wuxue implements Serializable {

    public String name;

    public String desc;

    public int level;

    /*** 物攻 */
    public float physicalAttack;

    /*** 法攻 */
    public float spellsAttack;

    /*** 物理命中要害概率 */
    public float physicalCriticalRate;

    /*** 法术命中要害概率 */
    public float spellsCriticalRate;

    /*** 命中率 */
    public float hitRate;
}
