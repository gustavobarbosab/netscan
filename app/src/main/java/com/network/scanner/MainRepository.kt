package com.network.scanner

import com.network.scanner.core.scanner.NetScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val netScan: NetScan) {

    fun isWifiConnected() = netScan.isWifiConnected()

    suspend fun pingDevice(host: String) =
        withContext(Dispatchers.IO) {
            return@withContext netScan.pingBySystem().execute(host)
        }

    suspend fun scanNetworkDevices() = withContext(Dispatchers.IO) {
        // TODO talvez remover a redundancia de métodos
        return@withContext netScan.findNetworkDevices().findDevices()
    }

    suspend fun portScan(ipAddress: String, port: Int) = withContext(Dispatchers.IO) {
        return@withContext netScan.portScan(ipAddress, port, 5000)
    }
}