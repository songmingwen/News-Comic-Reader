package com.song.game.soaring.bean.equipment;

import com.song.game.soaring.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    裤子
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:08
 */
public class Pants implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalDefensed;

    public float spellsDefensed;

    /*** 血量恢复率 */
    public float healthRecoverRate;
}
