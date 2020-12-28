package com.song.sunset.holders;

import android.view.View;
import android.widget.ProgressBar;

import com.song.sunset.R;
import com.song.sunset.R2;
import com.zhihu.android.sugaradapter.Layout;
import com.zhihu.android.sugaradapter.SugarHolder;

import androidx.annotation.NonNull;

/**
 * @author songmingwen
 * @description
 * @since 2020/12/16
 */
@Layout(R2.layout.recycler_item_progress)
public class DefaultLoadMoreProgressHolder extends SugarHolder<DefaultLoadMoreProgressHolder.Data> {
    private ProgressBar mProgressView;

    public DefaultLoadMoreProgressHolder(@NonNull View view) {
        super(view);
        mProgressView = view.findViewById(R.id.progress);
    }

    @Override
    protected void onBindData(@NonNull Data data) {

    }

    @Override
    protected void onViewAttachedToWindow() {
        super.onViewAttachedToWindow();
        mProgressView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onViewDetachedFromWindow() {
        super.onViewDetachedFromWindow();
        mProgressView.setVisibility(View.GONE);
    }

    public static class Data {
    }
}
