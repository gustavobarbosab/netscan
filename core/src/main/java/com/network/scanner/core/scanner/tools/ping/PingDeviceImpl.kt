package com.network.scanner.core.scanner.tools.ping

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.tools.ping.PingDevice.PingResponse
import java.net.InetAddress
import java.net.NetworkInterface

@RequiresApi(Build.VERSION_CODES.M)
class PingDeviceImpl(private val netScanFacade: NetScanFacade) : PingDevice {

    override fun ping(
        ipDestiny: String,
        timeToLive: Int,
        timeout: Int
    ): PingResponse {
        val ipAddress = netScanFacade.getMyIpAddress()

        val iFace: NetworkInterface = netScanFacade.getNetworkInterface(ipAddress)

        val startTime = System.nanoTime()
        val pingAddress: InetAddress = netScanFacade.getInetAddress(ipDestiny)
        val isReachable = pingAddress.isReachable(iFace, timeToLive, timeout)
        var pingResponse: PingResponse = PingResponse.TimeOut

        if (isReachable) {
            val endTime = System.nanoTime()
            pingResponse = PingResponse.Success(
                origin = ipAddress,
                ttl = timeToLive,
                time = endTime - startTime
            )
        }
        return pingResponse
    }
}