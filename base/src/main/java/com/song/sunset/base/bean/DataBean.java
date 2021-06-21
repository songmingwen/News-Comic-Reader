package com.song.sunset.base.bean;

import androidx.annotation.Keep;

/**
 * Created by Song on 2016/9/6 0006.
 * Email:z53520@qq.com
 */
@Keep
public class DataBean<T> {
    public int stateCode;

    public String message;

    public T returnData;
}
