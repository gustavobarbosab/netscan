package com.network.scanner.core.data.tools.ping.system

import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.tools.PingOption
import com.network.scanner.core.domain.entities.NetScanObservable
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