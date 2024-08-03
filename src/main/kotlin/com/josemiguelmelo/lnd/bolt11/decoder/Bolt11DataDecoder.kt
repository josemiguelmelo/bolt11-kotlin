package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util
import com.josemiguelmelo.lnd.bolt11.model.Bolt11Data
import com.josemiguelmelo.lnd.bolt11.model.HumanReadablePart
import com.josemiguelmelo.lnd.bolt11.model.tag.Tag

internal class Bolt11DataDecoder(): Decoder<Bolt11DataDecoder.Bolt11DataDecoderRequest, Bolt11Data> {
    data class Bolt11DataDecoderRequest(
        val data: String,
        val humanReadablePart: HumanReadablePart
    )

    private val tagDecoder = TagDecoder()

    override fun decode(bolt11DataRequest: Bolt11DataDecoderRequest): Bolt11Data {
        val timestampEpoch = decodeTimestampEpoch(bolt11DataRequest.data)
        val tags = decodeTags(bolt11DataRequest.data)
        // val signature = decodeSignature(bolt11DataRequest.data)

        return Bolt11Data(
            timestamp = timestampEpoch,
            tags = tags,
            raw = bolt11DataRequest.data
        )
    }

    private fun decodeTimestampEpoch(bolt11Data: String): Int {
        val timestamp32 = bolt11Data.substring(0, 7)
        return Bech32Util.bech32ToInt(timestamp32)
    }

    private fun decodeTags(bolt11Data: String): List<Tag<*>> {
        val tagData = bolt11Data.substring(7, bolt11Data.length - 104)
        return tagDecoder.decode(tagData)
    }

    private fun decodeSignature(bolt11Data: String): String {
        val signature = bolt11Data.substring(bolt11Data.length - 104, bolt11Data.length)
        TODO()
    }
}