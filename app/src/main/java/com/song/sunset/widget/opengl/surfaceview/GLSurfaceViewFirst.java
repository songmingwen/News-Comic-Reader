package com.song.sunset.widget.opengl.surfaceview;

import android.content.Context;
import android.util.AttributeSet;

import com.song.sunset.widget.opengl.render.GLRenderFirst;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/22
 */
public class GLSurfaceViewFirst extends BaseGLSurfaceView {
    public GLSurfaceViewFirst(Context context) {
        super(context);
    }

    public GLSurfaceViewFirst(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Renderer getRender() {
        return new GLRenderFirst(getContext());
    }
}
