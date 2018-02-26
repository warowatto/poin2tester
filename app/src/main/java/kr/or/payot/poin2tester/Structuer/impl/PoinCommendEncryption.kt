package kr.or.payot.poin2tester.Structuer.impl

import android.util.Log
import kr.or.payot.poin2tester.Structuer.CommendEncryption
import kr.or.payot.poin2tester.Utils.CheckSum
import java.security.SecureRandom
import java.util.zip.Checksum
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by yongheekim on 2018. 1. 25..
 */
class PoinCommendEncryption : CommendEncryption {

    val key = byteArrayOf(
            0x2B, 0x7E, 0x15, 0x16, 0x28,
            0xAE.toByte(), 0xD2.toByte(), 0xA6.toByte(), 0xAB.toByte(), 0xF7.toByte(),
            0x15, 0x88.toByte(), 0x09, 0xCF.toByte(), 0x4F,
            0x3C)
    val iv = byteArrayOf(
            0x00, 0x01, 0x02, 0x03, 0x04,
            0x05, 0x06, 0x07, 0x08, 0x09,
            0x0A, 0x0B, 0x0C, 0x0D, 0x0E,
            0x0F)

    val keySpec: SecretKeySpec = SecretKeySpec(key, "AES")
    val ivSpec: IvParameterSpec = IvParameterSpec(iv)

    val cipher: Cipher = Cipher.getInstance("AES/CBC/NoPadding")

    @Throws(IllegalStateException::class)
    override fun encrypt(byteArray: ByteArray): ByteArray {
        val totalSize = 64
        val firstRandomSize = 4
        val lastRandomSize = 2
        val contentSize = byteArray.size + 2

        if (contentSize + firstRandomSize + lastRandomSize > 64) throw IllegalArgumentException("명령의 바이트수가 초과하였습니다")

        val random = SecureRandom()
        val checksum = CheckSum.crc(byteArray)
        var plainText = byteArrayOf(*byteArray, *checksum)
        val firstRandomBytes = random.generateSeed(firstRandomSize)
        val dummyRandomBytes = random.generateSeed(totalSize - firstRandomSize - lastRandomSize - contentSize)
        val lastRandomBytes = random.generateSeed(lastRandomSize)

        val commendBytes = byteArrayOf(*firstRandomBytes, *plainText, *dummyRandomBytes, *lastRandomBytes)

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)

        val result = cipher.doFinal(commendBytes)
        val request = result.map { Integer.toHexString(it.toInt() and 0xff) }.reduce {acc, t -> "$acc $t"}

        return result
    }

    @Throws(IllegalStateException::class)
    override fun decrypt(byteArray: ByteArray): List<String> {
        val recive = byteArray.map { Integer.toHexString(it.toInt() and 0xff) }.reduce {acc, t -> "$acc $t"}

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)

        val decrpytPlainText = cipher.doFinal(byteArray).drop(4)

        val validationOk = byteArrayOf(0, 0)
        val plainText: ArrayList<Byte> = arrayListOf()

        for (byte in decrpytPlainText) {
            plainText.add(byte)
            if (plainText.isEmpty()) continue

            val message = plainText.toByteArray()
            if (CheckSum.crc(message) contentEquals validationOk) break
        }

        if (!(CheckSum.crc(plainText.toByteArray()) contentEquals validationOk)) {
            throw IllegalArgumentException("명령 포멧이 잘못되었습니다")
        }

        return plainText.dropLast(2)
                .toByteArray()
                .toString(Charsets.UTF_8)
                .split(" ")
    }
}