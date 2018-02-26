package kr.or.payot.poin2tester.Activity.Scan

import android.bluetooth.BluetoothDevice

/**
 * Created by yongheekim on 2018. 1. 26..
 */
interface ScanContract {

    interface View {
        fun scanning(state: Boolean)

        fun findDevice(bluetoothDevice: BluetoothDevice)

        fun notFoundDevice()

        fun errorPermission(permissions: List<String>)
    }

    interface Presenter {
        fun scan(name: String)
    }
}