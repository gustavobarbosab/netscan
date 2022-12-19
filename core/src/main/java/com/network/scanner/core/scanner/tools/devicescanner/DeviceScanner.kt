package com.network.scanner.core.scanner.tools.devicescanner

import com.network.scanner.core.scanner.model.NetScanObservable

interface DeviceScanner {
    fun findDevices(): NetScanObservable<List<DeviceAddress>>
}