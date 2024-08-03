package com.josemiguelmelo.lnd.bolt11.decoder

import com.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.convertInt5ArrayToByteArray
import com.josemiguelmelo.lnd.bolt11.helper.ByteArrayUtil.toHexString
import com.josemiguelmelo.lnd.bolt11.model.Signature

internal class SignatureDecoder : Decoder<String, Signature> {

    override fun decode(signature: String): Signature {
        val signatureData = convertInt5ArrayToByteArray(bech32To5BitArray(signature))

        val recoveryFlag = signatureData.last()
        val r = signatureData.slice(0 until 32).toByteArray().toHexString()
        val s = signatureData.slice(32 until signatureData.size - 1).toByteArray().toHexString()

        return Signature(
            r = r,
            s = s,
            recoveryFlag = recoveryFlag,
            raw = signature,
        )
    }
}