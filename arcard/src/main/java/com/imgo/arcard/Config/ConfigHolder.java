package com.imgo.arcard.Config;

import android.text.TextUtils;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.log.ArLog;
import org.artoolkit.ar.base.rendering.gles20.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by norips on 20/10/16.
 * 本类作用：保存特征文件、model textures 视频信息（模型和画布）
 */

public class ConfigHolder {
    private static final String TAG = "ConfigHolder";

    private static ArrayList<Canvas> targets;
    private static ConfigHolder instance = null;
    private static boolean finish = false;
    private static boolean first = true;
    private static ShaderProgram shaderProgram=null;
    private static ShaderProgram shaderProgramMovie=null;

    private String currentDrawingCanvasName = null;

    private DrawStatusListener drawStatusListener;

    /**
     * Init all models and canvas, MUST be called in configureARScene function, during ARToolkit initialisation
     */
    synchronized public void init(){
        if (targets == null) {
            ArLog.e(TAG, "初始化失败，模型为空");
        }
        for(Canvas c : this.targets) {
            c.init();
        }
        finish = true;
    }

    /**
     * Init all models and canvas, MUST be called in a OpenGL thread
     */
    private void initGL(){
        for (Canvas c : targets){
            c.initGL(shaderProgram,shaderProgramMovie);
        }
    }

    /**
     * Load a config from a List
     * @param targets
     */
    public void load(List<Canvas> targets){
        this.targets = new ArrayList<Canvas>(targets);
    }

    public void setListener(DrawStatusListener listener) {
        this.drawStatusListener = listener;
    }

    /**
     * Set shader program for standard texture but do not use it
     * @param shaderProgram
     */
    public void setShaderProgram(ShaderProgram shaderProgram){
        this.shaderProgram = shaderProgram;
    }

    /**
     * Set shader program for movie texture but do not use it
     * @param shaderProgram
     */
    public void setShaderProgramMovie(ShaderProgram shaderProgram){
        this.shaderProgramMovie = shaderProgram;
    }

    /**
     * Singleton class
     * @return A unique instance of ConfigHolder
     */
    synchronized public static ConfigHolder getInstance(){
        if (instance == null) instance = new ConfigHolder();
        return instance;
    }

    /**
     * Draw all models and scale them to marker
     * @param projectionMatrix Float projectionMatrix.
     *
     */
    public void draw(float[] projectionMatrix){
        if(finish) {
            if(first){
                initGL();
                first = false;
            } else {
                String tempCanvasName = null;
                for (Canvas c : targets) {
                    if (ARToolKit.getInstance().queryMarkerVisible(c.getMarkerUID())) {
                        tempCanvasName = c.getCanvasName();
                        c.draw(projectionMatrix, ARToolKit.getInstance().queryMarkerTransformation(c.getMarkerUID()));
                    } else {
                        c.onMarkerInVisible();
                    }
                }
                //判断识别模型状态是否变更，如果变更则通知业务。
                if (drawStatusListener != null) {
                    if (!TextUtils.equals(currentDrawingCanvasName, tempCanvasName)) {
                        currentDrawingCanvasName = tempCanvasName;
                        if (TextUtils.isEmpty(currentDrawingCanvasName)) {
                            drawStatusListener.onInvisible();
                        } else {
                            drawStatusListener.onDrawing(currentDrawingCanvasName);
                        }
                    }
                }
            }
        }
    }

    public void reset() {
        currentDrawingCanvasName = null;
        if (first) {
            return;
        }
        first = true;
        for (Canvas c : targets) {
            c.reset();
        }
    }

    /**
     * Call nextPage() for all visible canva
     */
    public void nextPage(){
        for(Canvas c : targets){
            if(ARToolKit.getInstance().queryMarkerVisible(c.getMarkerUID())) {
                c.nextPage();
            }
        }
    }
    /**
     * Call previousPage() for all visible canva
     */
    public void previousPage(){
        for(Canvas c : targets){
            if(ARToolKit.getInstance().queryMarkerVisible(c.getMarkerUID())) {
                c.previousPage();
            }
        }
    }

}
