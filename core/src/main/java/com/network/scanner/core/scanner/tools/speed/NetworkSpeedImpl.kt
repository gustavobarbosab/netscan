package com.network.scanner.core.scanner.tools.speed

import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkSpeedImpl(private val connectivityManager: ConnectivityManager) : NetworkSpeed {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkSpeed(): NetworkSpeedResult {
        val nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val downSpeed = nc?.linkDownstreamBandwidthKbps
            ?: throw IllegalAccessException("An unexpected behavior happened..")
        val upSpeed = nc.linkUpstreamBandwidthKbps
        return NetworkSpeedResult(downSpeed, upSpeed)
    }
}