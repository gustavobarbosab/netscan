package com.network.scanner.core.data.tools.speed

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.data.facade.NetScanFacade
import com.network.scanner.core.domain.entities.NetworkSpeedResult
import com.network.scanner.core.domain.tools.NetworkSpeed

class NetworkSpeedImpl(private val facade: NetScanFacade) : NetworkSpeed {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun checkSpeed(): NetworkSpeedResult {
        val networkCapabilities = facade.getNetworkCapabilities()
        val downSpeed = networkCapabilities.linkDownstreamBandwidthKbps
        val upSpeed = networkCapabilities.linkUpstreamBandwidthKbps
        return NetworkSpeedResult(downSpeed, upSpeed)
    }
}