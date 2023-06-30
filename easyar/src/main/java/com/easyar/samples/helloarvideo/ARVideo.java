//================================================================================================================================
//
// Copyright (c) 2015-2022 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.easyar.samples.helloarvideo;

import android.util.Log;

import cn.easyar.DelayedCallbackScheduler;
import cn.easyar.FunctorOfVoidFromVideoStatus;
import cn.easyar.StorageType;
import cn.easyar.TextureId;
import cn.easyar.VideoPlayer;
import cn.easyar.VideoStatus;
import cn.easyar.VideoType;

public class ARVideo {
    private VideoPlayer player;
    private boolean prepared;
    private boolean found;
    private String path;

    public ARVideo() {
        player = new VideoPlayer();
        prepared = false;
        found = false;
    }

    public void dispose() {
        player.close();
    }

    public void openVideoFile(String path, int texid, DelayedCallbackScheduler scheduler) {
        this.path = path;
        player.setRenderTexture(TextureId.fromInt(texid));
        player.setVideoType(VideoType.Normal);
        player.open(path, StorageType.Assets, scheduler, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    public void openTransparentVideoFile(String path, int texid, DelayedCallbackScheduler scheduler) {
        this.path = path;
        player.setRenderTexture(TextureId.fromInt(texid));
        player.setVideoType(VideoType.TransparentSideBySide);
        player.open(path, StorageType.Assets, scheduler, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    public void openStreamingVideo(String url, int texid, DelayedCallbackScheduler scheduler) {
        this.path = url;
        player.setRenderTexture(TextureId.fromInt(texid));
        player.setVideoType(VideoType.Normal);
        player.open(url, StorageType.Absolute, scheduler, new FunctorOfVoidFromVideoStatus() {
            @Override
            public void invoke(int status) {
                setVideoStatus(status);
            }
        });
    }

    public void setVideoStatus(int status) {
        Log.i("HelloAR", "video: " + path + " (" + Integer.toString(status) + ")");
        if (status == VideoStatus.Ready) {
            prepared = true;
            if (found) {
                player.play();
            }
        } else if (status == VideoStatus.Completed) {
            if (found) {
                player.play();
            }
        }
    }

    public void onFound() {
        found = true;
        if (prepared) {
            player.play();
        }
    }

    public void onLost() {
        found = false;
        if (prepared) {
            player.pause();
        }
    }

    public boolean isRenderTextureAvailable() {
        return player.isRenderTextureAvailable();
    }

    public void update() {
        player.updateFrame();
    }
}
