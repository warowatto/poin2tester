package kr.or.payot.poin2tester

import kr.or.payot.poin2tester.Structuer.CommendEncryption
import kr.or.payot.poin2tester.Structuer.impl.PoinCommendEncryption
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    lateinit var encryption: CommendEncryption

    @Before
    fun init() {
        encryption = PoinCommendEncryption()
    }

    @Test
    fun addition_isCorrect() {
        val plainText = "CMD A 1 1000".toByteArray(Charsets.UTF_8)

        val en = encryption.encrypt(plainText)

        val de = encryption.decrypt(en)

        printByteArray(en)
        println("values : ${de}")
    }

    fun printByteArray(byteArray: ByteArray) {
        val params = byteArray.map { it.toInt() and 0xff }.map { Integer.toHexString(it) }
        println(params)
    }
}
