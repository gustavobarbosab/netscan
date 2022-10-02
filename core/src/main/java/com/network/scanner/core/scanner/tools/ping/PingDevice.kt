package com.network.scanner.core.scanner.tools.ping

import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import java.net.InetAddress
import java.net.NetworkInterface

class PingDevice(private val netScanFacade: NetScanFacade) {

    private var onSuccess: (PingResponse) -> Unit = {}

    private var onFailure: (Throwable) -> Unit = {}

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun ping(attrs: PingAttributes) {
        val ipAddress = netScanFacade.getMyIpAddress()
        runCatching {
            val iFace: NetworkInterface = netScanFacade.getNetworkInterface(ipAddress)

            repeat(attrs.attempts) { index ->
                val startTime = System.nanoTime()
                val pingAddress: InetAddress = netScanFacade.getInetAddress(attrs.ipDestiny)
                val isReachable = pingAddress.isReachable(iFace, attrs.timeToLive, attrs.timeout)

                if (!isReachable) {
                    onFailure(DeviceNotFound())
                    return@repeat
                }

                val endTime = System.nanoTime()
                onSuccess(
                    PingResponse(
                        origin = ipAddress,
                        icmpSequence = index,
                        ttl = attrs.timeToLive,
                        time = endTime - startTime
                    )
                )
            }
        }.onFailure(onFailure)
    }

    internal fun setOnSuccess(success: (PingResponse) -> Unit) {
        this.onSuccess = success
    }

    internal fun setOnFailure(failure: (Throwable) -> Unit) {
        this.onFailure = failure
    }

    class Builder(private val netScanFacade: NetScanFacade) {
        private lateinit var ipAddress: String
        private var timeToLive = DEFAULT_TIME_TO_LIVE
        private var timeout = DEFAULT_TIMEOUT
        private var attempts = DEFAULT_ATTEMPTS
        private lateinit var success: (PingResponse) -> Unit
        private var failure: (Throwable) -> Unit = {}

        fun ipDestiny(ipAddress: String) = apply { this.ipAddress = ipAddress }

        fun ttl(time: Int) = apply { this.timeToLive = time }

        fun timeout(time: Int) = apply { this.timeout = time }

        fun attempts(attempts: Int) = apply { this.attempts = attempts }

        fun onSuccess(success: (PingResponse) -> Unit) {
            this.success = success
        }

        fun onFailure(failure: (Throwable) -> Unit) {
            this.failure = failure
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun start(): PingDevice = PingDevice(netScanFacade).apply {
            setOnFailure(failure)
            setOnSuccess(success)
            val attrs = PingAttributes(
                ipDestiny = this@Builder.ipAddress,
                timeToLive = this@Builder.timeToLive,
                timeout = this@Builder.timeout,
                attempts = this@Builder.attempts
            )
            ping(attrs)
        }
    }

    data class PingResponse(
        val origin: String,
        val icmpSequence: Int,
        val ttl: Int,
        val time: Long
    )

    class DeviceNotFound : Throwable()

    companion object {
        private const val DEFAULT_TIME_TO_LIVE = 200
        private const val DEFAULT_TIMEOUT = 50
        private const val DEFAULT_ATTEMPTS = 1
    }
}