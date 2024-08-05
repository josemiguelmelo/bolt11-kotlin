package io.github.josemiguelmelo.lnd.bolt11.util

import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.convertInt5ArrayToByteArray
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.textToHexString
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.toHexString
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.toInt
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ByteArrayUtilTest() {
    @Test
    fun shouldConvertByteArrayToInt() {
        val byteArray = "132".toByteArray()
        val result = byteArray.toInt()

        val expected = 3224370
        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertByteArrayToHexString() {
        val byteArray = "132a".toByteArray()
        val result = byteArray.toHexString()
        val expected = "31333261"
        assertEquals(expected, result)
    }

    @Test
    fun shouldConvertTextToHexString() {
        val text = "testing text to hex string"
        val result = textToHexString(text)
        val expected = "74657374696e67207465787420746f2068657820737472696e67"
        assertEquals(expected, result)
    }

    @Test
    fun shouldInt5ToInt8ByteArrayWithoutOverflow() {
        val int5Array = listOf(1, 3, 10, 1)
        val result = convertInt5ArrayToByteArray(int5Array, false)
        val expected = listOf<Byte>(8, -44)
        assertEquals(expected, result.toList())
    }

    @Test
    fun shouldInt5ToInt8ByteArrayWithDefaultOverflowValue() {
        val int5Array = listOf(1, 3, 10, 1)
        val result = convertInt5ArrayToByteArray(int5Array)
        val expected = listOf<Byte>(8, -44)
        assertEquals(expected, result.toList())
    }

    @Test
    fun shouldInt5ToInt8ByteArrayWithOverflow() {
        val int5Array = listOf(1, 3, 10, 1)
        val result = convertInt5ArrayToByteArray(int5Array, true)
        val expected = listOf<Byte>(8, -44, 16)
        assertEquals(expected, result.toList())
    }
}
