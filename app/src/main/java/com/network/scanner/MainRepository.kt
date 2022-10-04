package com.network.scanner

import com.network.scanner.core.scanner.NetScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val netScan: NetScan) {

    fun isWifiConnected() = netScan.isWifiConnected()

    suspend fun pingDevice(ipDestiny: String) =
        withContext(Dispatchers.IO) {
            return@withContext netScan.pingDevice.ping(ipDestiny)
        }

    suspend fun findDevices() = withContext(Dispatchers.IO) {
        return@withContext netScan.networkDevices.findDevices()
    }
}