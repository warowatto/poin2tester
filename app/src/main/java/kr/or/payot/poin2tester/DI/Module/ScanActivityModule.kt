package kr.or.payot.poin2tester.DI.Module

import dagger.Module
import dagger.Provides
import kr.or.payot.poin2tester.Activity.Scan.ScanActivity
import kr.or.payot.poin2tester.Activity.Scan.ScanActivityPresenter
import kr.or.payot.poin2tester.Activity.Scan.ScanActivityView
import kr.or.payot.poin2tester.Activity.Scan.ScanContract
import kr.or.payot.poin2tester.DI.PerActivity
import kr.or.payot.poin2tester.Structuer.DeviceScanner

/**
 * Created by yongheekim on 2018. 1. 26..
 */

@Module(includes = arrayOf(BluetoothModule::class))
class ScanActivityModule(val activity: ScanActivity) {

    @PerActivity
    @Provides
    fun view(): ScanContract.View = ScanActivityView(activity)

    @PerActivity
    @Provides
    fun persenter(view: ScanContract.View, deviceScanner: DeviceScanner): ScanContract.Presenter
            = ScanActivityPresenter(activity, view, deviceScanner)
}