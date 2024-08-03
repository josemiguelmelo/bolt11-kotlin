package com.josemiguelmelo.lnd.bolt11.model.tag

data class Tag<T>(
    val value: T,
    val type: Char,
    val length: Int,
    val description: String,
    val raw: String,
)

enum class TagType(val description: String, val code: Char) {
    PAYMENT_HASH("payment_hash", 'p'),
    DESCRIPTION("description", 'd'),
    DESCRIPTION_HASH("description_hash", 'h'),

    MIN_FINAL_CLTV_EXPIRY("min_final_cltv_expiry", 'c'),
    EXPIRY("expiry", 'x'),
    PAYMENT_SECRET("payment_secret", 's'),
    PAYEE_PUBLIC_KEY("payee_public_key", 'n'),

    FALLBACK_ADDRESS("fallback_address", 'f'),
    ROUTING_INFORMATION("routing_information", 'r'),

    FEATURE_BITS("feature_bits", '9'),
    ;

    companion object {
        fun fromTypeCode(code: Char): TagType? {
            return TagType.values().firstOrNull { it.code == code }
        }
    }
}
