/*
 *  SimpleRenderer.java
 *  ARToolKit5
 *
 *  Disclaimer: IMPORTANT:  This Daqri software is supplied to you by Daqri
 *  LLC ("Daqri") in consideration of your agreement to the following
 *  terms, and your use, installation, modification or redistribution of
 *  this Daqri software constitutes acceptance of these terms.  If you do
 *  not agree with these terms, please do not use, install, modify or
 *  redistribute this Daqri software.
 *
 *  In consideration of your agreement to abide by the following terms, and
 *  subject to these terms, Daqri grants you a personal, non-exclusive
 *  license, under Daqri's copyrights in this original Daqri software (the
 *  "Daqri Software"), to use, reproduce, modify and redistribute the Daqri
 *  Software, with or without modifications, in source and/or binary forms;
 *  provided that if you redistribute the Daqri Software in its entirety and
 *  without modifications, you must retain this notice and the following
 *  text and disclaimers in all such redistributions of the Daqri Software.
 *  Neither the name, trademarks, service marks or logos of Daqri LLC may
 *  be used to endorse or promote products derived from the Daqri Software
 *  without specific prior written permission from Daqri.  Except as
 *  expressly stated in this notice, no other rights or licenses, express or
 *  implied, are granted by Daqri herein, including but not limited to any
 *  patent rights that may be infringed by your derivative works or by other
 *  works in which the Daqri Software may be incorporated.
 *
 *  The Daqri Software is provided by Daqri on an "AS IS" basis.  DAQRI
 *  MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 *  THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE, REGARDING THE DAQRI SOFTWARE OR ITS USE AND
 *  OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 *
 *  IN NO EVENT SHALL DAQRI BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 *  MODIFICATION AND/OR DISTRIBUTION OF THE DAQRI SOFTWARE, HOWEVER CAUSED
 *  AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 *  STRICT LIABILITY OR OTHERWISE, EVEN IF DAQRI HAS BEEN ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 *  Copyright 2015 Daqri, LLC.
 *  Copyright 2011-2015 ARToolworks, Inc.
 *
 *  Author(s): Julian Looser, Philip Lamb
 *
 */

package com.imgo.arcard;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.imgo.arcard.Config.ConfigHolder;
import com.imgo.arcard.shader.SimpleFragmentShader;
import com.imgo.arcard.shader.SimpleShaderProgram;
import com.imgo.arcard.shader.SimpleVertexShader;
import com.imgo.arcard.shaderMovie.FragmentShaderMovie;
import com.imgo.arcard.shaderMovie.ShaderProgramMovie;
import com.imgo.arcard.shaderMovie.VertexShaderMovie;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.log.ArLog;
import org.artoolkit.ar.base.rendering.gles20.ARRendererGLES20;
import org.artoolkit.ar.base.rendering.gles20.ShaderProgram;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * A very simple Renderer that adds a marker and draws a cube on it.
 */
public class SimpleRenderer extends ARRendererGLES20 {
    private final static String TAG = "SimpleRenderer";
    private float tmpMatrix[] = new float[16];

    /**
     * This method gets called from the framework to setup the ARScene.
     * So this is the best spot to configure you assets for your AR app.
     * For example register used markers in here.
     */

    @Override
    public boolean configureARScene() {
        //Aka have access to file, else onRequestPermissionsResult while trigger it
        ConfigHolder.getInstance().init();
        return true;
    }

    //Shader calls should be within a GL thread that is onSurfaceChanged(), onSurfaceCreated() or onDrawFrame()
    //As the cube instantiates the shader during setShaderProgram call we need to create the cube here.
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        super.onSurfaceCreated(unused, config);
        ArLog.i(TAG, "onSurfaceCreated");
        ShaderProgram shaderProgram = new SimpleShaderProgram(new SimpleVertexShader(), new SimpleFragmentShader());
        ConfigHolder.getInstance().setShaderProgram(shaderProgram);
        ConfigHolder.getInstance().setShaderProgramMovie(new ShaderProgramMovie(new VertexShaderMovie(), new FragmentShaderMovie()));

    }

    /**
     * Override the render function from {@link ARRendererGLES20}.
     */
    @Override
    public void draw() {
        ArLog.i(TAG, "draw");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        float[] projectionMatrix = ARToolKit.getInstance().getProjectionMatrix();
        //Rotate matrix
        Matrix.setIdentityM(tmpMatrix, 0);
        Matrix.rotateM(tmpMatrix, 0, 90.0f, 0.0f, 0.0f, -1.0f);
        // /!\ tmpMatrix * projectionMatrix =/=  projectionMatrix * tmpMatrix
        Matrix.multiplyMM(projectionMatrix, 0, tmpMatrix, 0, projectionMatrix, 0);

        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glFrontFace(GLES20.GL_CW);
        ConfigHolder.getInstance().draw(projectionMatrix);

    }


}