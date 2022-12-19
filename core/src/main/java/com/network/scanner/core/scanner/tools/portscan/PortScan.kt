package com.network.scanner.core.scanner.tools.portscan

import com.network.scanner.core.scanner.model.NetScanObservable
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketAddress
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
                val socket = Socket()
                val socketAddress: SocketAddress = InetSocketAddress(ip, port)
                socket.connect(socketAddress, timeout)
                val result = if (socket.isConnected) {
                    PortScanResult.DeviceFound
                } else {
                    PortScanResult.DeviceNotFound
                }
                listener.emit(result)
                socket.close()
            }.onFailure(listener::throwException)
        }
        return listener
    }

    companion object {
        private const val DEFAULT_TIMEOUT = 5000
    }
}