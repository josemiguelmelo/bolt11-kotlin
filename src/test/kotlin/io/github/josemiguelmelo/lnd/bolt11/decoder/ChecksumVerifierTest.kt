package io.github.josemiguelmelo.lnd.bolt11.decoder

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ChecksumVerifierTest() {
    private val checksumVerifier = ChecksumVerifier()

    @Test
    fun shouldReturnValidChecksum() {
        val hrp = "lnbc2500u"
        val data = "pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp"
        val checksum = "fna3rh"

        val result = checksumVerifier.verify(hrp, data, checksum)

        assertTrue(result)
    }

    @Test
    fun shouldReturnInvalidWhenHRPNotCorrectChecksum() {
        val hrp = "lnbc2500m"
        val data = "pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqdq5xysxxatsyp3k7enxv4jsxqzpu9qrsgquk0rl77nj30yxdy8j9vdx85fkpmdla2087ne0xh8nhedh8w27kyke0lp53ut353s06fv3qfegext0eh0ymjpf39tuven09sam30g4vgp"
        val checksum = "fna3rh"

        val result = checksumVerifier.verify(hrp, data, checksum)

        assertFalse(result)
    }

    @Test
    fun shouldReturnInvalidWhenDataNotCorrectChecksum() {
        val hrp = "lnbc2500u"
        val data = "pvjluezsp5zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zyg3zygspp5qqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqqqsyqcyq5rqwzqfqypqhp58yjmdan79s6qqdhdzgynm4zwqd5d7xmw5fk98klysy043l2ahrqs9qrsgq7ea976txfraylvgzuxs8kgcw23ezlrszfnh8r6qtfpr6cxga50aj6txm9rxrydzd06dfeawfk6swupvz4erwnyutnjq7x39ymw6j38gp"
        val checksum = "fna3rh"

        val result = checksumVerifier.verify(hrp, data, checksum)

        assertFalse(result)
    }
}
