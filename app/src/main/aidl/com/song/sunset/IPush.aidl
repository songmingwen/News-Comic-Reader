// IHandler.aidl
package com.song.sunset;

// Declare any non-default types here with import statements
import com.song.sunset.beans.MusicInfo;

interface IPush {
    void connect();

    void sendMusicInfo(in MusicInfo musicInfo);
}
