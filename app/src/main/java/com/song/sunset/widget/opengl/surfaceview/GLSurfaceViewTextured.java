package com.song.sunset.widget.opengl.surfaceview;

import android.content.Context;
import android.util.AttributeSet;

import com.song.sunset.widget.opengl.render.GLRenderTextured;


/**
 * @author songmingwen
 * @description
 * @since 2019/4/22
 */
public class GLSurfaceViewTextured extends BaseGLSurfaceView {
    public GLSurfaceViewTextured(Context context) {
        super(context);
    }

    public GLSurfaceViewTextured(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Renderer getRender() {
        return new GLRenderTextured(getContext());
    }
}
