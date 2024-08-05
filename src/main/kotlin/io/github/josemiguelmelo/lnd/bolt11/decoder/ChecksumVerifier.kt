package io.github.josemiguelmelo.lnd.bolt11.decoder

import io.github.josemiguelmelo.lnd.bolt11.bech32.Bech32Util.bech32To5BitArray

class ChecksumVerifier {
    private val GENERATOR = intArrayOf(0x3b6a57b2, 0x26508e6d, 0x1ea119fa, 0x3d4233dd, 0x2a1462b3)

    fun verify(
        humanReadablePartRaw: String,
        data: String,
        checksum: String,
    ): Boolean {
        val dataByteArray = bech32To5BitArray(data + checksum).toTypedArray()
        val expandedHRP = expandHRP(humanReadablePartRaw)
        val all = expandedHRP + dataByteArray
        return polymod(all) == 1
    }

    private fun expandHRP(humanReadablePartRaw: String): Array<Int> {
        val firstExpandedPart =
            humanReadablePartRaw.map { it.code shr 5 }
        val separator = 0
        val secondExpandedPart =
            humanReadablePartRaw.map { it.code and 31 }

        val expandedHRPList = firstExpandedPart + separator + secondExpandedPart
        return expandedHRPList.toTypedArray()
    }

    private fun polymod(values: Array<Int>): Int {
        var chk = 1
        for (v in values) {
            val top = chk shr 25
            chk = (chk and 0x1ffffff) shl 5 xor (v.toInt() and 0xff)
            for (i in 0..4) {
                if ((top shr i and 1) != 0) {
                    chk = chk xor GENERATOR[i]
                }
            }
        }
        return chk
    }
}
