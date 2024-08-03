package com.josemiguelmelo.lnd.bolt11.decoder

interface Decoder<IN, OUT> {
    fun decode(str: IN): OUT
}
