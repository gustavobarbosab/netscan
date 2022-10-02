package com.network.scanner.core.scanner.tools.ping

data class PingAttributes(
    val ipDestiny: String,
    val timeToLive: Int,
    val timeout: Int,
    val attempts: Int,
)
