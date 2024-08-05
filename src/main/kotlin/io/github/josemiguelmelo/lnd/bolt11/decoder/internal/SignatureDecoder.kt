package io.github.josemiguelmelo.lnd.bolt11.decoder.internal

import io.github.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray
import io.github.josemiguelmelo.lnd.bolt11.decoder.Decoder
import io.github.josemiguelmelo.lnd.bolt11.model.Signature
import io.github.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.convertInt5ArrayToByteArray
import io.github.josemiguelmelo.lnd.bolt11.util.ByteArrayUtil.toHexString

internal class SignatureDecoder : Decoder<String, Signature> {
    /**
     * @param input Bolt11 signature part as string
     */
    override fun decode(input: String): Signature {
        val signatureData = convertInt5ArrayToByteArray(bech32To5BitArray(input))

        val recoveryFlag = signatureData.last()
        val r = signatureData.slice(0 until 32).toByteArray().toHexString()
        val s = signatureData.slice(32 until signatureData.size - 1).toByteArray().toHexString()

        return Signature(
            r = r,
            s = s,
            recoveryFlag = recoveryFlag,
            raw = input,
        )
    }
}
