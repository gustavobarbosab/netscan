package com.network.scanner.core.data.tools.portscan

import com.network.scanner.core.domain.entities.observable.NetScanObservable
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.entities.observable.ObservableSubject
import com.network.scanner.core.domain.entities.observable.SubscribeResult
import com.network.scanner.core.domain.tools.PortScan
import java.util.concurrent.ExecutorService

class PortScanImpl(private val executor: ExecutorService) : PortScan {

    private val observable: ObservableSubject<PortScanResult> = NetScanObservable(executor::shutdownNow)

    override fun scan(
        ip: String,
        port: Int,
        timeout: Int
    ): SubscribeResult<PortScanResult> {
        executor.execute {
            runCatching {
                val worker = PortScanWorker(ip, port, timeout)
                val result = worker.execute()
                observable.emit(result)
                observable.complete()
            }.onFailure(observable::throwException)
        }
        return observable.subscriber()
    }
}