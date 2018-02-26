package kr.or.payot.poin2tester.Activity.Connect

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by yongheekim on 2018. 2. 1..
 */
class ConnectActivtyLogAdapter : RecyclerView.Adapter<ListItemView>() {

    val items: ArrayList<Array<String>> = arrayListOf()

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListItemView {
        val view = LayoutInflater.from(parent?.context)
                .inflate(android.R.layout.simple_list_item_2, parent, false)
        return ListItemView(view)
    }

    override fun onBindViewHolder(holder: ListItemView?, position: Int) {
        val item = items[position]

        val stringMessage: String = item.reduce { acc, s -> "$acc $s" }
        val byteMessage:String = stringMessage.toByteArray(Charsets.UTF_8)
                .map { Integer.toHexString(it.toInt() and 0xff) }
                .reduce { acc, s -> "$acc $s" }
        val text1 = "받은 명령 메시지 : $stringMessage"
        val text2 = "받은 바이트 : $byteMessage"

        holder?.let {
            it.text1.text = text1
            it.text2.text = text2
        }
    }

}

class ListItemView(view: View) : RecyclerView.ViewHolder(view) {

    val text1: TextView
    val text2: TextView

    init {
        text1 = view.findViewById(android.R.id.text1)
        text2 = view.findViewById(android.R.id.text2)
    }
}