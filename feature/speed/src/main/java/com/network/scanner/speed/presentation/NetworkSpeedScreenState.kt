package com.network.scanner.speed.presentation

sealed class NetworkSpeedScreenState {
    object ScreenStarted : NetworkSpeedScreenState()
    object Searching : NetworkSpeedScreenState()
    object Failure : NetworkSpeedScreenState()
    object DeviceNotSupported : NetworkSpeedScreenState()
    class UpdateSpeed(val download: String, val upload: String) : NetworkSpeedScreenState()
}