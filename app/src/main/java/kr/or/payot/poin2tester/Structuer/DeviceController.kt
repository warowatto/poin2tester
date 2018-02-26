package kr.or.payot.poin2tester.Structuer

import android.bluetooth.BluetoothDevice

/**
 * Created by yongheekim on 2018. 1. 25..
 */
interface DeviceController {

    fun isConnect(): Boolean

    fun connect(bluetoothDevice: BluetoothDevice)

    fun sendMessage(cmd: String)

    fun disconnect()
}