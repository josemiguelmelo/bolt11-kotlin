package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.model.HumanReadablePart

internal class HumanReadablePartDecoder : Decoder<String, HumanReadablePart> {
    private val NETWORK_PREFIXES =
        mapOf(
            "lnbcrt" to "Bitcoin Regtest",
            "lnbc" to "Bitcoin Mainnet",
            "lntb" to "Bitcoin Testnet",
            "lntx" to "Litecoin Mainnet",
        )

    override fun decode(hrp: String): HumanReadablePart {
        val network: Pair<String, String> = decodeNetwork(hrp)

        val amountPart = hrp.substring(network.first.length)
        val amountMsat = decodeAmount(amountPart)

        return HumanReadablePart(network = network.first, networkName = network.second, mSat = amountMsat, raw = hrp)
    }

    private fun decodeNetwork(hrp: String): Pair<String, String> {
        return NETWORK_PREFIXES.entries.find { hrp.startsWith(it.key) }?.toPair()
            ?: ("" to "Unknown Network")
    }

    private fun decodeAmount(amountPart: String): Long {
        if (amountPart.isEmpty()) return 0
        var multiplier: Long = 1
        var lastChar = amountPart[amountPart.length - 1]
        when (lastChar) {
            'm' -> multiplier = 100000000L
            'u' -> multiplier = 100000L
            'n' -> multiplier = 100L
            'p' -> multiplier = 1L
            else ->
                if (Character.isDigit(lastChar)) {
                    multiplier = 100000000000L // default to satoshis (1 BTC)
                    lastChar = '\u0000'
                }
        }
        val numberPart = if (lastChar == '\u0000') amountPart else amountPart.substring(0, amountPart.length - 1)
        return numberPart.toLong() * multiplier
    }
}
