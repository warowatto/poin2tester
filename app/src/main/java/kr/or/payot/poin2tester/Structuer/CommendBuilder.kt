package kr.or.payot.poin2tester.Structuer

/**
 * Created by yongheekim on 2018. 1. 25..
 */
interface CommendBuilder {

    // 명령어 생성
    fun build(cmd: String): ByteArray

    fun convert(byteArray: ByteArray): List<String>
}