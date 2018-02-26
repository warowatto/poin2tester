package kr.or.payot.poin2tester.Activity.Scan

import android.arch.lifecycle.LifecycleObserver
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kr.or.payot.poin2tester.Activity.Connect.ConnectActivity
import kr.or.payot.poin2tester.Structuer.DeviceScanner
import java.util.concurrent.TimeUnit

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class ScanActivityPresenter
constructor(val activity: ScanActivity, view: ScanContract.View, deviceScanner: DeviceScanner) :
        ScanContract.Presenter, LifecycleObserver,
        DeviceScanner by deviceScanner,
        ScanContract.View by view {

    override fun scan(name: String) {
        scanning(true)
//        scanDevice(name)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        {
//                            findDevice(it)
//                            val intent = Intent(activity, ConnectActivity::class.java).apply { putExtra("device", it) }
//                            scanning(false)
//                            activity.startActivity(intent)
//                        },
//                        {
//                            notFoundDevice()
//                            scanning(false)
//                            it.printStackTrace()
//                        },
//                        { scanning(false) }
//                )

        val adapter = BluetoothAdapter.getDefaultAdapter()

        val callback: BluetoothAdapter.LeScanCallback = object : BluetoothAdapter.LeScanCallback {
            override fun onLeScan(device: BluetoothDevice?, p1: Int, p2: ByteArray?) {
                val deviceName: String? = device?.name
                if (name.equals(deviceName)) {
                    val intent = Intent(activity, ConnectActivity::class.java).apply { putExtra("device", device) }
                    activity.startActivity(intent)
                    adapter.stopLeScan(this)
                }
            }
        }

        adapter.startLeScan(callback)
        Observable.interval(5, TimeUnit.SECONDS, Schedulers.computation())
                .take(1)
                .subscribe { adapter.stopLeScan(callback) }


//        val callback = object : ScanCallback() {
//            override fun onScanResult(callbackType: Int, result: ScanResult?) {
//                super.onScanResult(callbackType, result)
//                adapter.bluetoothLeScanner.stopScan(this)
//                val intent = Intent(activity, ConnectActivity::class.java).apply { putExtra("device", result?.device) }
//                activity.startActivity(intent)
//            }
//        }
//        adapter.bluetoothLeScanner.startScan(callback)

    }

    fun permissionCheck(): Boolean {
        val permissions = arrayOf(
                android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.BLUETOOTH_ADMIN,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        )

        val failPremission = activity.permissionCheck(permissions)
                .toObservable()
                .flatMap { it.toObservable() }
                .filter { !it.second }
                .map { it.first }
                .toList().blockingGet()

        errorPermission(failPremission)

        return !failPremission.isNotEmpty()
    }
}