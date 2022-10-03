package com.network.scanner

import com.network.scanner.core.scanner.NetScan
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val netScan: NetScan) {

    suspend fun pingDevice(ipDestiny: String) =
        withContext(Dispatchers.IO) {
            return@withContext netScan.pingDevice()
                .ipDestiny(ipDestiny)
                .attempts(20)
                .start()
        }
}