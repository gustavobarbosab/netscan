package com.network.scanner.core.scanner.tools.ping

import com.network.scanner.core.scanner.model.NetScanObservable

interface PingOption {
    fun execute(host: String): NetScanObservable
}