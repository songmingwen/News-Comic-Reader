package com.song.sunset.activitys;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.R;
import com.song.sunset.utils.FileUtils;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.SdCardUtil;
import com.song.sunset.utils.loadingmanager.ProgressLayout;
import com.song.sunset.utils.volley.SampleVolleyFactory;

import java.io.File;

/**
 * Created by Song on 2016/9/2 0002.
 * Email:z53520@qq.com
 */
@Route(path = "/song/pic/scale")
public class ScalePicActivity extends BaseActivity {

    public static final String PIC_URL = "pic_url";
    public static final String PIC_ID = "pic_id";
    private String picUrl;
    private String picId;
    private SubsamplingScaleImageView imageView;
    private ProgressLayout progressLayout;
    private boolean hasCache = false;
    private File file;
    private View.OnClickListener mOnRetryListener;
    private TextView mSavePicTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale_pic);
        ScreenUtils.fullscreen(this, true);

        progressLayout = (ProgressLayout) findViewById(R.id.progress);
        progressLayout.showLoading();
        mOnRetryListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressLayout.showLoading();
                setBitmapFromNet();
            }
        };

        if (getIntent() != null) {
            picUrl = getIntent().getStringExtra(PIC_URL);
            picId = getIntent().getStringExtra(PIC_ID);
        }

        initView();
        if (!setBitmapFromLocation()) {
            setBitmapFromNet();
        } else {
            mSavePicTip.setVisibility(View.GONE);
        }
    }

    private void initView() {
        imageView = (SubsamplingScaleImageView) findViewById(R.id.id_pic_activity_image);
        imageView.setDoubleTapZoomDuration(200);
        imageView.setDoubleTapZoomScale(2.5f);
        imageView.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE);
        imageView.setMaxScale(5f);
        mSavePicTip = (TextView) findViewById(R.id.txt_save_pic_tip);
    }

    private void startFadeAnim() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.anim_save_pic_tip_fade);
        animator.setTarget(mSavePicTip);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mSavePicTip.setVisibility(View.GONE);
            }
        });
    }

    private boolean setBitmapFromLocation() {
        file = new File(SdCardUtil.getNormalSDCardPath() + "/Sunset/SavedCover", picId + ".jpg");
        if (file.exists()) {
            imageView.setImage(ImageSource.uri(Uri.fromFile(file)));
            progressLayout.showContent();
            setListener(null);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setBitmapFromNet() {
        hasCache = false;
        RequestQueue queue = SampleVolleyFactory.getRequestQueue(this);
        ImageRequest imageRequest = new ImageRequest(picUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        hasCache = true;
                        progressLayout.showContent();
                        fadeAnimDelay();
                        if (response != null) {
                            imageView.setImage(ImageSource.bitmap(response));
                            setListener(response);
                        } else {
                            progressLayout.showError(mOnRetryListener);
                        }
                    }
                }, 2048, 2048, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (hasCache)
                    progressLayout.showContent();
                else {
                    progressLayout.showError(mOnRetryListener);
                }
            }
        });
        imageRequest.setRetryPolicy(new DefaultRetryPolicy());
        queue.add(imageRequest);
    }

    private void fadeAnimDelay() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startFadeAnim();
            }
        }, 1000);
    }

    private void setListener(final Bitmap response) {
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (response != null) {
                    if (SdCardUtil.isSdCardAvailable()) {
                        if (file != null && file.exists()) {
                            Toast.makeText(getApplication(), "图片已保存", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        savePic(response);
                    }
                } else {
                    Toast.makeText(getApplication(), "图片已保存", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    private void savePic(final Bitmap response) {
        new Thread() {
            @Override
            public void run() {
                if (FileUtils.saveFile(response, "/Sunset/SavedCover", picId + ".jpg")) {
                    showResult("图片保存成功");
                } else {
                    showResult("图片保存失败");
                }
                super.run();
            }
        }.start();
    }

    private void showResult(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), s, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void start(Context context, String ori, String comicId) {
        Intent intent = new Intent(context, ScalePicActivity.class);
        intent.putExtra(PIC_URL, ori);
        intent.putExtra(PIC_ID, comicId);
        context.startActivity(intent);
    }
}
