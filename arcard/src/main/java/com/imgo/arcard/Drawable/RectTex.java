package com.imgo.arcard.Drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Handler;

import org.artoolkit.ar.base.log.ArLog;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by norips on 20/10/16.
 */

public class RectTex extends Rectangle {
    private final static String TAG = "RectTex";
    private boolean finished = false;
    private int[] textures = null;
    private int[] textureAct = null;

    public RectTex(float pos[][], List<String> pathToTextures, Context context) {
        super(pos,pathToTextures,context);
    }
    /** This will be used to pass in the texture. */
    private int mTextureUniformHandle;


    /**
     * The object own drawing function.
     * Called from the renderer to redraw this instance
     * with possible changes in values.
     *
     */
    private Handler handler = null;
    Runnable runnable = null;
    private void reInitLoad(){
        for (int i = 0; i < pathToTextures.size(); i++)
            stack.addFirst(textureAct[i]);

        finished = false;
    }

    @Override
    public void init(){
        if(handler == null) {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    reInitLoad();
                    ArLog.d(TAG,"reInitLoad called");
                }
            };
        }
    }
    public void draw(float[] projectionMatrix, float[] modelViewMatrix) {
        if(handler!=null) {
            //Time out
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 10000);
        }
        super.draw(projectionMatrix,modelViewMatrix);
        GLES20.glUseProgram(shaderProgram.getShaderProgramHandle());
        shaderProgram.setProjectionMatrix(projectionMatrix);
        shaderProgram.setModelViewMatrix(modelViewMatrix);
        if(finished == false) {
            ArLog.d(TAG,"loadGLTexture called");
            loadGLTexture(context,pathToTextures);
            ArLog.d(TAG,"loadGLTexture exited");
        } else {
            mTextureUniformHandle = GLES20.glGetUniformLocation(shaderProgram.getShaderProgramHandle(), "u_Texture");
            // Tell the texture uniform sampler to use this texture in the shader by binding to texture unit textureAct[currentTexture].
            GLES20.glUniform1i(mTextureUniformHandle, textureAct[currentTexture]);
            ArLog.d(TAG,"Current texture" + textureAct[currentTexture]);
            shaderProgram.render(this.getmVertexBuffer(),this.getmTextureBuffer() , this.getmIndexBuffer());
        }

    }

    /**
     * Load the textures
     *
     * @param context - The Activity context
     * @param pathToTextures ArrayList of path to textures
     */
    public void loadGLTexture(Context context, List<String> pathToTextures) {
        //Generate a number of texture, texture pointer...
        textures = new int[pathToTextures.size()];
        textureAct = new int[pathToTextures.size()];
        GLES20.glGenTextures(pathToTextures.size(), textures, 0);

        Bitmap bitmap = null;

        for (int i = 0; i < pathToTextures.size(); i++) {
            // Create a bitmap
            bitmap = getBitmapFromAsset(context, pathToTextures.get(i));

            //...and bind it to our array
            textureAct[i] = stack.removeFirst();
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureAct[i]);
            GLES20.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);

            //Create Nearest Filtered Texture
            GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
            GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);

            //Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
            GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            GLES20.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

            //Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

            //Clean up
            bitmap = null;
        }
        finished = true;
    }

    /**
     * Return bitmap from file
     * @param context
     * @param filePath
     * @return Bitmap type
     */
    public static Bitmap getBitmapFromAsset(Context context, String filePath) {
        return BitmapFactory.decodeFile(filePath);
    }
}

