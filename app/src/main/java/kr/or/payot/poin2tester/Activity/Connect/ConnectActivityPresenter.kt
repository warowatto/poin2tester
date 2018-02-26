package kr.or.payot.poin2tester.Activity.Connect

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.bluetooth.BluetoothDevice
import android.content.*
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.github.karczews.rxbroadcastreceiver.RxBroadcastReceivers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_connect.view.*
import kr.or.payot.poin2tester.R
import kr.or.payot.poin2tester.Structuer.DeviceServiceHandler
import kr.or.payot.poin2tester.Structuer.impl.PoinDeviceService

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class ConnectActivityPresenter
constructor(val activity: ConnectActivity, val bluetoothDevice: BluetoothDevice, view: ConnectContract.View) :
        ConnectContract.Presenter, LifecycleObserver,
        ConnectContract.View by view {


    // Dolmedo.kr@gmail.com
    val dispose: CompositeDisposable = CompositeDisposable()

    val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {

        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            device = DeviceServiceHandler.Stub.asInterface(p1)
            device?.connect(bluetoothDevice)
        }
    }

    var device: DeviceServiceHandler? = null



    init {
        activity.lifecycle.addObserver(this)

        RxBroadcastReceivers.fromIntentFilter(activity, IntentFilter("kr.or.payot.poin"))
                .doOnNext { Log.d("방송수신", "메시지 안받나요?") }
                .map { it.getStringArrayExtra("message") }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe { reciveMessage(it) }
                .apply { activity.disposeable.add(this) }

        activity.findViewById<Button>(R.id.btn_end).setOnClickListener {
            disConnect()
        }
    }

    override fun insertCoin() {
        device?.insertCoin(1000)
    }

    override fun sendMessage(message: String) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun deviceBindService() {
        activity.applicationContext.bindService(Intent(activity, PoinDeviceService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun disConnect() {
        device?.disConnect()
        dispose.dispose()
        activity.applicationContext.unbindService(serviceConnection)
    }

}