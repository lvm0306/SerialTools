package com.lovesosoi.serialtools;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.serialport.SerialBaseProvider;
import android.serialport.SerialPort;
import android.util.Log;
import android.view.View;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    SerialPort serialPort;

    public final static byte[] CLOSE_LIGHT_CMD = new byte[]{0x55, 0x00, 0x02, 0x02, 0x00, 0x57};
    public final static byte[] OPEN_LIGHT_CMD = new byte[]{0x55, 0x00, 0x02, 0x02, 0x01, 0x58};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            AProvider aProvider = new AProvider();
//            serialPort = aProvider.initSerialPort("/dev/ttyS3", 115200);
//            aProvider.setiSetRecvListener(new SerialBaseProvider.ISetRecv() {
//                @Override
//                public void recv(byte[] buffer, int size) {
//
//                }
//            });
            SerialPort.Builder builder = new SerialPort.Builder("/dev/ttyS3", 115200);
            builder.stopBits(1)
                    .dataBits(8)
                    .parity(2)
                    .flags(0);
            serialPort = aProvider.initSerialPort(builder);
            aProvider.setiSetRecvListener(new SerialBaseProvider.ISetRecv() {
                @Override
                public void recv(byte[] buffer, int size) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void openLight(View view) {
        try {
            Log.e(TAG, "openLight: " + OPEN_LIGHT_CMD);
            serialPort.getOutputStream().write(OPEN_LIGHT_CMD);
        } catch (IOException e) {
            Log.e(TAG, "openLight: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeLight(View view) {
        try {
            Log.e(TAG, "closeLight: " + OPEN_LIGHT_CMD);
            serialPort.getOutputStream().write(CLOSE_LIGHT_CMD);
        } catch (IOException e) {
            Log.e(TAG, "closeLight: " + e.getMessage());

            e.printStackTrace();
        }
    }

}
