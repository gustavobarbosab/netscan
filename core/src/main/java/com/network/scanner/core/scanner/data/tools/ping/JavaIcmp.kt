package com.network.scanner.core.scanner.data.tools.ping

import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.tools.PingOption
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import java.net.InetAddress
import java.util.concurrent.Executor

class JavaIcmp(
    private val netScanFacade: NetScanFacade,
    private val executor: Executor
) : PingOption {

    override fun execute(hostAddress: String): NetScanObservable<PingResult> {
        val listener = NetScanObservable<PingResult>()
        executor.execute {
            runCatching {
                val ipAddress = netScanFacade.getMyIpAddress()

                val pingAddress: InetAddress = netScanFacade.getInetAddress(hostAddress)
                val hostname: String = pingAddress.hostName
                val isReachable = pingAddress.isReachable(DEFAULT_TIMEOUT)
                listener.emit(PingResult(isReachable, hostAddress, hostname))
            }.onFailure(listener::throwException)
        }
        return listener
    }

    companion object {
        // TODO receive this params from function
        const val DEFAULT_TIME_TO_LIVE = 4
        const val DEFAULT_TIMEOUT = 3
    }
}