package com.josemiguelmelo.lnd.bolt11.decoder.internal

import com.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import com.josemiguelmelo.lnd.bolt11.model.Signature
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SignatureDataDecoderTest() {
    private val decoder = SignatureDecoder()

    companion object {
        private val dataDecoder = Bolt11DataDecoder()

        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    dataDecoder.signatureData(data.dataString),
                    data.decodeResult.data.signature,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testSignatureDecoder(
        input: String,
        expected: Signature,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }
}
