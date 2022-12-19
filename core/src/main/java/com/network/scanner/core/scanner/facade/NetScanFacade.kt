package com.network.scanner.core.scanner.facade

import java.net.InetAddress
import java.net.NetworkInterface

interface NetScanFacade {
    fun getMyIpAddress(): String

    fun getMyMacAddress(): String

    fun getNetworkInterface(ipAddress: String): NetworkInterface

    fun getInetAddress(ipAddress: String): InetAddress

    fun isWifiConnected(): Boolean

    fun isPhoneNetworkConnected(): Boolean

    fun isBluetoothConnected(): Boolean
}