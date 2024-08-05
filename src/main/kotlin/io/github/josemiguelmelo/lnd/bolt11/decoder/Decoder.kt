package io.github.josemiguelmelo.lnd.bolt11.decoder

interface Decoder<IN, OUT> {
    fun decode(input: IN): OUT
}
