package com.network.scanner.core.scanner.data.tools.portscan

import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import java.util.concurrent.ExecutorService

class PortScan(private val executor: ExecutorService) {

    private val listener = NetScanObservable<PortScanResult>(executor::shutdownNow)

    fun scan(
        ip: String,
        port: Int,
        timeout: Int = DEFAULT_TIMEOUT
    ): NetScanObservable<PortScanResult> {
        executor.execute {
            runCatching {
                val worker = PortScanWorker(ip, port, timeout)
                val result = worker.execute()
                listener.emit(result).complete()
            }.onFailure(listener::throwException)
        }
        return listener
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 5000
    }
}