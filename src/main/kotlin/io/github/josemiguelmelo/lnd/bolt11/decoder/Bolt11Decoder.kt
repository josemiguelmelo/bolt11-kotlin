package io.github.josemiguelmelo.lnd.bolt11.decoder

import io.github.josemiguelmelo.lnd.bolt11.decoder.internal.Bolt11DataDecoder
import io.github.josemiguelmelo.lnd.bolt11.decoder.internal.HumanReadablePartDecoder
import io.github.josemiguelmelo.lnd.bolt11.model.Bolt11

class Bolt11Decoder : Decoder<String, Bolt11> {
    private val humanReadablePartDecoder = HumanReadablePartDecoder()
    private val bolt11DataDecoder = Bolt11DataDecoder()
    private val checksumVerifier = ChecksumVerifier()

    private data class Bolt11Parts(
        val hrp: String,
        val data: String,
        val checksum: String,
    )

    /**
     * @param input Bolt11 invoice string
     */
    override fun decode(input: String): Bolt11 {
        val invoiceLowerCase = input.lowercase()

        val bolt11Parts = decodeBolt11Parts(invoiceLowerCase)

        if (!checksumVerifier.verify(bolt11Parts.hrp, bolt11Parts.data, bolt11Parts.checksum)) {
            throw Error("Checksum failed")
        }

        val humanReadablePart = humanReadablePartDecoder.decode(bolt11Parts.hrp)

        val bolt11DataDecoderRequest =
            Bolt11DataDecoder.Bolt11DataDecoderRequest(
                data = bolt11Parts.data,
                humanReadablePart = humanReadablePart,
            )
        val data = bolt11DataDecoder.decode(bolt11DataDecoderRequest)
        return Bolt11(
            hrp = humanReadablePart,
            data = data,
            checksum = bolt11Parts.checksum,
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
            checksum = checksum,
        )
    }
}
