package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.NetScanObservable
import com.network.scanner.core.domain.entities.PingResult

interface PingOption {
    fun execute(hostAddress: String): NetScanObservable<PingResult>
}