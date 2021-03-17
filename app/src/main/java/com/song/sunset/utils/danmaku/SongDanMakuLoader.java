package com.song.sunset.utils.danmaku;

import java.io.InputStream;

import master.flame.danmaku.danmaku.loader.ILoader;
import master.flame.danmaku.danmaku.loader.IllegalDataException;
import master.flame.danmaku.danmaku.parser.IDataSource;

/**
 * <pre>
 *     Copyright (C), 2006-2021, 快乐阳光互动娱乐传媒有限公司
 *     Desc   : 
 *     Author : songmingwen
 *     Email  : mingwen@mgtv.com
 *     Time   : 2021/3/17 11:27
 * </pre>
 */
public class SongDanMakuLoader implements ILoader {
    @Override
    public IDataSource<?> getDataSource() {
        return null;
    }

    @Override
    public void load(String uri) throws IllegalDataException {

    }

    @Override
    public void load(InputStream in) throws IllegalDataException {

    }
}
