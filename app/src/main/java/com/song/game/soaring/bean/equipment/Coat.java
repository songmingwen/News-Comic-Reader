package com.song.game.soaring.bean.equipment;

import com.song.game.soaring.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    上衣
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:07
 */
public class Coat implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalDefensed;

    public float spellsDefensed;

    /*** 血量恢复率 */
    public float healthRecoverRate;
}
