package com.song.sunset.utils.net;

import retrofit2.Response;

/**
 * @author songmingwen
 * @description
 * @since 2020/2/26
 */
public class RetrofitAPIError extends Exception {
    public final Response response;

    public RetrofitAPIError(Response response) {
        super();
        this.response = response;
    }

    public <T> Response<T> getResponse() {
        return (Response<T>) response;
    }
}