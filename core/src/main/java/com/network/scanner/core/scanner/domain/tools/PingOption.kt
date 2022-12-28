package com.network.scanner.core.scanner.domain.tools

import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.PingResult

interface PingOption {
    fun execute(hostAddress: String): NetScanObservable<PingResult>
}