package kr.or.payot.poin2tester.DI.Module

import android.bluetooth.BluetoothAdapter
import android.content.Context
import dagger.Module
import dagger.Provides
import kr.or.payot.poin2tester.DI.PerActivity
import kr.or.payot.poin2tester.DI.PerService
import kr.or.payot.poin2tester.Structuer.CommendBuilder
import kr.or.payot.poin2tester.Structuer.CommendEncryption
import kr.or.payot.poin2tester.Structuer.DeviceController
import kr.or.payot.poin2tester.Structuer.DeviceScanner
import kr.or.payot.poin2tester.Structuer.impl.PoinCommendBuilder
import kr.or.payot.poin2tester.Structuer.impl.PoinCommendEncryption
import kr.or.payot.poin2tester.Structuer.impl.PoinDeviceController
import kr.or.payot.poin2tester.Structuer.impl.PoinDeviceScanner

/**
 * Created by yongheekim on 2018. 1. 25..
 */

@Module
class BluetoothModule(val context: Context) {

    @Provides
    fun bluetoothAdapter(): BluetoothAdapter
            = BluetoothAdapter.getDefaultAdapter()

    @Provides
    fun bluetoothScanner(bluetoothAdapter: BluetoothAdapter): DeviceScanner
            = PoinDeviceScanner(bluetoothAdapter)

    @PerService
    @Provides
    fun commendEncrypt(): CommendEncryption = PoinCommendEncryption()

    @PerService
    @Provides
    fun commendBuilder(encryption: CommendEncryption): CommendBuilder = PoinCommendBuilder(encryption)

    @PerService
    @Provides
    fun bluetootController(commendBuilder: CommendBuilder): DeviceController =
            PoinDeviceController(context, commendBuilder)
}