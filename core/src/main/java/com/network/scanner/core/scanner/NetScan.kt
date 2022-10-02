package com.network.scanner.core.scanner

import com.network.scanner.core.scanner.tools.ping.PingDevice

interface NetScan {
    fun pingDevice(): PingDevice.Builder
}