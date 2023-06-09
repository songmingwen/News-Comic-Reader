package org.artoolkit.ar.base.rendering;

import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.opengl.GLSurfaceView;

import org.artoolkit.ar.base.log.ArLog;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Desc:    默认 EGLConfig 配置，有抗锯齿配置
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/30 9:42
 */
public class DefConfigChooser implements GLSurfaceView.EGLConfigChooser {

    private static final String TAG = "DefConfigChooser";

    private boolean supportsEs2;

    public DefConfigChooser(boolean supportsEs2) {
        supportsEs2 = supportsEs2;
    }

    @Override
    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
        int[] attrs = {
                EGL10.EGL_RED_SIZE, 8,
                EGL10.EGL_GREEN_SIZE, 8,
                EGL10.EGL_BLUE_SIZE, 8,
                EGL10.EGL_ALPHA_SIZE, 8,
                EGL10.EGL_DEPTH_SIZE, 16,
                EGL10.EGL_STENCIL_SIZE, 0,
                EGL10.EGL_SAMPLES, 2,  // This is for 4x MSAA.抗锯齿
                EGL10.EGL_NONE
        };

        /* We know none of the subclasses define EGL_RENDERABLE_TYPE.
         * And we know the configSpec is well formed.
         */
        int len = attrs.length;
        int[] newConfigSpec = new int[len + 2];
        System.arraycopy(attrs, 0, newConfigSpec, 0, len-1);
        newConfigSpec[len-1] = EGL10.EGL_RENDERABLE_TYPE;
        if (supportsEs2) {
            newConfigSpec[len] = EGL14.EGL_OPENGL_ES2_BIT;  /* EGL_OPENGL_ES2_BIT */
        } else {
            newConfigSpec[len] = EGLExt.EGL_OPENGL_ES3_BIT_KHR; /* EGL_OPENGL_ES3_BIT_KHR */
        }
        newConfigSpec[len+1] = EGL10.EGL_NONE;

        EGLConfig[] configs = new EGLConfig[1];
        int[] configCounts = new int[1];
        egl.eglChooseConfig(display, newConfigSpec, configs, 1, configCounts);

        if (configCounts[0] == 0) {
            // Failed! Error handling.
            ArLog.d(TAG, "抗锯齿设置失败");
            return null;
        } else {
            return configs[0];
        }
    }
}
