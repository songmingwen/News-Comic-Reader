package com.song.video;

import android.content.Context;

import com.song.sunset.utils.AppConfig;

/**
 * Created by Song on 2017/4/27 0027.
 * E-mail: z53520@qq.com
 */
public class VideoManager {

    private NormalVideoPlayer mVideoPlayer;

    private VideoManager() {
    }

    private static VideoManager sInstance;

    public static synchronized VideoManager instance() {
        if (sInstance == null) {
            sInstance = new VideoManager();
        }
        return sInstance;
    }

    public NormalVideoPlayer getCurrentNormalVideoPlayer(Context context) {
        if (mVideoPlayer == null) {
            mVideoPlayer = new NormalVideoPlayer(context);
        }
        return mVideoPlayer;
    }

    public void setCurrentNromalVideoPlayer(NormalVideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseNormalVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }

    public void suspendNormalVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    public void resumeNormalVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    public void releaseNormalVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }

    public boolean onBackPressd() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
