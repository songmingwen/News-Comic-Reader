package com.song.game.soaring.bean.equipment;

import com.song.game.soaring.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    手套
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:10
 */
public class Gloves implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalDefensed;

    public float spellsDefensed;

    /*** 物理暴击率 */
    public float physicalCriticalRate;
}
