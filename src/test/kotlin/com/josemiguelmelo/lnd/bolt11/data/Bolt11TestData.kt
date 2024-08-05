package com.josemiguelmelo.lnd.bolt11.data

import com.josemiguelmelo.lnd.bolt11.model.Bolt11
import com.josemiguelmelo.lnd.bolt11.model.Bolt11Data
import com.josemiguelmelo.lnd.bolt11.model.HumanReadablePart
import com.josemiguelmelo.lnd.bolt11.model.Signature
import com.josemiguelmelo.lnd.bolt11.model.tag.Tag
import com.josemiguelmelo.lnd.bolt11.model.tag.TagType

internal object Bolt11TestData {
    data class Bolt11TestData(
        val rawInvoice: String,
        val humanReadablePart: HumanReadablePart,
        val dataString: String,
        val decodeResult: Bolt11,
    )

    data class InvalidBolt11TestData(
        val rawInvoice: String,
        val errorMessage: String,
    )

    val validTestData =
        listOf(
            Bolt11TestData(
                rawInvoice = "lnbc2500u1pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgpfna3rh",
                dataString = "pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp",
                humanReadablePart =
                    HumanReadablePart(
                        raw = "lnbc2500u",
                        network = "lnbc",
                        networkName = "Bitcoin Mainnet",
                        mSat = 250_000_000,
                    ),
                decodeResult =
                    Bolt11(
                        checksum = "fna3rh",
                        hrp =
                            HumanReadablePart(
                                raw = "lnbc2500u",
                                network = "lnbc",
                                networkName = "Bitcoin Mainnet",
                                mSat = 250_000_000,
                            ),
                        data =
                            Bolt11Data(
                                timestamp = 1496314658,
                                tags =
                                    listOf(
                                        Tag(
                                            value = "1111111111111111111111111111111111111111111111111111111111111111",
                                            type = TagType.PAYMENT_SECRET.code,
                                            description = TagType.PAYMENT_SECRET.description,
                                            length = 52,
                                            raw = "zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygs",
                                        ),
                                        Tag(
                                            value = "0001020304050607080900010203040506070809000102030405060708090102",
                                            type = TagType.PAYMENT_HASH.code,
                                            length = 52,
                                            description = TagType.PAYMENT_HASH.description,
                                            raw = "qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypq",
                                        ),
                                        Tag(
                                            value = "1 cup coffee",
                                            type = TagType.DESCRIPTION.code,
                                            length = 20,
                                            description = TagType.DESCRIPTION.description,
                                            raw = "xysxxatsyp3k7enxv4js",
                                        ),
                                        Tag(
                                            value = 60,
                                            type = TagType.EXPIRY.code,
                                            length = 2,
                                            description = TagType.EXPIRY.description,
                                            raw = "pu",
                                        ),
                                        Tag(
                                            value = "100000100000000",
                                            type = TagType.FEATURE_BITS.code,
                                            length = 3,
                                            description = TagType.FEATURE_BITS.description,
                                            raw = "sgq",
                                        ),
                                    ),
                                signature =
                                    Signature(
                                        s = "6cbfe1a478b8d2307e92c88139464cb7e6ef26e414c4abe33337961ddc5e8ab1",
                                        r = "e59e3ffbd3945e4334879158d31e89b076dff54f3fa7979ae79df2db9dcaf589",
                                        recoveryFlag = 1.toByte(),
                                        raw = "uk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp",
                                    ),
                                signingData = "6c6e626332353030750b25fe64500d04444444444444444444444444444444444444444444444444444444444444444021a000081018202830384048000810182028303840480008101820283038404808103414312063757020636f66666565030041e140382000",
                                raw = "pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp",
                            ),
                    ),
            ),
        )

    val invalidChecksumInvoice =
        "lnbc2500m1pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgpfna3rh"
}
