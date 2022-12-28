package com.network.scanner.core.scanner.domain.tools

import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.DeviceAddressResult

interface DeviceScanner {
    fun findDevices(): NetScanObservable<List<DeviceAddressResult>>
}