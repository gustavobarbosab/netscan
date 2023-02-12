package com.network.devicescanner.presentation

import com.network.scanner.core.scanner.domain.entities.DeviceInfo

sealed class DeviceScannerState {
    object RequestPermission : DeviceScannerState()
    object SearchingDeviceList : DeviceScannerState()
    object DeviceSearchFinished : DeviceScannerState()
    class AddDevice(val device: DeviceInfo) : DeviceScannerState()
}
