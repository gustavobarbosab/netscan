package com.network.scanner.core.scanner.tools.ping

import com.network.scanner.core.scanner.model.NetScanObservable

interface PingOption {
    fun execute(hostAddress: String): NetScanObservable<PingResult>
}