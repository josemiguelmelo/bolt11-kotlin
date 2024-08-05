package io.github.josemiguelmelo.lnd.bolt11.model

import io.github.josemiguelmelo.lnd.bolt11.model.tag.Tag

data class Bolt11Data(
    val timestamp: Int,
    val tags: List<Tag<*>>,
    val signature: Signature,
    val signingData: String,
    val raw: String,
)
