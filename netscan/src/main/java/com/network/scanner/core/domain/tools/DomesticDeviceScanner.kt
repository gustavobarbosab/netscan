package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.DeviceInfoResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult

interface DomesticDeviceScanner {
    fun findDevices(): SubscribeResult<DeviceInfoResult>
}