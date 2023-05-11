package com.network.scanner.core.domain.entities

data class WifiInfoResult(
    val ssid: String,
    val bssid: String,
    val capabilities: String
)