package com.song.sunset.base.holder;

import android.view.View;

import androidx.annotation.NonNull;

import com.song.sunset.base.R2;
import com.zhihu.android.sugaradapter.Layout;
import com.zhihu.android.sugaradapter.SugarHolder;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
@Layout(R2.layout.recycler_item_load_more_end)
public class DefaultLoadMoreEndHolder extends SugarHolder<DefaultLoadMoreEndHolder.EmptyInfo> {

    public DefaultLoadMoreEndHolder(@NonNull View view) {
        super(view);
    }

    @Override
    protected void onBindData(@NonNull EmptyInfo pEmptyInfo) {
    }

    public static class EmptyInfo {
        public String mTitle;
    }
}