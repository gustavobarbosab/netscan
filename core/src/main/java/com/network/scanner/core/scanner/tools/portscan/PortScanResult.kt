package com.network.scanner.core.scanner.tools.portscan

sealed interface PortScanResult {
    object DeviceFound : PortScanResult
    object DeviceNotFound : PortScanResult
}