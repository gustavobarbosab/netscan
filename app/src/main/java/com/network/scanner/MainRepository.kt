package com.network.scanner

import com.network.scanner.core.scanner.domain.NetScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val netScan: NetScan) {

    fun speed() = netScan.checkNetworkSpeed()

    fun isWifiConnected() = netScan.hasWifiConnection()

    fun pingDevice(host: String) = netScan.pingBySystem(host)

    suspend fun portScan(ipAddress: String, port: Int) = withContext(Dispatchers.IO) {
        return@withContext netScan.portScan(ipAddress, port, 5000)
    }
}