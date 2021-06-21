package com.song.sunset.beans.zhihu;


import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author songmingwen
 * @description
 * @since 2019/6/24
 */
@Keep
public class TagsList {

    public String name;

    public String icon;

    public boolean isSelected;

    /**
     * 颜色
     */
    public String color;

    /**
     * 带透明度颜色
     */
    public String transColor;

    @SerializedName("sub_groups")
    public List<SubTagsList> subGroups;

}
