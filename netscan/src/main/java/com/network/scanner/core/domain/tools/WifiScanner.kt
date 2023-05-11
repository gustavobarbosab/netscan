package com.network.scanner.core.domain.tools

import com.network.scanner.core.domain.entities.WifiInfoResult
import com.network.scanner.core.domain.entities.observable.SubscribeResult

interface WifiScanner {
    fun startScan(): SubscribeResult<List<WifiInfoResult>>
    fun stop()
}