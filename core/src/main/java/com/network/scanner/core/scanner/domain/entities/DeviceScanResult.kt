package com.network.scanner.core.scanner.domain.entities

data class DeviceScanResult(
    val localAddress: String,
    val devicesFound: List<DeviceInfo>
)
