package kr.or.payot.poin2tester.Utils

import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Created by yongheekim on 2017. 4. 15..
 */

object CheckSum {

    fun crc(data: ByteArray): ByteArray {
        var crc = 0xffff
        var flag: Int

        for (b in data) {
            crc = crc xor (b.toInt() and 0xff)

            for (i in 0..7) {
                flag = crc and 1
                crc = crc shr 1
                if (flag != 0) {
                    crc = crc xor 0xa001
                }
            }
        }

        val buffer = ByteBuffer.allocate(2)
        buffer.order(ByteOrder.LITTLE_ENDIAN)

        while (buffer.hasRemaining()) {
            buffer.putShort(crc.toShort())
        }

        return buffer.array()
    }
}
