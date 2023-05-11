package com.network.scanner.core.data.tools.ping.java

import com.network.scanner.core.domain.tools.Worker
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.domain.entities.PingResult
import com.network.scanner.core.domain.exceptions.HostNotFoundException

class JavaPingWorker(
    private val netScanFacade: NetScanFacade,
    var hostAddress: String
) : Worker<PingResult> {
    override fun execute(): PingResult {
        val pingAddress = netScanFacade.getInetAddress(hostAddress)
        val hostname: String = pingAddress.hostName
        val isReachable = pingAddress.isReachable(JavaIcmp.DEFAULT_TIMEOUT)
        if (isReachable) {
            return PingResult(hostAddress, hostname)
        }
        throw HostNotFoundException("This device was not found")
    }
}