package com.imgo.arcard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.imgo.arcard.Config.ConfigHolder;
import com.imgo.arcard.Config.DrawStatusListener;

import org.artoolkit.ar.base.ARBaseFragment;
import org.artoolkit.ar.base.log.ArLog;
import org.artoolkit.ar.base.rendering.ARRenderer;

/**
 * Desc:
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/5/15 10:54
 */
public class ARFragment extends ARBaseFragment implements DrawStatusListener {

    private ProgressDialog pDialog;

    private FrameLayout supplyFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigHolder.getInstance().setListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ar, container, false);
        supplyFrameLayout = rootView.findViewById(R.id.container);
        return rootView;
    }

    @Override
    synchronized public void onFrameProcessed() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    protected String getResDirPath() {
        return getActivity().getCacheDir().getAbsolutePath();
    }

    @Override
    protected FrameLayout supplyFrameLayout() {
        return supplyFrameLayout;
    }

    @Override
    protected ARRenderer supplyRenderer() {
        return new SimpleRenderer();
    }

    @Override
    protected void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage(getResources().getString(R.string.loading_text_fragment));
        pDialog.setTitle(getResources().getString(R.string.loading_title_fragment));
        pDialog.setIndeterminate(true);
        pDialog.show();
    }

    @Override
    public void onPause() {
        ConfigHolder.getInstance().reset();
        super.onPause();
    }

    @Override
    public void onDrawing(String canvasId) {
        ArLog.i(TAG, "canvasID = " + canvasId);
    }

    @Override
    public void onInvisible() {
        ArLog.i(TAG, "canvas onInvisible");
    }
}
