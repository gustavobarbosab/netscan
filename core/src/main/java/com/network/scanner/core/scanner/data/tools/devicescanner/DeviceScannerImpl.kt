package com.network.scanner.core.scanner.data.tools.devicescanner

import android.util.Log
import com.network.scanner.core.scanner.domain.entities.DeviceAddressResult
import com.network.scanner.core.scanner.domain.tools.DeviceScanner
import com.network.scanner.core.scanner.domain.entities.NetScanObservable
import java.util.*
import java.util.concurrent.Executor

class DeviceScannerImpl(
    private val executor: Executor
) : DeviceScanner {

    private val listener = NetScanObservable<List<DeviceAddressResult>>()

    override fun findDevices(): NetScanObservable<List<DeviceAddressResult>> {
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