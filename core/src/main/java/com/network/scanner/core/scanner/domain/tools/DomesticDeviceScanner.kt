package com.network.scanner.core.scanner.domain.tools

import com.network.scanner.core.scanner.domain.entities.DeviceAddress
import com.network.scanner.core.scanner.domain.entities.NetScanObservable

interface DomesticDeviceScanner {
    fun findDevices(): NetScanObservable<DeviceAddress>
}