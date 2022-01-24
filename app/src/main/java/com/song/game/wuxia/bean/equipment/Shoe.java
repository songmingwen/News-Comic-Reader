package com.song.game.wuxia.bean.equipment;

import com.song.game.wuxia.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:08
 */
public class Shoe implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalDefensed;

    public float spellsDefensed;

    /*** 敏捷 */
    public float agility;
}
