package io.github.josemiguelmelo.lnd.bolt11.model.tag

data class FallbackAddressTagValue(
    val version: Int,
    val fallbackAddress: String,
)
