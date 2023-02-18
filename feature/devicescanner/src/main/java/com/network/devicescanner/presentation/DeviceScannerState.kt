package com.network.devicescanner.presentation

import com.network.devicescanner.domain.DeviceItem

sealed class DeviceScannerState {
    object RequestPermission : DeviceScannerState()
    object SearchingDeviceList : DeviceScannerState()
    object DeviceSearchFinished : DeviceScannerState()
    class AddDevice(val device: DeviceItem) : DeviceScannerState()
}
