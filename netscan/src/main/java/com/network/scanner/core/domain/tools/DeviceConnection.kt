package com.network.scanner.core.domain.tools

interface DeviceConnection {
    fun hasWifiConnection(): Boolean

    fun hasCellularConnection(): Boolean

    fun hasEthernetConnection(): Boolean

    fun hasInternetConnection(): Boolean
}