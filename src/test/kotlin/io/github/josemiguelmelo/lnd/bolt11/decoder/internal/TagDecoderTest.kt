package io.github.josemiguelmelo.lnd.bolt11.decoder.internal

import io.github.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import io.github.josemiguelmelo.lnd.bolt11.model.tag.Tag
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class TagDecoderTest() {
    private val decoder = TagDecoder()

    companion object {
        private val dataDecoder = Bolt11DataDecoder()

        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    dataDecoder.tagData(data.dataString),
                    data.decodeResult.data.tags,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testTagDecoder(
        input: String,
        expected: List<Tag<*>>,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }
}
