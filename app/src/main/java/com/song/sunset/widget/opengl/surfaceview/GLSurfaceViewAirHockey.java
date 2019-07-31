package com.song.sunset.widget.opengl.surfaceview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.song.sunset.widget.opengl.render.AirHockeyRenderer;

/**
 * @author songmingwen
 * @description
 * @since 2019/4/26
 */
public class GLSurfaceViewAirHockey extends BaseGLSurfaceView {

    private AirHockeyRenderer mAirHockeyRenderer;

    public GLSurfaceViewAirHockey(Context context) {
        super(context);
    }

    public GLSurfaceViewAirHockey(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected Renderer getRender() {
        mAirHockeyRenderer = new AirHockeyRenderer(getContext());
        return mAirHockeyRenderer;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event != null) {
            // Convert touch coordinates into normalized device
            // coordinates, keeping in mind that Android's Y
            // coordinates are inverted.
            final float normalizedX = (event.getX() / (float) getWidth()) * 2 - 1;
            final float normalizedY = -((event.getY() / (float) getHeight()) * 2 - 1);

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                GLSurfaceViewAirHockey.this.queueEvent(() -> mAirHockeyRenderer.handleTouchPress(normalizedX, normalizedY));
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                GLSurfaceViewAirHockey.this.queueEvent(() -> mAirHockeyRenderer.handleTouchDrag(normalizedX, normalizedY));
            }

            return true;
        } else {
            return false;
        }
    }
}
