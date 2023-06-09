package com.imgo.arcard.Config;


import android.content.Context;

import com.imgo.arcard.Drawable.Drawable;
import com.imgo.arcard.Drawable.RectTex;

import org.artoolkit.ar.base.rendering.gles20.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norips on 20/10/16.
 */

public class Model {
    private final static String TAG = "Model";
    private float pos[][] = new float[4][3];
    private Drawable drawable;
    private Context context;
    private List<String> pathToTextures;

    /**
     *
     * @param name Name of your model, only use to debug
     * @param pos Array of 3D position, the four point of your rectangle
     *            pos[0] = Top Left corner
     *            pos[1] = Top Right corner
     *            pos[2] = Bottom Right corner
     *            pos[3] = Bottom Left corner
     * @param pathToTextures An ArrayList<String> containing paths to your texture
     * @param context Context activity to load from assets folder
     */
    public Model(String name, float pos[][], List<String> pathToTextures, Context context){
        for(int i = 0; i < 4;i++){
            this.pos[i][0] = pos[i][0];
            this.pos[i][1] = pos[i][1];
            this.pos[i][2] = pos[i][2];
        }
        this.context = context;
        this.pathToTextures = new ArrayList<String>(pathToTextures);

    }

    public Model(String name, Drawable drawable){
        for(int i = 0; i < 4;i++){
            this.pos[i][0] = pos[i][0];
            this.pos[i][1] = pos[i][1];
            this.pos[i][2] = pos[i][2];
        }
        //TODO: Load texture on detection or on startup ?
        this.drawable = drawable;
    }

    /**
     * Draw all models and scale them to marker
     * @param projectionMatrix Float projectionMatrix.
     * @param modelViewMatrix Float modelViewMatrix.
     *
     */
    public void draw(float[] projectionMatrix, float[] modelViewMatrix) {
        if (drawable == null) {
            return;
        }
        drawable.draw(projectionMatrix,modelViewMatrix);
    }

    public void onMarkerInVisible() {
        if (drawable == null) {
            return;
        }
        drawable.onMarkerInVisible();
    }

    public void init(){
        if(drawable == null)
            drawable = new RectTex(pos,pathToTextures,context);
    }

    public void nextPage(){
        if (drawable == null) {
            return;
        }
        drawable.nextTexture();
    }

    public void previousPage(){
        if (drawable == null) {
            return;
        }
        drawable.previousTexture();
    }

    public void initGL(ShaderProgram shaderProgram){
        if (drawable == null) {
            return;
        }
        drawable.setShaderProgram(shaderProgram);
    }

    public void reset() {
        if (drawable == null) {
            return;
        }
        drawable.reset();
    }
}
