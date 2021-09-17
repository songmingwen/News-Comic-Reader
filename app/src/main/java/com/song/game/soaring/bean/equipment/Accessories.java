package com.song.game.soaring.bean.equipment;

import com.song.game.soaring.bean.pack.Pack;

import java.io.Serializable;

/**
 * Desc:    饰品
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/13 16:31
 */
public class Accessories implements Serializable, Pack {

    public String name;

    public String desc;

    public int level;

    public float fortune;

    /*** 精神力恢复率 */
    public float spellsRecoverRate;

    /*** 法术暴击率 */
    public float spellsCriticalRate;
}
