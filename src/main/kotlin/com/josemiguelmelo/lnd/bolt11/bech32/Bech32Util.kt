package com.josemiguelmelo.lnd.bolt11.bech32

import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.convertInt5ArrayToByteArray
import java.net.URLDecoder

object Bech32Util {
    val BECH32_CHARSET = "qpzry9x8gf2tvdw0s3jn54khce6mua7l"

    fun bech32To5BitArray(bech32: String): List<Int> {
        return bech32.mapIndexed { idx, _ ->
            BECH32_CHARSET.indexOf(bech32[idx])
        }
    }

    fun bech32ToBinaryString(byteArray: List<Int>): String {
        return byteArray.joinToString("") { byte ->
            byte.and(0xFF).toString(2).padStart(6, '0').takeLast(5)
        }
    }

    fun bech32ToInt(bech32Str: String): Int {
        return bech32Str.foldIndexed(0) { idx, acc, _ ->
            val newAcc = acc * 32
            newAcc + BECH32_CHARSET.indexOf(bech32Str[idx])
        }
    }

    fun bech32ToUTF8String(bech32Str: String): String {
        val int5Array = bech32To5BitArray(bech32Str)
        val byteArray = convertInt5ArrayToByteArray(int5Array)

        val utf8String = StringBuilder()
        for (byte in byteArray) {
            utf8String.append("%")
            utf8String.append(byte.toInt().and(0xFF).toString(16).padStart(2, '0'))
        }
        return URLDecoder.decode(utf8String.toString(), "UTF-8")
    }
}
