package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.DeviceInfo
import com.network.scanner.core.domain.entities.NetScanObservable

interface DomesticDeviceScanner {
    fun findDevices(): NetScanObservable<DeviceInfo>
}