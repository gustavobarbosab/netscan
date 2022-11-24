package com.network.scanner.core.scanner.model

sealed class NetScanScheduler {
    object Main : NetScanScheduler()
    object IO : NetScanScheduler()
}