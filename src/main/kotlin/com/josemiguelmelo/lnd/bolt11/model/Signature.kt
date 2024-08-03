package com.josemiguelmelo.lnd.bolt11.model

data class Signature(
    val s: String,
    val r: String,
    val recoveryFlag: Byte,
    val raw: String,
)
