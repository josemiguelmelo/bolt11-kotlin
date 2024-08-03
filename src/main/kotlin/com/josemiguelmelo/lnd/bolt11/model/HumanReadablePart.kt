package com.josemiguelmelo.lnd.bolt11.model

data class HumanReadablePart(
    val raw: String,
    val network: String,
    val networkName: String,
    val mSat: Long,
)