package android.serialport;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public abstract class SerialBaseProvider {

    protected static SerialPort mSerialPort;
    protected OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private static String TAG = "SerialProvider";
    private int mBuffer = 64;

    public SerialPort initSerialPort(String s, int b)
            throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            mSerialPort = SerialPort //
                    .newBuilder(s, b) // 串口地址地址，波特率
                    .parity(2) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
                    .dataBits(8) // 数据位,默认8；可选值为5~8
                    .stopBits(1) // 停止位，默认1；1:1位停止位；2:2位停止位
                    .build();
            initOther();
        }
        return mSerialPort;
    }

    public SerialPort initSerialPort(SerialPort.Builder builder) throws IOException {
        if (mSerialPort == null) {
            mSerialPort = SerialPort //
                    .newBuilder(builder.getDevice(), builder.getBaudrate()) // 串口地址地址，波特率
                    .parity(builder.getParity()) // 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
                    .dataBits(builder.getDataBits()) // 数据位,默认8；可选值为5~8
                    .stopBits(builder.getStopBits()) // 停止位，默认1；1:1位停止位；2:2位停止位
                    .flags(builder.getFlags()) // 停止位，默认1；1:1位停止位；2:2位停止位
                    .build();
            initOther();
        }
        return mSerialPort;
    }

    public void setBuffer(int buffer) {
        mBuffer = buffer;

    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[mBuffer];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        onDataReceived(buffer, size);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private void initOther() {

        try {
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            Log.e(TAG, "SecurityException" + e.getMessage());
        } catch (InvalidParameterException e) {
            Log.e(TAG, "InvalidParameterException" + e.getMessage());
        }
    }

    public void close() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    public interface ISetRecv {
        void recv(byte[] buffer, int size);
    }

    public ISetRecv iSetRecvListener;

    public ISetRecv getiSetRecvListener() {
        return iSetRecvListener;
    }

    public void setiSetRecvListener(ISetRecv iSetRecvListener) {
        this.iSetRecvListener = iSetRecvListener;
    }

    public abstract void onDataReceived(byte[] buffer, int size);
}
