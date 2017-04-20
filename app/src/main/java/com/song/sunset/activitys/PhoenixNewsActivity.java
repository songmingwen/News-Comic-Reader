package com.song.sunset.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.song.core.statusbar.StatusBarUtil;
import com.song.sunset.R;
import com.song.sunset.activitys.base.BaseActivity;
import com.song.sunset.utils.ScreenUtils;
import com.song.sunset.utils.loadingmanager.LoadingAndRetryManager;

/**
 * Created by Song on 2017/4/6 0006.
 * E-mail: z53520@qq.com
 */

public class PhoenixNewsActivity extends BaseActivity {

    public static final String PHOENIX_NEWS_URL = "phoenix_news_url";
    private String url;
    private ProgressBar progressBar;
    private LoadingAndRetryManager mLoadingAndRetryManager;
    private WebView mWebView;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, PhoenixNewsActivity.class);
        intent.putExtra(PHOENIX_NEWS_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.fullscreen(this, true);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        setContentView(R.layout.activity_phoenix_news_layout);

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, null);
        mLoadingAndRetryManager.showLoading();
        if (getIntent() != null) {
            url = getIntent().getStringExtra(PHOENIX_NEWS_URL);
        }

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mWebView = (WebView) findViewById(R.id.web_view);
        initWebView(mWebView);
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(this, "网页暂时无法打开", Toast.LENGTH_SHORT).show();
            mLoadingAndRetryManager.showEmpty();
            return;
        }
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressedSupport() {
        if (mWebView != null && mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressedSupport();
    }

    @Override
    protected void onDestroy() {
        if (null != mWebView) {
            mWebView.removeAllViews();
            mWebView.destroy();
        }
        super.onDestroy();
    }

    protected void initWebView(WebView webView) {
        initSettings(webView.getSettings());
        webView.setScrollbarFadingEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            webView.getSettings().setTextZoom(100);
        }
        webView.setWebChromeClient(new WebChromeClientCompat());
        webView.setWebViewClient(new WebDetailClient());
    }

    protected void initSettings(WebSettings settings) {
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        settings.setSupportZoom(false);
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
    }

    private class WebChromeClientCompat extends WebChromeClient {

        public void onProgressChanged(WebView view, int progress) {
            if (progress < 100 && progressBar.getVisibility() == ProgressBar.GONE) {
                progressBar.setVisibility(ProgressBar.VISIBLE);
            }
            if (progress >= 60) {
                mLoadingAndRetryManager.showContent();
            }
            progressBar.setProgress(progress);
            if (progress == 100) {
                progressBar.setVisibility(ProgressBar.GONE);
            }
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin,
                                                       GeolocationPermissions.Callback callback) {
            if (callback != null) {
                callback.invoke(origin, true, false);
            }
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    private class WebDetailClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.stopLoading();
            view.clearView();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();//解决https网址webView无法展示的问题
        }
    }
}
