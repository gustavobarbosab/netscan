package com.network.scanner

import com.network.scanner.core.scanner.NetScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val netScan: NetScan) {

    fun isWifiConnected() = netScan.hasWifiConnection()

    suspend fun pingDevice(host: String) =
        withContext(Dispatchers.IO) {
            return@withContext netScan.pingByIcmp(host)
        }

    suspend fun portScan(ipAddress: String, port: Int) = withContext(Dispatchers.IO) {
        return@withContext netScan.portScan(ipAddress, port, 5000)
    }
}