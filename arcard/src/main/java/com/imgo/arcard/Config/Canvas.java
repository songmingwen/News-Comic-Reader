package com.imgo.arcard.Config;

import android.opengl.Matrix;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.rendering.gles20.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norips on 20/10/16.
 */

public class Canvas {

    private String pathToFeature;
    private int markerID;
    private List<Model> models;
    private List<Model> modelsMovie;
    private String canvasName;

    /**
     *
     * @param name Only use to debug
     * @param pathToFeature Path to feature folder (folder containing iset,fset and fset3 file
     * @param models A List of your models
     */
    public Canvas(String name, String pathToFeature, List<Model> models){
        this.pathToFeature = pathToFeature;
        this.models = new ArrayList<Model>(models);
        modelsMovie = new ArrayList<Model>();
    }

    public void init(){
        this.markerID = ARToolKit.getInstance().addMarker("nft;" + pathToFeature);
        for(Model m : models)
            m.init();
    }

    /**
     *
     * @param name Only use to debug
     * @param pathToFeature Path to feature folder (folder containing iset,fset and fset3 file
     */
    public Canvas(String name, String pathToFeature){
        this.canvasName = name;
        this.pathToFeature = pathToFeature;
        models = new ArrayList<Model>();
        modelsMovie = new ArrayList<Model>();
    }

    /**
     * Must be called in opengl thread
     * @param shaderProgram Shader compiled program for standard texture
     * @param shaderProgramMovie Shader compiled program for movie texture (Using OES buffer)
     */
    public void initGL(ShaderProgram shaderProgram, ShaderProgram shaderProgramMovie){
        for(Model m : models){
            m.initGL(shaderProgram);
        }
        for(Model m : modelsMovie){
            if(shaderProgramMovie != null)
                m.initGL(shaderProgramMovie);
        }
    }
    /**
     *
     * @return markerUID of the Canvas
     */
    public int getMarkerUID(){
        return markerID;
    }

    /**
     *
     * @param model The model you want to add to this canvas
     */
    public void addModel(Model model){
        models.add(model);
    }

    public void addModelMovie(Model model){
        models.add(model);
        modelsMovie.add(model);

    }

    public String getPathToFeature() {
        return pathToFeature;
    }

    public String getCanvasName() {
        return canvasName;
    }

    /**
     * Draw all models and scale them to marker
     * @param projectionMatrix Float projectionMatrix.
     * @param modelViewMatrix Float modelViewMatrix.
     *
     */
    public void draw(float[] projectionMatrix, float[] modelViewMatrix){
        if (ARToolKit.getInstance().getMarkerPatternCount(markerID) > 0 && modelViewMatrix != null) {
            float[] width = new float[1];
            float[] height = new float[1];
            ARToolKit.getInstance().getMarkerPatternConfig(markerID, 0, null, width, height, null, null);
            Matrix.scaleM(modelViewMatrix, 0, width[0] / 100.0f, height[0] / 100, 1.0f);
        }
        for(Model model : models){
            model.draw(projectionMatrix,modelViewMatrix);
        }
    }

    public void onMarkerInVisible() {
        for (Model model : models) {
            model.onMarkerInVisible();
        }
    }

    /**
     *  Change texture to all model to next page
     */
    public void nextPage(){
        for(Model model : models){
            model.nextPage();
        }
    }

    /**
     * Change texture to all model to previous page
     */
    public void previousPage(){
        for(Model model : models){
            model.previousPage();
        }
    }

    public void reset() {
        for(Model model : models){
            model.reset();
        }
    }
}
