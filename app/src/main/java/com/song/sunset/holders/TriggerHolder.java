package com.song.sunset.holders;

import android.view.View;

import com.song.sunset.R2;
import com.zhihu.android.sugaradapter.Layout;
import com.zhihu.android.sugaradapter.SugarHolder;

import androidx.annotation.NonNull;

@Layout(R2.layout.comic_list_full_item)
public class TriggerHolder extends SugarHolder<TriggerHolder> {
    public TriggerHolder(@NonNull View view) {
        super(view);
    }

    @Override
    protected void onBindData(@NonNull TriggerHolder data) {

    }
}