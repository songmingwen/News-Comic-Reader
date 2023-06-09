package com.imgo.arcard.Drawable;

import org.artoolkit.ar.base.rendering.gles20.ARDrawableOpenGLES20;

/**
 * Created by norips on 15/12/16.
 */

public interface Drawable extends ARDrawableOpenGLES20 {
    void nextTexture();
    void previousTexture();
    void reset();
}
