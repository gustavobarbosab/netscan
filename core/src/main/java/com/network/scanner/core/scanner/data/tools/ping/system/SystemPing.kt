package com.network.scanner.core.scanner.data.tools.ping.system

import android.util.Log
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.tools.PingOption
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService

class SystemPing(private val executor: ExecutorService) : PingOption {

    override fun execute(hostAddress: String): NetScanObservable<PingResult> {
        val listener = NetScanObservable<PingResult>(executor::shutdownNow)
        executor.execute {
            runCatching {
                val worker = SystemPingWorker(hostAddress)
                val result = worker.execute()
                listener.emit(result)
            }.onFailure(listener::throwException)
        }
        return listener
    }
}