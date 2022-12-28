package com.network.scanner.core.scanner.domain.entities

data class PingResult(
    val reachable: Boolean,
    val hostAddress: String,
    val hostname: String
)
