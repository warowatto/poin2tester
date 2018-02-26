// DeviceServiceHandler.aidl
package kr.or.payot.poin2tester.Structuer;

import android.bluetooth.BluetoothDevice;
// Declare any non-default types here with import statements

interface DeviceServiceHandler {
    void connect(in BluetoothDevice device);

    void send(String meg);

    void insertCoin(int coin);

    void disConnect();
}
