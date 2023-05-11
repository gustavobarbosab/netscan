package com.network.scanner.core.data.tools.portscan

import com.network.scanner.core.domain.tools.Worker
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.exceptions.HostNotFoundException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress

class PortScanWorker(
    var ipAddress: String,
    var port: Int,
    var timeout: Int
) : Worker<PortScanResult> {

    override fun execute(): PortScanResult {
        val startTime = System.currentTimeMillis()
        val socket = Socket()
        val socketAddress: SocketAddress = InetSocketAddress(ipAddress, port)
        socket.connect(socketAddress, timeout)
        if (socket.isConnected.not()) {
            throw HostNotFoundException("Invalid host address or port.")
        }
        socket.close()
        val endTime = System.currentTimeMillis()
        return PortScanResult(
            elapsedTimeMillis = endTime - startTime,
            portScanned = port,
            hostAddress = ipAddress
        )
    }
}