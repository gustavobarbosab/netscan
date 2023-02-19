package com.network.scanner.portscan.presentation

sealed class PortScanScreenState {
    object ScreenStarted: PortScanScreenState()
    object Searching: PortScanScreenState()
    object InvalidAddress: PortScanScreenState()
    object InvalidPort: PortScanScreenState()
    object DeviceNotFound: PortScanScreenState()
    object PortOpened: PortScanScreenState()
}