package com.josemiguelmelo.lnd.bolt11.model

data class Bolt11(
    val hrp: HumanReadablePart,
    val data: Bolt11Data,
    val checksum: String,
)
