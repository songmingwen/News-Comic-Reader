package com.song.game.wuxia.bean.equipment;

import com.song.game.wuxia.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    武器
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 15:45
 */
public class Weapon implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalAttack;

    public float spellsAttack;

    /*** 物理暴击率 */
    public float physicalCriticalRate;

    /*** 法术暴击率 */
    public float spellsCriticalRate;

    /*** 命中率 */
    public float hitRate;
}
