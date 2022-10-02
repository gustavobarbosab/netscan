package com.network.scanner.core.scanner.facade

import android.os.Build
import androidx.annotation.RequiresApi
import java.net.InetAddress
import java.net.NetworkInterface

interface NetScanFacade {
    @RequiresApi(Build.VERSION_CODES.M)
    fun getMyIpAddress(): String

    fun getMyMacAddress(): String

    fun getNetworkInterface(ipAddress: String): NetworkInterface

    fun getInetAddress(ipAddress: String): InetAddress

    fun unbind()
}