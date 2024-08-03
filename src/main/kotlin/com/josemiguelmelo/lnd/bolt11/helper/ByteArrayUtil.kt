package com.josemiguelmelo.lnd.bolt11.helper

import org.bouncycastle.util.encoders.Hex

object ByteArrayUtil {
    fun ByteArray.toHexString() = Hex.toHexString(this)

    fun ByteArray.toInt(): Int {
        return this.fold(0) { acc, byte ->
            (acc shl 8) + (byte.toInt() and 0xFF)
        }
    }

    fun convertInt5ArrayToByteArray(int5Array: List<Int>, includeOverflow: Boolean = false): ByteArray {
        var count = 0
        var buffer = 0
        val byteArray = mutableListOf<Byte>()

        for (value in int5Array) {
            buffer = (buffer shl 5) + value
            count += 5
            if (count >= 8) {
                byteArray.add((buffer shr (count - 8) and 255).toByte())
                count -= 8
            }
        }

        if (includeOverflow && count > 0) {
            byteArray.add((buffer shl (8 - count) and 255).toByte())
        }

        return byteArray.toByteArray()
    }

    fun textToHexString(text: String): String =
        text.map { it.code.toString(16) }.joinToString("")
}