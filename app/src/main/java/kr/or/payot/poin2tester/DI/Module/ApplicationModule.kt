package kr.or.payot.poin2tester.DI.Module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by yongheekim on 2018. 1. 25..
 */

@Singleton
@Module
class ApplicationModule(val application: Application) {

    @Singleton
    @Provides
    fun context(): Context = application.applicationContext
}