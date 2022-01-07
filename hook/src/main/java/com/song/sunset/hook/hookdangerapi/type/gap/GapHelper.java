package com.song.sunset.hook.hookdangerapi.type.gap;

import android.content.Context;
import android.text.TextUtils;

import com.song.sunset.hook.bean.GapConfig;
import com.song.sunset.hook.bean.GapConfigList;
import com.song.sunset.hook.utils.AssetsUtil;
import com.song.sunset.hook.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Desc:    间隔时间 api 调用帮助类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2021/12/29 14:39
 */
public class GapHelper {

    private static List<GapConfig> list;

    public static long getGapTime(Context context, String apiName) {
        if (!getConfigList(context).isEmpty()) {
            for (GapConfig config : getConfigList(context)) {
                if (TextUtils.equals(apiName, config.apiName)) {
                    return config.gapTime;
                }
            }
        }
        return 0;
    }

    private static List<GapConfig> getConfigList(Context context) {
        if (list != null) {
            return list;
        }
        String json = AssetsUtil.getJson("danger_gap.json", context);
        GapConfigList listData = JsonUtil.gsonToBean(json, GapConfigList.class);
        if (listData == null || listData.data == null || listData.data.isEmpty()) {
            list = new ArrayList<>();
        } else {
            list = listData.data;
        }
        return list;
    }
}
