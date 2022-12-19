package com.network.scanner.core.scanner.facade

import android.net.ConnectivityManager
import java.net.InetAddress
import java.net.NetworkInterface

interface NetScanFacade {
    val connectivityManager: ConnectivityManager

    fun getMyIpAddress(): String

    fun getMyMacAddress(): String

    fun getNetworkInterface(ipAddress: String): NetworkInterface

    fun getInetAddress(ipAddress: String): InetAddress
}