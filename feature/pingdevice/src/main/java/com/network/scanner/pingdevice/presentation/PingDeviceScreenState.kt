package com.network.scanner.pingdevice.presentation

sealed class PingDeviceScreenState {
    object ScreenStarted: PingDeviceScreenState()
    object Searching: PingDeviceScreenState()
    object DeviceFound: PingDeviceScreenState()
    object InvalidAddress: PingDeviceScreenState()
    object DeviceNotFound: PingDeviceScreenState()
}