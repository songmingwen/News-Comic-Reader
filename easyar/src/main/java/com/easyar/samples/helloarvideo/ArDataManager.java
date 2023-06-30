package com.easyar.samples.helloarvideo;

import android.text.TextUtils;

import com.easyar.samples.helloarvideo.bean.ArData;

import java.util.List;

/**
 * Desc:    ar 数据持有类
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/6/30 10:28
 */
public class ArDataManager {

    private static ArDataManager Instance = new ArDataManager();

    private List<ArData> list;

    private ArDataManager() {
    }

    public static ArDataManager getInstance() {
        return Instance;
    }

    public void setList(List<ArData> list) {
        this.list = list;
    }

    public List<ArData> getList() {
        return list;
    }

    public ArData getArDataFromName(String name) {
        ArData arData = null;
        if (enable()) {
            for (ArData data : getList()) {
                if (TextUtils.equals(name, data.getImgName())) {
                    arData = data;
                    break;
                }
            }
        }
        return arData;
    }

    public int getIndexFromName(String name) {
        int index = 0;
        if (enable()) {

            for (int i = 0; i < getList().size(); i++) {
                if (TextUtils.equals(name, list.get(i).getImgName())) {
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    public int getArListSize() {
        if (enable()) {
            return list.size();
        } else {
            return 0;
        }
    }

    public boolean enable() {
        return list != null && !list.isEmpty();
    }
}
