package com.network.scanner.core.scanner.tools.ping

data class PingResult(
    val reachable: Boolean,
    val hostAddress: String
)