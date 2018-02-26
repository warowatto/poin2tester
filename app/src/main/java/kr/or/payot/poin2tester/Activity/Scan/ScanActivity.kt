package kr.or.payot.poin2tester.Activity.Scan

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scan.*
import kr.or.payot.poin2tester.Activity.RootActivity
import kr.or.payot.poin2tester.App
import kr.or.payot.poin2tester.DI.Component.DaggerScanActivityComponent
import kr.or.payot.poin2tester.DI.Module.BluetoothModule
import kr.or.payot.poin2tester.DI.Module.ScanActivityModule
import kr.or.payot.poin2tester.R
import javax.inject.Inject

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class ScanActivity : RootActivity() {

    @Inject
    lateinit var presenter: ScanContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        DaggerScanActivityComponent.builder()
                .applicationComponent(App.component)
                .bluetoothModule(BluetoothModule(this))
                .scanActivityModule(ScanActivityModule(this))
                .build().inject(this)

        btn_find.setOnClickListener {
            val name = edit_mac.text.toString()
            presenter.scan(name)
        }
    }
}