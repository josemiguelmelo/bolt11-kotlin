package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.model.Bolt11

class Bolt11Decoder : Decoder<String, Bolt11> {
    private val humanReadablePartDecoder = HumanReadablePartDecoder()

    private data class Bolt11Parts(
        val hrp: String,
        val data: String,
        val checksum: String,
    )

    override fun decode(invoice: String): Bolt11 {
        val bolt11Parts = decodeBolt11Parts(invoice)
        val humanReadablePart = humanReadablePartDecoder.decode(bolt11Parts.hrp)

        return Bolt11(
            hrp = humanReadablePart,
        )
    }

    private fun decodeBolt11Parts(invoice: String): Bolt11Parts {
        val hrpEndIndex = invoice.lastIndexOf('1')
        require(hrpEndIndex >= 0) { "Invalid Bolt11 invoice" }
        val hrp = invoice.substring(0, hrpEndIndex)

        val dataStartIdx = hrpEndIndex + 1
        val checksumStartIdx = invoice.length - 6
        val data = invoice.substring(dataStartIdx, checksumStartIdx)
        val checksum = invoice.substring(checksumStartIdx, invoice.length)

        return Bolt11Parts(
            hrp = hrp,
            data = data,
            checksum = checksum
        )
    }
}