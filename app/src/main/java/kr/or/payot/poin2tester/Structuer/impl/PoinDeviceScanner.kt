package kr.or.payot.poin2tester.Structuer.impl

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.util.Log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kr.or.payot.poin2tester.Structuer.DeviceScanner
import java.util.concurrent.TimeUnit

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class PoinDeviceScanner(val bluetoothAdapter: BluetoothAdapter) : DeviceScanner {

    override fun scanDevice(name: String): Observable<BluetoothDevice> {
        return Observable.create<BluetoothDevice> { e ->
            val callback = object : ScanCallback() {
                override fun onScanFailed(errorCode: Int) {
                    super.onScanFailed(errorCode)
                    e.onError(NullPointerException("스캔 에러발생 : ${errorCode}"))
                }

                override fun onScanResult(callbackType: Int, result: ScanResult?) {
                    super.onScanResult(callbackType, result)
                    Log.d("장비를 찾음", result?.device?.name)
                    result?.device?.let {
                        if (it.name == name) e.onNext(it)
                    }
                }
            }

            bluetoothAdapter.bluetoothLeScanner.startScan(callback)
            Observable.just("")
                    .delay(5, TimeUnit.SECONDS, Schedulers.computation())
                    .subscribe { scanStop(callback); e.onComplete() }
        }
    }


    override fun scanStop(scanCallback: ScanCallback) {
        bluetoothAdapter.bluetoothLeScanner.stopScan(scanCallback)
    }
}