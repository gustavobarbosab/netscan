package com.network.scanner.core.scanner.tools.ping

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import java.net.InetAddress
import java.net.NetworkInterface

class PingDevice(private val netScanFacade: NetScanFacade) {

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun ping(attrs: PingAttributes): List<PingResponse> {
        val ipAddress = netScanFacade.getMyIpAddress()

        val iFace: NetworkInterface = netScanFacade.getNetworkInterface(ipAddress)
        val pingList = mutableListOf<PingResponse>()

        repeat(attrs.attempts) { index ->
            val startTime = System.nanoTime()
            val pingAddress: InetAddress = netScanFacade.getInetAddress(attrs.ipDestiny)
            val isReachable = pingAddress.isReachable(iFace, attrs.timeToLive, attrs.timeout)
            var pingResponse: PingResponse = PingResponse.TimeOut

            if (isReachable) {
                val endTime = System.nanoTime()
                pingResponse = PingResponse.Success(
                    origin = ipAddress,
                    icmpSequence = index,
                    ttl = attrs.timeToLive,
                    time = endTime - startTime
                )
            }

            pingList.add(pingResponse)
        }
        return pingList
    }

    class Builder(private val netScanFacade: NetScanFacade) {
        private lateinit var ipAddress: String
        private var timeToLive = DEFAULT_TIME_TO_LIVE
        private var timeout = DEFAULT_TIMEOUT
        private var attempts = DEFAULT_ATTEMPTS

        fun ipDestiny(ipAddress: String) = apply { this.ipAddress = ipAddress }

        fun ttl(time: Int) = apply { this.timeToLive = time }

        fun timeout(time: Int) = apply { this.timeout = time }

        fun attempts(attempts: Int) = apply { this.attempts = attempts }

        @RequiresApi(Build.VERSION_CODES.M)
        fun start(): List<PingResponse> {
            val pingDevice = PingDevice(netScanFacade)
            val attrs = PingAttributes(
                ipDestiny = this@Builder.ipAddress,
                timeToLive = this@Builder.timeToLive,
                timeout = this@Builder.timeout,
                attempts = this@Builder.attempts
            )
            return pingDevice.ping(attrs)
        }
    }

    sealed class PingResponse {
        data class Success(
            val origin: String,
            val icmpSequence: Int,
            val ttl: Int,
            val time: Long
        ) : PingResponse()

        object TimeOut : PingResponse()
    }

    companion object {
        private const val DEFAULT_TIME_TO_LIVE = 200
        private const val DEFAULT_TIMEOUT = 50
        private const val DEFAULT_ATTEMPTS = 1
    }
}