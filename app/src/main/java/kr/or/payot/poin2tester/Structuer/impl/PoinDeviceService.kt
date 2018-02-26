package kr.or.payot.poin2tester.Structuer.impl

import android.app.Service
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import kr.or.payot.poin2tester.DI.Component.DaggerDeviceServiceComponent
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import kr.or.payot.poin2tester.Structuer.DeviceController
import kr.or.payot.poin2tester.Structuer.DeviceServiceHandler
import javax.inject.Inject
import kotlin.math.log

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class PoinDeviceService : Service() {

    @Inject
    lateinit var deviceController: DeviceController

    override fun onCreate() {
        super.onCreate()
        Log.d("온크리에이트가 되긴합니까?", "예")
        DaggerDeviceServiceComponent.builder()
                .bluetoothModule(BluetoothModule(this))
                .build().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("온스타트커맨드 되긴합니까?", "예")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder =
            object : DeviceServiceHandler.Stub() {
                override fun send(meg: String?) {
                    deviceController.sendMessage(meg!!)
                }

                override fun connect(device: BluetoothDevice?) {
                    device?.let { deviceController.connect(it) }
                }

                override fun insertCoin(coin: Int) {
                    deviceController.sendMessage("CMD S 2 ${coin}")
                }

                override fun disConnect() {
                    deviceController.disconnect()
                    stopSelf()
                }
            }

    override fun unbindService(conn: ServiceConnection?) {
        super.unbindService(conn)
        stopSelf()
    }

}