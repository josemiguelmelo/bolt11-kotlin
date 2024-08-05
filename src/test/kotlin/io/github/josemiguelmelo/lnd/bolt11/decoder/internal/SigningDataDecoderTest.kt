package io.github.josemiguelmelo.lnd.bolt11.decoder.internal

import com.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SigningDataDecoderTest() {
    private val decoder = SigningDataDecoder()

    companion object {
        private val dataDecoder = Bolt11DataDecoder()

        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    SigningDataDecoder.SigningDataDecoderRequest(
                        tagData = dataDecoder.tagData(data.dataString),
                        timestampData = dataDecoder.timestampData(data.dataString),
                        humanReadablePartRaw = data.humanReadablePart.raw,
                    ),
                    data.decodeResult.data.signingData,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testSigningDataDecoder(
        input: SigningDataDecoder.SigningDataDecoderRequest,
        expected: String,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }
}
