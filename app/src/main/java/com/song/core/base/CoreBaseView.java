package com.song.core.base;

import android.content.Context;

/**
 * Created by hpw on 16/10/27.
 */

public interface CoreBaseView {
    Context getContext();

    void showError(String msg);
}
