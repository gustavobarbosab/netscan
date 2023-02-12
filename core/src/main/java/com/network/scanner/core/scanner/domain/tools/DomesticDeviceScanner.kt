package com.network.scanner.core.scanner.domain.tools

import com.network.scanner.core.scanner.domain.entities.DeviceInfo
import com.network.scanner.core.scanner.domain.entities.NetScanObservable

interface DomesticDeviceScanner {
    fun findDevices(): NetScanObservable<DeviceInfo>
}