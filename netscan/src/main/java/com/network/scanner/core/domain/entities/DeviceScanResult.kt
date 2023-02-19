package com.network.scanner.core.domain.entities

data class DeviceScanResult(
    val localAddress: String,
    val devicesFound: List<DeviceInfo>
)
