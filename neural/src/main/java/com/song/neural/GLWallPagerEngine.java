package com.song.neural;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/10
 */
class GLWallPagerEngine extends WallpaperService.Engine {

    private WallpaperService mWallPaperService;

    private WallpaperGLSurfaceView mWallpaperGLSurfaceView;

    public GLWallPagerEngine(WallpaperService ws) {
        ws.super();
        mWallPaperService = ws;
    }

    @Override
    public void onCreate(SurfaceHolder surfaceHolder) {
        super.onCreate(surfaceHolder);
    }

    @Override
    public void onSurfaceCreated(SurfaceHolder holder) {
        mWallpaperGLSurfaceView = new WallpaperGLSurfaceView(mWallPaperService);
    }

    @Override
    public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.onSurfaceChanged(holder, format, width, height);
    }

    @Override
    public void onVisibilityChanged(boolean visible) {
        super.onVisibilityChanged(visible);
        if (visible) {
            mWallpaperGLSurfaceView.onResume();
        } else {
            mWallpaperGLSurfaceView.onPause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWallpaperGLSurfaceView.onWallpaperDestroy();
    }

    private class WallpaperGLSurfaceView extends GLSurfaceView {

        public WallpaperGLSurfaceView(Context context) {
            super(context);

            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
            boolean supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
            if (supportEs2) {
                setEGLContextClientVersion(2);
                setRenderer(new GLRenderNeuralNet(getContext()));
                // 只有当绘制数据变化时，才绘制视图。
                setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
            }


        }

        @Override
        public SurfaceHolder getHolder() {
            return GLWallPagerEngine.this.getSurfaceHolder();
        }

        public void onWallpaperDestroy() {
            super.onDetachedFromWindow();
        }

    }

}
