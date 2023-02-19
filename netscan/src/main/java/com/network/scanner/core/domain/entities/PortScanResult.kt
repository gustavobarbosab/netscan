package com.network.scanner.core.domain.entities

data class PortScanResult(
    val elapsedTimeMillis: Long,
    val portScanned: Int,
    val hostAddress: String
)