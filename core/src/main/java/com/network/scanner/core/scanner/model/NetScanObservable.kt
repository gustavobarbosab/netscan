package com.network.scanner.core.scanner.model

import android.os.Looper
import androidx.core.os.HandlerCompat

class NetScanObservable {
    private var success: () -> Unit = {}
    private var error: () -> Unit = {}
    private val mainResultHandler by lazy { HandlerCompat.createAsync(Looper.getMainLooper()) }
    private var scheduler: NetScanScheduler = NetScanScheduler.IO

    fun onScheduler(netScanScheduler: NetScanScheduler) = apply {
        this.scheduler = netScanScheduler
    }

    fun onSuccess(success: () -> Unit) = apply {
        this.success = success
    }

    fun onError(error: () -> Unit) = apply {
        this.error = error
    }

    fun emit(): Any = when (scheduler) {
        NetScanScheduler.IO -> success.invoke()
        NetScanScheduler.Main -> mainResultHandler.post { success.invoke() }
    }


    fun throwException(): Any = when (scheduler) {
        NetScanScheduler.IO -> error.invoke()
        NetScanScheduler.Main -> mainResultHandler.post { error.invoke() }
    }
}