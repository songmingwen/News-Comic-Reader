package com.song.sunset.utils.retrofit;

import com.song.sunset.beans.basebeans.BaseBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Song on 2016/9/20 0020.
 * Email:z53520@qq.com
 */
public class RetrofitCall {

    public static <T> void call(Call<BaseBean<T>> call, final RetrofitCallback<T> retrofitCall) {
        call.enqueue(new Callback<BaseBean<T>>() {
            @Override
            public void onResponse(Call<BaseBean<T>> call, Response<BaseBean<T>> response) {
                if (response == null) {
                    retrofitCall.onFailure(-1, "无数据");
                    return;
                }

                if (!response.isSuccessful()) {
                    retrofitCall.onFailure(response.code(), "非成功响应码");
                    return;
                }

                if (response.body() == null) {
                    retrofitCall.onFailure(-1, "无内容");
                    return;
                }

                if (response.body().data == null) {
                    retrofitCall.onFailure(-1, "没有信息");
                    return;
                }

                if (response.body().data.stateCode == 0) {
                    retrofitCall.onFailure(-1, "没有更多信息");
                    return;
                }

                T returnData = response.body().data.returnData;
                retrofitCall.onSuccess(returnData);
            }

            @Override
            public void onFailure(Call<BaseBean<T>> call, Throwable t) {
                retrofitCall.onFailure(-7, t.toString());
            }
        });
    }
}
