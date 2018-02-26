package kr.or.payot.poin2tester.DI.Component

import android.content.Context
import dagger.Component
import kr.or.payot.poin2tester.DI.Module.ApplicationModule
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import javax.inject.Singleton

/**
 * Created by yongheekim on 2018. 1. 25..
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class, BluetoothModule::class))
interface ApplicationComponent {

    fun context(): Context
}