package com.song.sunset.utils.retrofit;

/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public interface RetrofitCallback<T> {

    void onSuccess(T t);

    void onFailure(int errorCode, String errorMsg);

}
