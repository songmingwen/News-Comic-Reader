package com.song.sunset.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.song.sunset.utils.ViewUtil;

/**
 * Created by songmw3 on 2016/12/8.
 */

public class ImageSpanView extends RelativeLayout {

    private boolean log = false;

    public ImageSpanView(Context context) {
        super(context);
    }

    public ImageSpanView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageSpanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        Log.i("-----onLayout ：", "top" + rect.top + " ; bottom" + rect.bottom);

        int height = getHeight();
        int[] arrayOfInt = new int[2];
        this.getLocationOnScreen(arrayOfInt);
        int j = arrayOfInt[1];
        if ((j < ViewUtil.getScreenHeigth()) && (j + height > 0)) {
            Log.i("-----onLayout ：", "在" + "height = " + height + " , top = " + j);
        } else {
            Log.i("-----onLayout ：", "不在" + "height = " + height + " , top = " + j);
        }
    }

    public void loadImageOrRemoveIfNeed() {
        Rect rect = new Rect();
        getGlobalVisibleRect(rect);
        if (log) {
            Log.i("-----loadImage ：", "top" + rect.top + " ; bottom" + rect.bottom);

            int height = getHeight();
            int[] arrayOfInt = new int[2];
            this.getLocationOnScreen(arrayOfInt);
            int j = arrayOfInt[1];
            if ((j < ViewUtil.getScreenHeigth()) && (j + height > 0)) {
                Log.i("-----loadImage ：", "在" + "height = " + height + " , top = " + j);
            } else {
                Log.i("-----loadImage ：", "不在" + "height = " + height + " , top = " + j);
            }
        }
    }

    public void log() {
        log = true;
    }
}