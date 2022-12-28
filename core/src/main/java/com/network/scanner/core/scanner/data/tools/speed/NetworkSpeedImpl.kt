package com.network.scanner.core.scanner.data.tools.speed

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.domain.entities.NetworkSpeedResult
import com.network.scanner.core.scanner.domain.tools.NetworkSpeed

class NetworkSpeedImpl(private val facade: NetScanFacade) : NetworkSpeed {

    private val connectivityManager
        get() = facade.connectivityManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkSpeed(): NetworkSpeedResult {
        val nc = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val downSpeed = nc?.linkDownstreamBandwidthKbps
            ?: throw IllegalAccessException("An unexpected behavior happened..")
        val upSpeed = nc.linkUpstreamBandwidthKbps
        return NetworkSpeedResult(downSpeed, upSpeed)
    }
}