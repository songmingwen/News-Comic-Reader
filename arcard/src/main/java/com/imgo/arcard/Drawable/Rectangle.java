package com.imgo.arcard.Drawable;

import android.content.Context;
import android.opengl.GLES20;

import org.artoolkit.ar.base.log.ArLog;
import org.artoolkit.ar.base.rendering.RenderUtils;
import org.artoolkit.ar.base.rendering.gles20.ShaderProgram;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by norips on 24/10/16.
 */

public abstract class Rectangle implements Drawable {
    protected FloatBuffer mVertexBuffer;
    protected FloatBuffer mTexBuffer;
    protected ByteBuffer mIndexBuffer;
    protected List<String> pathToTextures;
    protected ShaderProgram shaderProgram = null;
    protected Context context;
    protected int currentTexture;
    protected static int nextTexture=0;
    protected static BlockingDeque<Integer> stack = new LinkedBlockingDeque<Integer>();
    protected byte[] indices = {0,1,2,2,3,0};          //      0***1
                                                        //      *   *
                                                        //      3***2
    private static boolean firstTime = true;
    protected float[] texCoords = {
            0,0, //Reverse axis Top left
            1,0, //Top right
            1,1, //Bottom right
            0,1};//Bottom left

    /**
     *
     * @param pos Array of 3D position, the four point of your rectangle
     *            pos[0] = Top Left corner
     *            pos[1] = Top Right corner
     *            pos[2] = Bottom Right corner
     *            pos[3] = Bottom Left corner
     * @param pathToTextures An ArrayList<String> containing paths to your texture
     *
     */
    public Rectangle(float pos[][], List<String> pathToTextures, Context context) {
        setArrays(pos);
        this.pathToTextures = new ArrayList<String>(pathToTextures);
        this.context = context;
        currentTexture = 0;
    }

    public FloatBuffer getmVertexBuffer() {
        return mVertexBuffer;
    }

    public ByteBuffer getmIndexBuffer() {
        return mIndexBuffer;
    }

    public FloatBuffer getmTextureBuffer() {
        return mTexBuffer;
    }

    public void setShaderProgram(ShaderProgram shaderProgram) {
        this.shaderProgram = shaderProgram;
    }

    public void init(){
        ;
    }
    private void setArrays(float pos[][]) {


        float vertices[] = new float[12];
        for(int i = 0; i < 4;i++){
            vertices[(i*3)] = pos[i][0];
            vertices[(i*3)+1] = pos[i][1];
            vertices[(i*3)+2] = pos[i][2];
            ArLog.d("RectText","" + vertices[(i*3)] + "," + vertices[(i*3)+1] + "," + vertices[(i*3)+2]);
        }

        mVertexBuffer = RenderUtils.buildFloatBuffer(vertices);
        mIndexBuffer = RenderUtils.buildByteBuffer(indices);
        mTexBuffer = RenderUtils.buildFloatBuffer(texCoords);
    }

    public void draw(float[] projectionMatrix, float[] modelViewMatrix){
        if(firstTime){
            int textureUnit[] = new int[1];
            GLES20.glGetIntegerv(GLES20.GL_MAX_TEXTURE_IMAGE_UNITS,textureUnit,0);
            for(int i = 0; i < textureUnit[0];i++){
                stack.addLast(i);
            }
        }
        firstTime = false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void onMarkerInVisible() {

    }

    public void nextTexture(){
        if(currentTexture >= pathToTextures.size()-1){
            currentTexture = 0;
        } else {
            currentTexture++;
        }

    }
    public void previousTexture(){
        if(currentTexture == 0){
            currentTexture = pathToTextures.size()-1;
        } else {
            currentTexture--;
        }

    }
}
