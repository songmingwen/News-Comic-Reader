package com.song.sunset.hook;

import android.content.Context;

/**
 * Desc:    敏感、危险 api 监控接口
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/14 15:39
 */

public interface HookInterface {
    /*** 开始监控 */
    void startObserve(Context context);

    /*** 结束监控 */
    void stopObserve();
}
