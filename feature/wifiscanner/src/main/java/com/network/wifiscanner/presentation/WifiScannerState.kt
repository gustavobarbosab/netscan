package com.network.wifiscanner.presentation

import com.network.wifiscanner.domain.WifiItemModel

sealed class WifiScannerState {
    object RequestPermission : WifiScannerState()
    object UnsupportedDevice : WifiScannerState()
    object SearchingDeviceList : WifiScannerState()
    object DeviceSearchFinished : WifiScannerState()
    object WifiDisconnected : WifiScannerState()
    class LoadWifiList(val list: List<WifiItemModel>) : WifiScannerState()
}
