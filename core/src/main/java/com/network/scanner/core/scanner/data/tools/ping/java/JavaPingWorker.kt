package com.network.scanner.core.scanner.data.tools.ping.java

import com.network.scanner.core.scanner.data.Worker
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.exceptions.HostNotFoundException

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