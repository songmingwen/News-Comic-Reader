package com.song.sunset.base.bean;

import androidx.annotation.Keep;

/**
 * Created by Song on 2016/9/6 0006.
 * Email:z53520@qq.com
 */
@Keep
public class BaseBean<T> {

    public int code;

    public DataBean<T> data;
}
