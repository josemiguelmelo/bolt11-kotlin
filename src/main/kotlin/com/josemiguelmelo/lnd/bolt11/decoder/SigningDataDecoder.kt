package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.convertInt5ArrayToByteArray
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.toHexString

internal class SigningDataDecoder : Decoder<SigningDataDecoder.SigningDataDecoderRequest, String> {
    data class SigningDataDecoderRequest(
        val timestampData: String,
        val tagData: String,
        val humanReadablePartRaw: String,
    )

    override fun decode(signingDataDecoderRequest: SigningDataDecoderRequest): String {
        val value5BitArray =
            bech32To5BitArray(
                signingDataDecoderRequest.timestampData + signingDataDecoderRequest.tagData,
            )
        val value8BitArray = convertInt5ArrayToByteArray(value5BitArray, true)
        return ByteArrayUtil.textToHexString(signingDataDecoderRequest.humanReadablePartRaw) + value8BitArray.toHexString()
    }
}
