# SerialTools
SerialTools

[Fork](https://github.com/licheedev/Android-SerialPort-API)

## Installation & Usage

moudel 依赖serialport

**Usage**

```java
根据你使用情况去 创建你得合理 Listener
public class AProvider extends SerialBaseProvider {
    @Override
    public void onDataReceived(byte[] buffer, int size) {
        if (iSetRecvListener != null) {
            iSetRecvListener.recv(buffer, size);
        }
    }
}

serialPort = aProvider.initSerialPort("/dev/ttyS3", 115200);
aProvider.setiSetRecvListener(new SerialBaseProvider.ISetRecv() {
    @Override
    public void recv(byte[] buffer, int size) {

    }
});

如果你的校验位、数据位和停止位 不是默认的 用第二个初始化
serialPort = aProvider.initSerialPort(build);

```

```java
import android.serialport.SerialPort;
```

**`su` path**

```java
// su默认路径为 "/system/bin/su"
// The default path of su is "/system/bin/su"
// 可通过此方法修改
// If the path is different then change it using this
SerialPort.setSuPath("/system/xbin/su");
```


```java
// 可选配置数据位、校验位、停止位 - 7E2(7数据位、偶校验、2停止位)
// 校验位；0:无校验位(NONE，默认)；1:奇校验位(ODD);2:偶校验位(EVEN)
// 数据位,默认8；可选值为5~8
// 停止位，默认1；1:1位停止位；2:2位停止位
    
// read/write to serial port - needs to be in different thread!
InputStream in = serialPort.getInputStream();
OutputStream out = serialPort.getOutputStream();

// close
serialPort.tryClose();
```
