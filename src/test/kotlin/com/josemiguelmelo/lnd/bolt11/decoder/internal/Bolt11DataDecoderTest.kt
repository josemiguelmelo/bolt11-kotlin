package com.josemiguelmelo.lnd.bolt11.decoder.internal

import com.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import com.josemiguelmelo.lnd.bolt11.model.Bolt11Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Bolt11DataDecoderTest() {
    private val decoder = Bolt11DataDecoder()

    companion object {
        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    Bolt11DataDecoder.Bolt11DataDecoderRequest(
                        data = data.dataString,
                        humanReadablePart = data.humanReadablePart,
                    ),
                    data.decodeResult.data,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testDataDecoder(
        input: Bolt11DataDecoder.Bolt11DataDecoderRequest,
        expected: Bolt11Data,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnTagDataPart() {
        val dataString = validTestData.first().dataString
        val result = decoder.tagData(dataString)
        val expected = "sp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgq"
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnTimestampDataPart() {
        val dataString = validTestData.first().dataString
        val result = decoder.timestampData(dataString)
        val expected = "pvjluez"
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnSignatureDataPart() {
        val dataString = validTestData.first().dataString
        val result = decoder.signatureData(dataString)
        val expected = "uk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp"
        assertEquals(expected, result)
    }
}
