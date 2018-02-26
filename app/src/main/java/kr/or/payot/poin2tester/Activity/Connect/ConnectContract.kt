package kr.or.payot.poin2tester.Activity.Connect

/**
 * Created by yongheekim on 2018. 1. 26..
 */
interface ConnectContract {

    interface View {
        fun deviceReady()

        fun deviceDisconnect()

        fun reciveMessage(recive: Array<String>)
    }

    interface Presenter {
        fun insertCoin()

        fun sendMessage(message: String)
    }
}