package com.network.scanner.core.scanner.tools.devicescanner

import android.util.Log
import com.network.scanner.core.scanner.model.NetScanObservable
import java.util.*
import java.util.concurrent.Executor

class DeviceScannerImpl(
    private val executor: Executor
) : DeviceScanner {

    private val listener = NetScanObservable<List<DeviceAddress>>()

    override fun findDevices(): NetScanObservable<List<DeviceAddress>> {
        executor.execute {
            runCatching {
                val process = Runtime.getRuntime().exec(COMMAND)
                val scan = Scanner(process.errorStream)
                while (scan.hasNextLine()) {
                    val line = scan.nextLine()
                    Log.i("Log ip neigh", line)
//                    val result = DeviceAddress()
                }

                listener.emit(listOf())
            }.onFailure(listener::throwException)

        }
        return listener
    }

    companion object {
        private const val COMMAND = "/system/bin/ip neigh show"
    }
}