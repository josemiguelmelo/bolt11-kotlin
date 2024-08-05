package io.github.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.invalidChecksumInvoice
import com.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import com.josemiguelmelo.lnd.bolt11.model.Bolt11
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class Bolt11DecoderTest() {
    private val decoder = Bolt11Decoder()

    companion object {
        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    data.rawInvoice,
                    data.decodeResult,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testDecoder(
        input: String,
        expected: Bolt11,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }

    @Test
    fun shouldThrowChecksumFailedError() {
        assertThrows<Error>("Checksum failed") {
            decoder.decode(invalidChecksumInvoice)
        }
    }
}
