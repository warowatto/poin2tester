package kr.or.payot.poin2tester.Structuer.impl

import kr.or.payot.poin2tester.Structuer.CommendBuilder
import kr.or.payot.poin2tester.Structuer.CommendEncryption
import kr.or.payot.poin2tester.Utils.CheckSum

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class PoinCommendBuilder(val commendEncryption: CommendEncryption) : CommendBuilder {

    override fun build(cmd: String): ByteArray {
        val addCrc = cmd.toByteArray(Charsets.UTF_8).plus(CheckSum.crc(cmd.toByteArray(Charsets.UTF_8)))

        return commendEncryption.encrypt(addCrc)
    }

    @Throws(IllegalStateException::class)
    override fun convert(byteArray: ByteArray): List<String> {
        return commendEncryption.decrypt(byteArray)
    }

}