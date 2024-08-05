package com.josemiguelmelo.lnd.bolt11.decoder.internal

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32ToInt
import com.josemiguelmelo.lnd.bolt11.decoder.Decoder
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

    /**
     * @param input Bolt11 data request
     */
    override fun decode(input: Bolt11DataDecoderRequest): Bolt11Data {
        val timestampEpoch = decodeTimestampEpoch(input.data)
        val tags = decodeTags(input.data)
        val signature = decodeSignature(input.data)
        val signingData = decodeSigningData(input)

        return Bolt11Data(
            timestamp = timestampEpoch,
            tags = tags,
            signingData = signingData,
            signature = signature,
            raw = input.data,
        )
    }

    fun signatureData(bolt11Data: String) = bolt11Data.substring(bolt11Data.length - 104, bolt11Data.length)

    fun timestampData(bolt11Data: String) = bolt11Data.substring(0, 7)

    fun tagData(bolt11Data: String) = bolt11Data.substring(7, bolt11Data.length - 104)

    private fun decodeTimestampEpoch(bolt11Data: String): Int {
        val timestamp32 = timestampData(bolt11Data)
        return bech32ToInt(timestamp32)
    }

    private fun decodeTags(bolt11Data: String): List<Tag<*>> {
        val tagData = tagData(bolt11Data)
        return tagDecoder.decode(tagData)
    }

    private fun decodeSignature(bolt11Data: String): Signature {
        val signature = signatureData(bolt11Data)
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
}
