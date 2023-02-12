package com.network.scanner.core.scanner.data.tools.ping.system

import com.network.scanner.core.scanner.data.Worker
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.exceptions.HostNotFoundException
import java.net.InetAddress

class SystemPingWorker(
    var hostAddress: String
) : Worker<PingResult> {

    override fun execute(): PingResult {
        val command = COMMAND.format(hostAddress)
        val process = Runtime.getRuntime().exec(command)
        val exitValue = process.waitFor()
        process.apply {
            inputStream.close()
            outputStream.close()
            errorStream.close()
        }

        val isReachable = exitValue == 0
        if (isReachable) {
            val inetAddress = InetAddress.getByName(hostAddress)
            val hostName = inetAddress.hostName
            return PingResult(hostAddress, hostName)
        }
        throw HostNotFoundException("Host was not found...")
    }

    companion object {
        private const val COMMAND = "/system/bin/ping -c 1 %s"
    }
}