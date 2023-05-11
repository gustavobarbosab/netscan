package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult

interface PortScan {
    fun scan(
        ip: String,
        port: Int,
        timeout: Int = DEFAULT_TIMEOUT
    ): SubscribeResult<PortScanResult>

    companion object {
        private const val DEFAULT_TIMEOUT = 5000
    }
}