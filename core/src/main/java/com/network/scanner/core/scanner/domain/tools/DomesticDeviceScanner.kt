package com.network.scanner.core.scanner.domain.tools

import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.DeviceScanResult

interface DomesticDeviceScanner {
    fun findDevices(): NetScanObservable<DeviceScanResult>
}