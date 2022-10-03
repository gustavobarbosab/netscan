package com.network.scanner.core.scanner.facade

import android.os.Build
import androidx.annotation.RequiresApi
import java.net.InetAddress
import java.net.NetworkInterface

@RequiresApi(Build.VERSION_CODES.M)
interface NetScanFacade {
    fun getMyIpAddress(): String

    fun getMyMacAddress(): String

    fun getNetworkInterface(ipAddress: String): NetworkInterface

    fun getInetAddress(ipAddress: String): InetAddress

    fun isWifiConnected(): Boolean

    fun isPhoneNetworkConnected(): Boolean

    fun isBluetoothConnected(): Boolean

    fun unbind()
}