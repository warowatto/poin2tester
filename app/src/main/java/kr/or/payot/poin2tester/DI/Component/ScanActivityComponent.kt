package kr.or.payot.poin2tester.DI.Component

import dagger.Component
import kr.or.payot.poin2tester.Activity.Scan.ScanActivity
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import kr.or.payot.poin2tester.DI.Module.ScanActivityModule
import kr.or.payot.poin2tester.DI.PerActivity

/**
 * Created by yongheekim on 2018. 1. 25..
 */

@PerActivity
@Component(modules = arrayOf(BluetoothModule::class, ScanActivityModule::class),
        dependencies = arrayOf(ApplicationComponent::class))
interface ScanActivityComponent {

    fun inject(activity: ScanActivity)
}