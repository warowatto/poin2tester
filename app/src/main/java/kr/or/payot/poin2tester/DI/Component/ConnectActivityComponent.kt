package kr.or.payot.poin2tester.DI.Component

import dagger.Component
import kr.or.payot.poin2tester.Activity.Connect.ConnectActivity
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import kr.or.payot.poin2tester.DI.Module.ConnectActivityModule
import kr.or.payot.poin2tester.DI.PerActivity

/**
 * Created by yongheekim on 2018. 1. 26..
 */

@PerActivity
@Component(modules = arrayOf(ConnectActivityModule::class, BluetoothModule::class),
        dependencies = arrayOf(ApplicationComponent::class))
interface ConnectActivityComponent {
    fun inject(activity: ConnectActivity)
}