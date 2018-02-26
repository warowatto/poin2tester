package kr.or.payot.poin2tester

import android.app.Application
import kr.or.payot.poin2tester.DI.Component.ApplicationComponent
import kr.or.payot.poin2tester.DI.Component.DaggerApplicationComponent
import kr.or.payot.poin2tester.DI.Module.ApplicationModule

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class App : Application() {

    companion object {
        lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}