package com.network.scanner.core.scanner.model

import android.os.Looper
import androidx.core.os.HandlerCompat

class NetScanObservable<RESULT> {
    private var results: MutableList<(result: RESULT) -> Unit> = mutableListOf()
    private var error: MutableList<(exception: Throwable) -> Unit> = mutableListOf()
    private val mainResultHandler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var scheduler: NetScanScheduler = NetScanScheduler.IO

    fun onScheduler(netScanScheduler: NetScanScheduler) = apply {
        this.scheduler = netScanScheduler
    }

    fun onResult(result: (result: RESULT) -> Unit) = apply {
        this.results.add(result)
    }

    fun onError(error: (exception: Throwable) -> Unit) = apply {
        this.error.add(error)
    }

    fun emit(result: RESULT): Any = when (scheduler) {
        NetScanScheduler.IO -> results.forEach { it.invoke(result) }
        NetScanScheduler.Main -> mainResultHandler.post { results.forEach { it.invoke(result) } }
    }


    fun throwException(exception: Throwable): Any = when (scheduler) {
        NetScanScheduler.IO -> error.forEach { it.invoke(exception) }
        NetScanScheduler.Main -> mainResultHandler.post { error.forEach { it.invoke(exception) } }
    }
}