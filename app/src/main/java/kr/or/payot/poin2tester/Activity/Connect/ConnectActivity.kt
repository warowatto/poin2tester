package kr.or.payot.poin2tester.Activity.Connect

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_connect.*
import kr.or.payot.poin2tester.Activity.RootActivity
import kr.or.payot.poin2tester.App
import kr.or.payot.poin2tester.DI.Component.DaggerConnectActivityComponent
import kr.or.payot.poin2tester.DI.Module.ConnectActivityModule
import kr.or.payot.poin2tester.R
import javax.inject.Inject

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class ConnectActivity : RootActivity() {

    @Inject
    lateinit var presenter: ConnectContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        DaggerConnectActivityComponent.builder()
                .applicationComponent(App.component)
                .connectActivityModule(ConnectActivityModule(this, intent.getParcelableExtra("device")))
                .build().inject(this)

        btnSend.setOnClickListener {
            val message = editCommend.text.toString()
            if (message.isNullOrEmpty()) {
                val rootView = window.decorView.rootView
                Snackbar.make(rootView, "명령을 입력해 주세요", Snackbar.LENGTH_SHORT).show()
            } else {
                presenter.sendMessage(message)
                // presenter.insertCoin()
            }
        }
    }
}