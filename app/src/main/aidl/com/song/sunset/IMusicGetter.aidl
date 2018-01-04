// IGetDataFromNet.aidl
package com.song.sunset;

import com.song.sunset.beans.MusicInfo;
import com.song.sunset.IMusicCallBackListener;

// Declare any non-default types here with import statements

interface IMusicGetter {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getMusicList(IMusicCallBackListener listener);
}
