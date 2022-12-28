package com.network.scanner.core.scanner.data.tools.ping

import android.util.Log
import com.network.scanner.core.scanner.domain.entities.PingResult
import com.network.scanner.core.scanner.domain.tools.PingOption
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import java.util.*
import java.util.concurrent.Executor

class SystemPing(private val executor: Executor) : PingOption {

    override fun execute(hostAddress: String): NetScanObservable<PingResult> {
        val listener = NetScanObservable<PingResult>()
        executor.execute {
            runCatching {
                val command = COMMAND.format(hostAddress)
                val process = Runtime.getRuntime().exec(command)
                val scan = Scanner(process.inputStream)
                while (scan.hasNextLine()) {
                    Log.i("Log ping", scan.nextLine())
                }
                val isReachable = process.exitValue() == 0
                val result = PingResult(isReachable, hostAddress, String())
                listener.emit(result)
            }.onFailure(listener::throwException)
        }
        return listener
    }

    companion object {
        // TODO we can receive this parameters
        private const val COMMAND = "/system/bin/ping -n -w 10 -c 1 %s"
    }
}