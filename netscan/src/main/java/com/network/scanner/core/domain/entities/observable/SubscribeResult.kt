package com.network.scanner.core.domain.entities.observable

interface SubscribeResult<RESULT> {
    fun onScheduler(netScanScheduler: SubscribeScheduler): SubscribeResult<RESULT>
    fun onResult(result: (result: RESULT) -> Unit): SubscribeResult<RESULT>
    fun onFailure(error: (exception: Throwable) -> Unit) : SubscribeResult<RESULT>
    fun onComplete(complete: () -> Unit): SubscribeResult<RESULT>
    fun dispose()
}