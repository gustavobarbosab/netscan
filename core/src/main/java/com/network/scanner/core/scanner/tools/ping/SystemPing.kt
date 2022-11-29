package com.network.scanner.core.scanner.tools.ping

import android.util.Log
import com.network.scanner.core.scanner.model.NetScanObservable
import java.util.*
import java.util.concurrent.Executor

class SystemPing(private val executor: Executor) : PingOption {
    private val listener = NetScanObservable()

    override fun execute(host: String): NetScanObservable {
        executor.execute {
            runCatching {
                val command = COMMAND.format(host)
                val process = Runtime.getRuntime().exec(command)
                val scan = Scanner(process.inputStream)
                while (scan.hasNextLine()) {
                    Log.i("Log ping", scan.nextLine())
                }
                val isReachable = process.exitValue() == 0
                val result = PingResult(isReachable, host)
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