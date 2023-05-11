package com.network.scanner.core.domain.entities.observable

import android.os.Looper
import androidx.core.os.HandlerCompat

class NetScanObservable<RESULT>(
    private val cancelListener: () -> Unit
) : ObservableSubject<RESULT>, SubscribeResult<RESULT> {

    private var observers: MutableList<(result: RESULT) -> Unit> = mutableListOf()
    private var completeList: MutableList<() -> Unit> = mutableListOf()
    private var error: MutableList<(exception: Throwable) -> Unit> = mutableListOf()
    private val mainResultHandler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var scheduler: SubscribeScheduler = SubscribeScheduler.Main

    override fun onScheduler(netScanScheduler: SubscribeScheduler) = apply {
        this.scheduler = netScanScheduler
    }

    override fun onResult(result: (result: RESULT) -> Unit) = apply {
        this.observers.add(result)
    }

    override fun onFailure(error: (exception: Throwable) -> Unit) = apply {
        this.error.add(error)
    }

    override fun onComplete(complete: () -> Unit) = apply {
        this.completeList.add(complete)
    }

    override fun emit(result: RESULT) {
        when (scheduler) {
            SubscribeScheduler.Default -> observers.forEach { it.invoke(result) }
            SubscribeScheduler.Main -> mainResultHandler.post { observers.forEach { it.invoke(result) } }
        }
    }

    override fun complete() {
        mainResultHandler.post {
            completeList.forEach { it.invoke() }
            observers.clear()
            completeList.clear()
        }
    }

    override fun subscriber(): SubscribeResult<RESULT> = this

    override fun dispose() {
        mainResultHandler.post {
            cancelListener()
            complete()
        }
    }

    override fun throwException(exception: Throwable) {
        when (scheduler) {
            SubscribeScheduler.Default -> error.forEach { it.invoke(exception) }
            SubscribeScheduler.Main -> mainResultHandler.post { error.forEach { it.invoke(exception) } }
        }
    }
}