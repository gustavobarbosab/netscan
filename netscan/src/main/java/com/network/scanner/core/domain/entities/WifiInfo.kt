package com.network.scanner.core.domain.entities

data class WifiInfo(
    val ssid: String,
    val bssid: String,
    val capabilities: String
)