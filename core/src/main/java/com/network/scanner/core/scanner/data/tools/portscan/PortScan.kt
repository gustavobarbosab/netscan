package com.network.scanner.core.scanner.data.tools.portscan

import com.network.scanner.core.scanner.domain.entities.PortScanResult
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import java.util.concurrent.Executor

class PortScan(private val executor: Executor) {
    fun scan(
        ip: String,
        port: Int,
        timeout: Int = DEFAULT_TIMEOUT
    ): NetScanObservable<PortScanResult> {
        val listener = NetScanObservable<PortScanResult>()
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