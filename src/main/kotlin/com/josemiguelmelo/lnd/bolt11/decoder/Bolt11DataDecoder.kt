package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32ToInt
import com.josemiguelmelo.lnd.bolt11.model.Bolt11Data
import com.josemiguelmelo.lnd.bolt11.model.HumanReadablePart
import com.josemiguelmelo.lnd.bolt11.model.Signature
import com.josemiguelmelo.lnd.bolt11.model.tag.Tag

internal class Bolt11DataDecoder() : Decoder<Bolt11DataDecoder.Bolt11DataDecoderRequest, Bolt11Data> {
    data class Bolt11DataDecoderRequest(
        val data: String,
        val humanReadablePart: HumanReadablePart,
    )

    private val tagDecoder = TagDecoder()
    private val signatureDecoder = SignatureDecoder()
    private val signingDataDecoder = SigningDataDecoder()

    override fun decode(bolt11DataRequest: Bolt11DataDecoderRequest): Bolt11Data {
        val timestampEpoch = decodeTimestampEpoch(bolt11DataRequest.data)
        val tags = decodeTags(bolt11DataRequest.data)
        val signature = decodeSignature(bolt11DataRequest.data)
        val signingData = decodeSigningData(bolt11DataRequest)

        return Bolt11Data(
            timestamp = timestampEpoch,
            tags = tags,
            signingData = signingData,
            signature = signature,
            raw = bolt11DataRequest.data,
        )
    }

    private fun decodeTimestampEpoch(bolt11Data: String): Int {
        val timestamp32 = timestampData(bolt11Data)
        return bech32ToInt(timestamp32)
    }

    private fun decodeTags(bolt11Data: String): List<Tag<*>> {
        val tagData = tagData(bolt11Data)
        return tagDecoder.decode(tagData)
    }

    private fun decodeSignature(bolt11Data: String): Signature {
        val signature = bolt11Data.substring(bolt11Data.length - 104, bolt11Data.length)
        return signatureDecoder.decode(signature)
    }

    private fun decodeSigningData(bolt11DataRequest: Bolt11DataDecoderRequest): String {
        val request =
            SigningDataDecoder.SigningDataDecoderRequest(
                timestampData = timestampData(bolt11DataRequest.data),
                tagData = tagData(bolt11DataRequest.data),
                humanReadablePartRaw = bolt11DataRequest.humanReadablePart.raw,
            )
        return signingDataDecoder.decode(request)
    }

    private fun timestampData(bolt11Data: String) = bolt11Data.substring(0, 7)

    private fun tagData(bolt11Data: String) = bolt11Data.substring(7, bolt11Data.length - 104)
}
