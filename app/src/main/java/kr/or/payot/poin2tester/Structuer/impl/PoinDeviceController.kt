package kr.or.payot.poin2tester.Structuer.impl

import android.bluetooth.*
import android.content.Context
import android.content.Intent
import android.util.Log
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import kr.or.payot.poin2tester.Structuer.CommendBuilder
import kr.or.payot.poin2tester.Structuer.DeviceController
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class PoinDeviceController(val context: Context, val commendBuilder: CommendBuilder) : BluetoothGattCallback(), DeviceController {

    val statusEvent = PublishSubject.create<Int>()

    var gatt: BluetoothGatt? = null
    var gattChar: BluetoothGattCharacteristic? = null
    val responseMessage: ArrayList<Byte> = arrayListOf()

    override fun isConnect(): Boolean {
        return gatt != null
    }

    override fun connect(bluetoothDevice: BluetoothDevice) {
        bluetoothDevice.connectGatt(context, false, this)
    }

    override fun sendMessage(cmd: String) {
        val message = commendBuilder.build(cmd).toObservable().buffer(20).map { it.toByteArray() }
        val delay = Observable.interval(0, 50, TimeUnit.MILLISECONDS, Schedulers.computation())

        Observable.zip(message, delay, BiFunction { msg: ByteArray, _: Long -> msg })
                .subscribe(
                        {
                            this.gattChar?.setValue(it)
                            this.gatt?.writeCharacteristic(this.gattChar)
                        },
                        { it.printStackTrace() })
    }

    override fun disconnect() {
        sendMessage("CMD S 5")
        gatt?.close()
    }

    override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
        super.onConnectionStateChange(gatt, status, newState)
        statusEvent.onNext(newState)


        if (newState == BluetoothProfile.STATE_CONNECTED) {
            gatt?.discoverServices()
        }
    }

    override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
        super.onServicesDiscovered(gatt, status)

        val services: List<BluetoothGattService>? = gatt?.services

        findService@ for (service in services!!) {
            findChar@ for (char in service.characteristics) {
                if (isReadable(char) && isWriteable(char) && isNotify(char)) {
                    this.gatt = gatt
                    this.gattChar = char

                    break@findService
                }
            }
        }

        this.gattChar?.let {
            if (this.gatt?.setCharacteristicNotification(this.gattChar, true) ?: false) {
                val format = SimpleDateFormat("yyMMddHHmmss").format(Date())
                val telNumber = "01011112222"

                sendMessage("CMD S 1 $format $telNumber")
            }
        }
    }

    override fun onCharacteristicChanged(gatt: BluetoothGatt?, characteristic: BluetoothGattCharacteristic?) {
        super.onCharacteristicChanged(gatt, characteristic)
        characteristic?.value?.let {
            responseMessage.addAll(it.toList())
            if (responseMessage.size == 64) {
                val message = responseMessage.toByteArray()
                sendBroadCastReciver(commendBuilder.convert(message))
                responseMessage.clear()
            }
        }
    }

    fun sendBroadCastReciver(message: List<String>) {
        val broadCastName = "kr.or.payot.poin"
        val intent = Intent(broadCastName).apply { putExtra("message", message.toTypedArray()) }

        context.sendBroadcast(intent)
    }

    fun isReadable(characteristic: BluetoothGattCharacteristic): Boolean {
        return true
    }

    fun isWriteable(characteristic: BluetoothGattCharacteristic): Boolean {
        return (characteristic.properties and (BluetoothGattCharacteristic.PROPERTY_WRITE or BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE)) != 0
    }

    fun isNotify(characteristic: BluetoothGattCharacteristic): Boolean {
        return (characteristic.properties and (BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0)
    }
}