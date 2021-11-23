package com.lovesosoi.serialtools;

import android.serialport.SerialBaseProvider;

public class AProvider extends SerialBaseProvider {

    @Override
    public void onDataReceived(byte[] buffer, int size) {
        if (iSetRecvListener != null) {
            iSetRecvListener.recv(buffer, size);
        }

    }
}
