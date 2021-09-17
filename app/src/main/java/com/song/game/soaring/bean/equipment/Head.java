package com.song.game.soaring.bean.equipment;

import com.song.game.soaring.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    头部
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:02
 */
public class Head implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float physicalDefensed;

    public float spellsDefensed;

    /*** 精神力恢复率 */
    public float spellsRecoverRate;
}
