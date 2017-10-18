package com.song.sunset.services.impl;

import android.os.RemoteException;

import com.song.sunset.ICompute;

public class ComputeImpl extends ICompute.Stub {

    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }

}
