package kr.or.payot.poin2tester.DI.Module

import android.bluetooth.BluetoothDevice
import dagger.Module
import dagger.Provides
import kr.or.payot.poin2tester.Activity.Connect.ConnectActivity
import kr.or.payot.poin2tester.Activity.Connect.ConnectActivityPresenter
import kr.or.payot.poin2tester.Activity.Connect.ConnectActivityView
import kr.or.payot.poin2tester.Activity.Connect.ConnectContract
import kr.or.payot.poin2tester.DI.PerActivity

/**
 * Created by yongheekim on 2018. 1. 26..
 */

@Module
class ConnectActivityModule(val activity: ConnectActivity, val bluetoothDevice: BluetoothDevice) {

    @PerActivity
    @Provides
    fun view(): ConnectContract.View = ConnectActivityView(activity)

    @PerActivity
    @Provides
    fun presenter(view: ConnectContract.View): ConnectContract.Presenter =
            ConnectActivityPresenter(activity, bluetoothDevice, view)
}