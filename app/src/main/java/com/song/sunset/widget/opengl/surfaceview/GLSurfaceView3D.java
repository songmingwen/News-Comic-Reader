package com.song.sunset.widget.opengl.surfaceview;

import android.content.Context;
import android.util.AttributeSet;

import com.song.sunset.widget.opengl.render.OpenGLRender3D;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/22
 */
public class GLSurfaceView3D extends BaseGLSurfaceView {
    public GLSurfaceView3D(Context context) {
        super(context);
    }

    public GLSurfaceView3D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Renderer getRender() {
        return new OpenGLRender3D(getContext());
    }
}
