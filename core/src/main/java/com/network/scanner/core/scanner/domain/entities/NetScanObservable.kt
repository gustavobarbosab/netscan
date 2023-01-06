package com.network.scanner.core.scanner.domain.entities

import android.os.Looper
import androidx.core.os.HandlerCompat

class NetScanObservable<RESULT>(private val cancelListener: () -> Unit) {
    private var observers: MutableList<(result: RESULT) -> Unit> = mutableListOf()
    private var error: MutableList<(exception: Throwable) -> Unit> = mutableListOf()
    private val mainResultHandler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var scheduler: NetScanScheduler = NetScanScheduler.Main

    fun onScheduler(netScanScheduler: NetScanScheduler) = apply {
        this.scheduler = netScanScheduler
    }

    fun onResult(result: (result: RESULT) -> Unit) = apply {
        this.observers.add(result)
    }

    fun onFailure(error: (exception: Throwable) -> Unit) = apply {
        this.error.add(error)
    }

    fun emit(result: RESULT): NetScanObservable<RESULT> = apply {
        when (scheduler) {
            NetScanScheduler.Default -> observers.forEach { it.invoke(result) }
            NetScanScheduler.Main -> mainResultHandler.post { observers.forEach { it.invoke(result) } }
        }
    }

    fun complete() {
        observers.clear()
    }

    fun cancel() {
        cancelListener()
        complete()
    }

    fun throwException(exception: Throwable): Any = when (scheduler) {
        NetScanScheduler.Default -> error.forEach { it.invoke(exception) }
        NetScanScheduler.Main -> mainResultHandler.post { error.forEach { it.invoke(exception) } }
    }
}