package com.network.scanner.core.data.facade

import android.net.NetworkCapabilities
import java.net.InetAddress
import java.net.NetworkInterface

interface NetScanFacade {
    fun hasConnection(networkType: NetworkType): Boolean
    fun getMyIpAddress(): String
    fun getInetAddress(ipAddress: String): InetAddress
    fun getNetworkCapabilities(): NetworkCapabilities
}