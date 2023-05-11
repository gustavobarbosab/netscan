package com.network.scanner.core.domain.entities.observable

interface ObservableSubject<RESULT> {
    fun emit(result: RESULT)
    fun throwException(exception: Throwable)
    fun complete()
    fun subscriber(): SubscribeResult<RESULT>
}