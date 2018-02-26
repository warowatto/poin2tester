package kr.or.payot.poin2tester.Activity

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposables
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject

/**
 * Created by yongheekim on 2018. 1. 26..
 */
open class RootActivity : AppCompatActivity() {

    private val permissionEvent = PublishSubject.create<List<Pair<String, Boolean>>>()
    val disposeable:CompositeDisposable = CompositeDisposable()

    fun permissionCheck(permissions: Array<String>): Single<List<Pair<String, Boolean>>> {
        ActivityCompat.requestPermissions(this, permissions, 4000)

        return permissionEvent.take(1).singleOrError()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val permissionObserver = permissions.toObservable()
        val grantObserver = grantResults.toObservable().map { it == PackageManager.PERMISSION_GRANTED }
        val list =Observable.zip(permissionObserver, grantObserver, BiFunction { p: String, g: Boolean -> p to g })
                .toList().blockingGet()

        permissionEvent.onNext(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeable.dispose()
    }

}