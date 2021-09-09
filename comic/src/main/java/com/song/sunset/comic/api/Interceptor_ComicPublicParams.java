package com.song.sunset.comic.api;

import com.google.auto.service.AutoService;
import com.song.sunset.base.AppConfig;
import com.song.sunset.base.autoservice.interceptor.Interceptor;
import com.song.sunset.base.net.BasicParamsInterceptor;
import com.song.sunset.base.utils.DeviceUtils;

/**
 * Desc:    添加固定公共请求参数
 * http://api.iclient.ifeng.com/ClientNews?
 * id=SYLB10,SYDT10&
 * action=down&
 * pullNum=3&
 * lastDoc=,,,&
 * province=北京市&
 * city=北京市&
 * district=朝阳区&
 * gv=5.5.4&
 * av=5.5.4&
 * uid=865982024584370&
 * deviceid=865982024584370&
 * proid=ifengnews&
 * os=android_23&
 * df=androidphone&
 * vt=5&
 * screen=1080x1920&
 * publishid=9023&
 * nw=wifi&
 * loginid=76078652
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/9/9 16:30
 *
 */
@AutoService(Interceptor.class)
public class Interceptor_ComicPublicParams implements Interceptor {
    @Override
    public okhttp3.Interceptor createInterceptor() {
        return getBasePublicParams();
    }

    private okhttp3.Interceptor getBasePublicParams() {
        BasicParamsInterceptor.Builder builder = new BasicParamsInterceptor.Builder();
        builder
                .addQueryParam("device_id", "S_phone")
                .addQueryParam("uid", DeviceUtils.getAuthenticationID(AppConfig.getApp()))
                .addQueryParam("id", "SYLB10,SYDT10")
                .addQueryParam("gv", "5.6.9")
                .addQueryParam("av", "5.6.9")
                .addQueryParam("nw", "wifi")
                .addQueryParam("df", "androidphone")
                .addQueryParam("publishid", "9023")
                .addQueryParam("v", "3360100")
                .addQueryParam("t", System.currentTimeMillis() + "")
                .addQueryParam("key", "387df5b33fc7fe893a7ca573591a9d82ee5695909ca89b94bc237d734e13762664c4437ea3069d86847388c198390f44b7c0947136188de4aca46c4adfd7eaf9c0844103fd9f7b9f554531ff99da3430222d17ed61d61cfede2d27cb667b3173:::u17");

        return builder.build();
    }
}
