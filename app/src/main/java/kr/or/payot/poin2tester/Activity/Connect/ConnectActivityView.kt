package kr.or.payot.poin2tester.Activity.Connect

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kr.or.payot.poin2tester.R

/**
 * Created by yongheekim on 2018. 1. 26..
 */
class ConnectActivityView(val activity: ConnectActivity): ConnectContract.View {

    val recyclerView:RecyclerView
    val adapter:ConnectActivtyLogAdapter

    init {
        recyclerView = activity.findViewById(R.id.recyclerView)
        adapter = ConnectActivtyLogAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }


    override fun deviceReady() {
        val message = "장치가 연결되었습니다"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun deviceDisconnect() {
        val message = "장치가 연결 헤제 되었습니다"
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun reciveMessage(recive: Array<String>) {
        adapter.items.add(recive)
        adapter.notifyDataSetChanged()
    }
}