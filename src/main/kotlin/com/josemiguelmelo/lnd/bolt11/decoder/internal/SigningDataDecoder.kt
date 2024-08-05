package com.josemiguelmelo.lnd.bolt11.decoder.internal

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray
import com.josemiguelmelo.lnd.bolt11.decoder.Decoder
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.convertInt5ArrayToByteArray
import com.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.toHexString

internal class SigningDataDecoder : Decoder<SigningDataDecoder.SigningDataDecoderRequest, String> {
    data class SigningDataDecoderRequest(
        val timestampData: String,
        val tagData: String,
        val humanReadablePartRaw: String,
    )

    /**
     * @param input Bolt11 signing data request
     */
    override fun decode(input: SigningDataDecoderRequest): String {
        val value5BitArray =
            bech32To5BitArray(
                input.timestampData + input.tagData,
            )
        val value8BitArray = convertInt5ArrayToByteArray(value5BitArray, true)
        return ByteArrayUtil.textToHexString(input.humanReadablePartRaw) + value8BitArray.toHexString()
    }
}
