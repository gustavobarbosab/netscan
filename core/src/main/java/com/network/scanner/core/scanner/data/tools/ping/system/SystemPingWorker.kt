package com.network.scanner.core.scanner.data.tools.ping.system

import android.util.Log
import com.network.scanner.core.scanner.data.Worker
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.exceptions.HostNotFoundException
import java.util.*

class SystemPingWorker(
    var hostAddress: String
) : Worker<PingResult> {

    override fun execute(): PingResult {
        val command = COMMAND.format(hostAddress)
        val process = Runtime.getRuntime().exec(command)
        val scan = Scanner(process.inputStream)
        while (scan.hasNextLine()) {
            Log.i("Log ping", scan.nextLine())
        }
        val isReachable = process.exitValue() == 0
        if (isReachable) {
            return PingResult(hostAddress, hostAddress)
        }
        throw HostNotFoundException("Host was not found...")
    }

    companion object {
        // TODO we can receive this parameters
        private const val COMMAND = "/system/bin/ping -n -w 10 -c 1 %s"
    }
}