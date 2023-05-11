package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult

interface PingOption {
    fun execute(hostAddress: String): SubscribeResult<PingResult>
}