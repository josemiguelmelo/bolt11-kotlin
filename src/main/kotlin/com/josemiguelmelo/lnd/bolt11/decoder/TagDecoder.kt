package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray
import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32ToBinaryString
import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32ToInt
import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32ToUTF8String
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.convertInt5ArrayToByteArray
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.toHexString
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.toInt
import com.josemiguelmelo.lnd.bolt11.model.tag.FallbackAddressTagValue
import com.josemiguelmelo.lnd.bolt11.model.tag.RoutingInformationTagValue
import com.josemiguelmelo.lnd.bolt11.model.tag.Tag
import com.josemiguelmelo.lnd.bolt11.model.tag.TagType

internal class TagDecoder : Decoder<String, List<Tag<*>>> {
    override fun decode(tagDataAsString: String): List<Tag<*>> {
        var tagString = tagDataAsString
        val tags = mutableListOf<Tag<*>>()

        while (tagString.isNotEmpty()) {
            val type = tagString[0]
            val dataLength = bech32ToInt(tagString.substring(1, 3))
            val bech32Data = tagString.substring(3, dataLength + 3)

            tagString = tagString.substring(3 + dataLength, tagString.length)

            val tag = tagFromBech32Data(type, dataLength, bech32Data) ?: continue

            tags.add(tag)
        }

        return tags
    }

    private fun tagFromBech32Data(
        type: Char,
        dataLength: Int,
        bech32Data: String,
    ): Tag<*>? {
        val tagType = TagType.fromTypeCode(type) ?: return null

        val value =
            when (tagType) {
                TagType.PAYMENT_HASH -> convertInt5ArrayToByteArray(bech32To5BitArray(bech32Data)).toHexString()
                TagType.PAYMENT_SECRET -> convertInt5ArrayToByteArray(bech32To5BitArray(bech32Data)).toHexString()
                TagType.DESCRIPTION -> bech32ToUTF8String(bech32Data)
                TagType.DESCRIPTION_HASH -> bech32Data
                TagType.PAYEE_PUBLIC_KEY -> convertInt5ArrayToByteArray(bech32To5BitArray(bech32Data)).toHexString()
                TagType.EXPIRY -> bech32ToInt(bech32Data)
                TagType.MIN_FINAL_CLTV_EXPIRY -> bech32ToInt(bech32Data)

                TagType.FALLBACK_ADDRESS -> {
                    val version = bech32To5BitArray(bech32Data[0].toString())[0]
                    if (version < 0 || version > 18) return null
                    val data = bech32Data.substring(1, bech32Data.length)

                    FallbackAddressTagValue(version, data)
                }
                TagType.ROUTING_INFORMATION -> {
                    val data = convertInt5ArrayToByteArray(bech32To5BitArray(bech32Data))
                    val pubkey = data.slice(0 until 33).toByteArray()
                    val shortChannelId = data.slice(33 until 41).toByteArray()
                    val feeBaseMsat = data.slice(41 until 45).toByteArray()
                    val feeProportionalMillionths = data.slice(45 until 49).toByteArray()
                    val cltvExpiryDelta = data.slice(49 until 51).toByteArray()

                    RoutingInformationTagValue(
                        publicKey = pubkey.toHexString(),
                        shortChannelId = shortChannelId.toHexString(),
                        feeBaseMSat = feeBaseMsat.toInt(),
                        feeProportionalMillionths = feeProportionalMillionths.toInt(),
                        cltvExpiryDelta = cltvExpiryDelta.toInt(),
                    )
                }
                TagType.FEATURE_BITS ->
                    bech32ToBinaryString(bech32To5BitArray(bech32Data))
            } ?: return null

        return Tag(
            value = value,
            type = tagType.code,
            length = dataLength,
            description = tagType.description,
            raw = bech32Data,
        )
    }
}
