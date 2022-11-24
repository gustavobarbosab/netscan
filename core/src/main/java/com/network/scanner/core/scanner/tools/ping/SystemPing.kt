package com.network.scanner.core.scanner.tools.ping

import android.util.Log
import com.network.scanner.core.scanner.model.NetScanObservable
import java.util.*

class SystemPing : PingOption {

    override fun execute(host: String): NetScanObservable {
        val listener = NetScanObservable()
        startPing(host) { reachable ->
            if (reachable) listener.emit()
            else listener.throwException()
        }
        return listener
    }

    private fun startPing(
        host: String,
        onResult: (reachable: Boolean) -> Unit
    ) {
        runCatching {
            val command = COMMAND.format(host)
            val process = Runtime.getRuntime().exec(command)
            val scan = Scanner(process.inputStream)
            while (scan.hasNextLine()) {
                val response = scan.nextLine()
                Log.i(TAG, response)
                onResult(process.exitValue() == 0)
            }
        }.onFailure { onResult(false) }
    }

    companion object {
        private const val TAG = "Ping"
        private const val COMMAND = "/system/bin/ping -n -w 10 -c 5 %s"
    }
}