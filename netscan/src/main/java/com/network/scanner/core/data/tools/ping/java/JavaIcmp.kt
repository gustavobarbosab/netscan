package com.network.scanner.core.data.tools.ping.java

import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.domain.entities.observable.NetScanObservable
import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.entities.observable.ObservableSubject
import com.network.scanner.core.domain.entities.observable.SubscribeResult
import com.network.scanner.core.domain.tools.PingOption
import java.util.concurrent.ExecutorService

class JavaIcmp(
    private val facade: NetScanFacade,
    private val executor: ExecutorService
) : PingOption {

    override fun execute(hostAddress: String): SubscribeResult<PingResult> {
        val listener: ObservableSubject<PingResult> = NetScanObservable<PingResult>(executor::shutdownNow)
        executor.execute {
            runCatching {
                val worker = JavaPingWorker(facade, hostAddress)
                val result = worker.execute()
                listener.emit(result)
            }.onFailure(listener::throwException)
        }
        return listener.subscriber()
    }

    companion object {
        // TODO receive this params from function
        const val DEFAULT_TIME_TO_LIVE = 4
        const val DEFAULT_TIMEOUT = 3
    }
}