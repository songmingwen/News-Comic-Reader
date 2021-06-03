package com.song.sunset.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.song.sunset.base.R;

/**
 * @author songmingwen
 * @description
 * @since 2019/7/3
 */
public class SnackBarUtils {

    public static Snackbar show(View view, String Content, int duration, String action, View.OnClickListener clickListener) {
        final Snackbar snackbar = Snackbar.make(view, Content, duration);
        snackbar.setActionTextColor(view.getContext().getResources().getColor(R.color.color_accent));
        snackbar.setAction(action, clickListener);
        snackbar.show();
        return snackbar;
    }
}
