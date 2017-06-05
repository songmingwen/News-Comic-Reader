package com.song.sunset.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.song.sunset.utils.ViewUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Song on 2016/12/8.
 */

public class CustomScrollView extends ScrollView {

    private LayoutInflater inflater;
    private LinearLayout linearLayout;
    Map<Integer, ImageSpanView> imageSpanMap = new HashMap();

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.inflater = LayoutInflater.from(getContext());
        linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        addView(linearLayout, params);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Iterator iterator = imageSpanMap.values().iterator();
        while (iterator.hasNext()) {
            ((ImageSpanView) iterator.next()).loadImageOrRemoveIfNeed();
        }
    }

    public void addViews() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewUtil.dip2px(200));
        for (int i = 0; i < 10; i++) {
            ImageSpanView imageSpanView = new ImageSpanView(getContext());
            imageSpanView.setBackgroundColor(Color.parseColor("#" + i + i + i + i + i + i));
            imageSpanView.setLayoutParams(params);
            if (i == 0) {
                imageSpanView.log();
            }
            imageSpanView.loadImageOrRemoveIfNeed();
            linearLayout.addView(imageSpanView);
            imageSpanMap.put(i, imageSpanView);
        }
    }
}
