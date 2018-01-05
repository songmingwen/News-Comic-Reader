// IMusicCallBackListener.aidl
package com.song.sunset;

import com.song.sunset.beans.MusicInfo;
// Declare any non-default types here with import statements

interface IMusicCallBackListener {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void success(in List<MusicInfo> list);
    void failure();
}
