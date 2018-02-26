package kr.or.payot.poin2tester.Structuer

/**
 * Created by yongheekim on 2018. 1. 25..
 */
interface CommendEncryption {

    fun encrypt(byteArray: ByteArray): ByteArray

    @Throws(IllegalStateException::class)
    fun decrypt(byteArray: ByteArray): List<String>
}