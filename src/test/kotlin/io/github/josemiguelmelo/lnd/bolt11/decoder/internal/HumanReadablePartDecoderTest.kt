package io.github.josemiguelmelo.lnd.bolt11.decoder.internal

import io.github.josemiguelmelo.lnd.bolt11.data.Bolt11TestData.validTestData
import io.github.josemiguelmelo.lnd.bolt11.model.HumanReadablePart
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class HumanReadablePartDecoderTest() {
    private val decoder = HumanReadablePartDecoder()

    companion object {
        @JvmStatic
        fun bolt1Addresses() =
            validTestData.map { data ->
                Arguments.of(
                    data.humanReadablePart.raw,
                    data.humanReadablePart,
                )
            }
    }

    @ParameterizedTest
    @MethodSource("bolt1Addresses")
    fun testHRPDecoder(
        input: String,
        expected: HumanReadablePart,
    ) {
        val result = decoder.decode(input)
        assertEquals(expected, result)
    }

    @Test
    fun shouldReturnZeroOnEmptyAmount() {
        val result = decoder.decodeAmount("")
        assertEquals(0, result)
    }

    @Test
    fun shouldReturnCorrectValueDependingOnUnit() {
        val milliResult = decoder.decodeAmount("25m")
        val microResult = decoder.decodeAmount("25u")
        val nanoResult = decoder.decodeAmount("25n")
        val picoResult = decoder.decodeAmount("25p")

        assertEquals(2_500_000_000, milliResult)
        assertEquals(2_500_000, microResult)
        assertEquals(2_500, nanoResult)
        assertEquals(25, picoResult)
    }

    @Test
    fun shouldReturnInSatoshiWhenUnitNotProvided() {
        val result = decoder.decodeAmount("25")
        assertEquals(2_500_000_000_000, result)
    }

    @Test
    fun shouldReturnIgnoreUnitWhenInvalid() {
        val invalidChar = decoder.decodeAmount("25i")
        assertEquals(25, invalidChar)
    }

    @Test
    fun shouldDecodeNetworkCorrectly() {
        val regTestResult = decoder.decodeNetwork("lnbcrt")
        val mainNetResult = decoder.decodeNetwork("lnbc")
        val testnetResult = decoder.decodeNetwork("lntb")
        val litecoinMainnetResult = decoder.decodeNetwork("lntx")
        assertEquals(("lnbcrt" to "Bitcoin Regtest"), regTestResult)
        assertEquals(("lnbc" to "Bitcoin Mainnet"), mainNetResult)
        assertEquals(("lntb" to "Bitcoin Testnet"), testnetResult)
        assertEquals(("lntx" to "Litecoin Mainnet"), litecoinMainnetResult)
    }

    @Test
    fun shouldReturnUnknownNetworkWhenNotSupported() {
        val result = decoder.decodeNetwork("notsupported")
        assertEquals(("" to "Unknown Network"), result)
    }
}
