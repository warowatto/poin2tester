package kr.or.payot.poin2tester.Activity.Scan

import android.bluetooth.BluetoothDevice
import android.widget.Toast

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class ScanActivityView(val activity: ScanActivity) : ScanContract.View {
    override fun scanning(state: Boolean) {
        val message = if (state) "장비를 찾고 있습니다" else "찾기를 종료합니다"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun findDevice(bluetoothDevice: BluetoothDevice) {
        val message = "장치를 찾았습니다 : ${bluetoothDevice.name}"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun notFoundDevice() {
        val message = "장치를 찾지 못하였습니다"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun errorPermission(permissions: List<String>) {
        val message = "장치 허가 오류 : ${permissions}"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }
}