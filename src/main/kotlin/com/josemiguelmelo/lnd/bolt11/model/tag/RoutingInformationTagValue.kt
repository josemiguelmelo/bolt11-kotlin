package com.josemiguelmelo.lnd.bolt11.model.tag

data class RoutingInformationTagValue(
    val publicKey: String,
    val shortChannelId: String,
    val feeBaseMSat: Int,
    val feeProportionalMillionths: Int,
    val cltvExpiryDelta: Int,
)