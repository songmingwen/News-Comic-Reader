package org.artoolkit.ar.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.artoolkit.ar.base.camera.CameraEventListener;
import org.artoolkit.ar.base.camera.CaptureCameraPreview;
import org.artoolkit.ar.base.log.ArLog;
import org.artoolkit.ar.base.rendering.ARRenderer;
import org.artoolkit.ar.base.rendering.DefConfigChooser;
import org.artoolkit.ar.base.rendering.gles20.ARRendererGLES20;


/**
 * Desc:
 * A Fragment which can be subclassed to create an AR application. ARBaseFragment handles almost all of
 * the required operations to create a simple augmented reality application.
 * <p/>
 * ARBaseFragment automatically creates a camera preview surface and an OpenGL surface view, and
 * arranges these correctly in the user interface.The subclass simply needs to provide a FrameLayout
 * object which will be populated with these UI components, using {@link #supplyFrameLayout() supplyFrameLayout}.
 * <p/>
 * To create a custom AR experience, the subclass should also provide a custom renderer using
 * {@link #supplyRenderer() Renderer}. This allows the subclass to handle OpenGL drawing calls on its own.
 * <p>
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/15 9:39
 */
public abstract class ARBaseFragment extends Fragment implements CameraEventListener {

    private boolean firstUpdate = false;

    protected final static String TAG = "ARBaseFragment";

    /*** GL surface to render the virtual objects */
    private GLSurfaceView glView;

    /*** Camera preview which will provide video frames.*/
    private CaptureCameraPreview preview = null;

    /*** Renderer to use. This is provided by the subclass using {@link #supplyRenderer() Renderer()}.*/
    protected ARRenderer renderer;

    /**
     * Layout that will be filled with the camera preview and GL views. This is provided by the subclass using {@link #supplyFrameLayout() supplyFrameLayout()}.
     */
    protected ViewGroup mainLayout;

    /**
     * For any square template (pattern) markers, the number of rows
     * and columns in the template. May not be less than 16 or more than AR_PATT_SIZE1_MAX.
     */
    protected int pattSize = 16;

    /**
     * For any square template (pattern) markers, the maximum number
     * of markers that may be loaded for a single matching pass. Must be > 0.
     */
    protected int pattCountMax = 25;

    @Override
    public void cameraPreviewStarted(int width, int height, int rate, String pixelFormat, int cameraIndex, boolean cameraIsFrontFacing) {
        try {
            if (ARToolKit.getInstance().startWithPushedVideo(width, height, pixelFormat, null, cameraIndex, cameraIsFrontFacing)) {
                // Expects Data to be already in the cache dir. This can be done with the AssetUnpacker.
                ArLog.i(TAG, "cameraPreviewStarted(): Camera initialised");
            } else {
                // Error
                ArLog.e(TAG, "cameraPreviewStarted(): Error initialising camera. Cannot continue.");
                showError("cameraPreviewStarted(): Error initialising camera. Cannot continue.");
            }

            ArLog.i(TAG, "Camera settings: " + width + "x" + height + "@" + rate + "fps");
            firstUpdate = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cameraPreviewFrame(byte[] frame, int frameSize) {
        try {
            if (firstUpdate) {
                // ARToolKit has been initialised. The renderer can now add markers, etc...
                ArLog.i(TAG, "cameraPreviewFrame(): firstUpdate");
                if (renderer.configureARScene()) {
                    ArLog.i(TAG, "cameraPreviewFrame(): Scene configured successfully");
                } else {
                    // Error
                    ArLog.e(TAG, "cameraPreviewFrame(): Error configuring scene. Cannot continue.");
                    showError("cameraPreviewFrame(): Error configuring scene. Cannot continue.");
                }
                firstUpdate = false;
            }
            if (ARToolKit.getInstance().convertAndDetect1(frame, frameSize)) {
                // Update the renderer as the frame has changed
                if (glView != null)
                    glView.requestRender();
                onFrameProcessed();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cameraPreviewStopped() {
        try {
            ARToolKit.getInstance().stopAndFinal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(Thread thread, Throwable e) {
                        handleUncaughtException(thread, e);
                    }
                });
    }

    /**
     * Allows subclasses to supply a {@link FrameLayout} which will be populated
     * with a camera preview and GL surface view.
     *
     * @return The {@link FrameLayout} to use.
     */
    protected abstract FrameLayout supplyFrameLayout();

    /**
     * Allows subclasses to supply a custom {@link GLSurfaceView.Renderer}.
     *
     * @return The {@link GLSurfaceView.Renderer} to use.
     */
    protected abstract ARRenderer supplyRenderer();

    public void onFrameProcessed() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Create the camera preview
        if (getActivity() == null) {
            return;
        }
        mainLayout = supplyFrameLayout();
    }

    /**
     * @return Initialises the native code library if it is available.
     * The full path (in the filesystem) to the directory to be used by the
     * native routines as the base for relative references.
     * e.g. Activity.getContext().getCacheDir().getAbsolutePath()
     * or Activity.getContext().getFilesDir().getAbsolutePath()
     */
    protected abstract String getResDirPath();

    @Override
    public void onResume() {
        super.onResume();

        if (ARToolKit.getInstance().initialiseNativeWithOptions(getResDirPath()
                , pattSize
                , pattCountMax)) {
            renderer = supplyRenderer();
            if (renderer == null) {
                ArLog.e(TAG, "onStart(): Error: supplyRenderer did not return a renderer.");
                // No renderer supplied, use default, which does nothing
                renderer = new ARRenderer();
            }
        } else {
            showError("ar sdk 初始化失败：initialiseNativeWithOptions failed");
            return;
        }

        preview = new CaptureCameraPreview(getActivity(), this);
        ArLog.i(TAG, "onResume(): CaptureCameraPreview constructed");

        if (preview.gettingCameraAccessPermissionsFromUser())
            //No need to go further, must ask user to allow access to the camera first.
            return;

        // Create the GL view
        glView = new GLSurfaceView(getActivity());

        // Check if the system supports OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        if (supportsEs2) {
            ArLog.i(TAG, "onResume(): OpenGL ES 2.x is supported");

            if (renderer instanceof ARRendererGLES20) {
                // Request an OpenGL ES 2.0 compatible context.
                glView.setEGLContextClientVersion(2);
            } else {
                ArLog.w(TAG, "onResume(): OpenGL ES 2.x is supported but only a OpenGL 1.x renderer is available." +
                        " \n Use ARRendererGLES20 for ES 2.x support. \n Continuing with OpenGL 1.x.");
                glView.setEGLContextClientVersion(1);
            }
        } else {
            ArLog.i(TAG, "onResume(): Only OpenGL ES 1.x is supported");
            if (renderer instanceof ARRendererGLES20)
                throw new RuntimeException("Only OpenGL 1.x available but a OpenGL 2.x renderer was provided.");
            // This is where you could create an OpenGL ES 1.x compatible
            // renderer if you wanted to support both ES 1 and ES 2.
            glView.setEGLContextClientVersion(1);
        }

        glView.setEGLConfigChooser(new DefConfigChooser(supportsEs2));
        glView.getHolder().setFormat(PixelFormat.TRANSLUCENT); // Needs to be a translucent surface so the camera preview shows through.
        glView.setRenderer(renderer);
        glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY); // Only render when we have a frame (must call requestRender()).
        glView.setZOrderMediaOverlay(true); // Request that GL view's SurfaceView be on top of other SurfaceViews (including CameraPreview's SurfaceView).

        ArLog.i(TAG, "onResume(): GLSurfaceView created");

        // Add the views to the interface
        mainLayout.addView(preview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mainLayout.addView(glView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ArLog.i(TAG, "onResume(): Views added to main layout.");

        Log.i(TAG, "onResume(): Views added to main layout.");
        if (glView != null) glView.onResume();
    }

    @Override
    public void onPause() {
        Log.i(TAG, "onPause()");

        if (glView != null) {
            glView.onPause();
        }

        // System hardware must be released in onPause(), so it's available to
        // any incoming activity. Removing the CameraPreview will do this for the
        // camera. Also do it for the GLSurfaceView, since it serves no purpose
        // with the camera preview gone.
        mainLayout.removeAllViews();
        glView = null;
        preview = null;
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        ARToolKit.getInstance().stopAndFinal();
        super.onDestroyView();
    }

    private void handleUncaughtException(Thread thread, Throwable e) {
        Log.e(TAG, "handleUncaughtException(): exception type, " + e.toString());
        Log.e(TAG, "handleUncaughtException(): thread, \"" + thread.getName() + "\" exception, \"" + e.getMessage() + "\"");
        e.printStackTrace();
        return;
    }

    protected void showError(String error) {

    }
}
