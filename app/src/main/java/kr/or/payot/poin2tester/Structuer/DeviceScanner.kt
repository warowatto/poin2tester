package kr.or.payot.poin2tester.Structuer

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import io.reactivex.Observable

/**
 * Created by yongheekim on 2018. 1. 25..
 */
interface DeviceScanner {

    fun scanDevice(name: String): Observable<BluetoothDevice>

    fun scanStop(callback: ScanCallback)
}