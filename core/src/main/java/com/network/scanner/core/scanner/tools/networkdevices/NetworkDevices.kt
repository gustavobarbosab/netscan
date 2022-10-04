package com.network.scanner.core.scanner.tools.networkdevices

interface NetworkDevices {

    fun findDevices(): List<DeviceAddress>

    data class DeviceAddress(
        val ip: String,
        val mac: String
    )
}