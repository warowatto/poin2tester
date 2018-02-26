package kr.or.payot.poin2tester.DI.Component

import dagger.Component
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import kr.or.payot.poin2tester.DI.PerService
import kr.or.payot.poin2tester.Structuer.impl.PoinDeviceService

/**
 * Created by yongheekim on 2018. 1. 26..
 */

@PerService
@Component(modules = arrayOf(BluetoothModule::class))
interface DeviceServiceComponent {

    fun inject(service: PoinDeviceService)
}