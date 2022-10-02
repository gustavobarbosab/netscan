package com.network.scanner.core.scanner.tools.devicelist

import android.content.Context

class NetworkDeviceList(
    private val context: Context
) {
//    private val manager =
//        (context.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager)
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private val linkProperties = manager.getLinkProperties(manager.activeNetwork)
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    fun ping(
//        ipDestiny: String,
//        timeToLive: Int,
//        timeout: Int,
//        attempts: Int,
//        onSuccess: (PingInfo) -> Unit,
//        onFailure: (Throwable) -> Unit
//    ) {
//        val ipAddress = linkProperties?.linkAddresses?.first()?.address.toString()
//            .filter { it.isDigit() || '.' == it }
//
//        kotlin.runCatching {
//            val iFace: NetworkInterface =
//                NetworkInterface.getByInetAddress(InetAddress.getByName(ipAddress))
//            val pingAddress: InetAddress = InetAddress.getByName(ipDestiny)
//
//            if (pingAddress.isReachable(iFace, timeToLive, timeout)) {
//                val ip = pingAddress.hostAddress.orEmpty()
//
//            } else {
//
//            }
//
//        }.onFailure(onFailure)
//    }
//
//
//
//
//    class PingDeviceBuilder(context: Context) {
//
//        private var timeToLive = DEFAULT_TIME_TO_LIVE
//        private var timeout = DEFAULT_TIMEOUT
//
//        fun ttl(time: Int) = apply { this.timeToLive = time }
//
//        fun timeout(time: Int) = apply { this.timeout = time }
//    }
//
//    companion object {
//        const val IP_TOTAL_RANGE = 255
//        const val INITIAL_IP = 0
//        const val DEFAULT_TIME_TO_LIVE = 200
//        const val DEFAULT_TIMEOUT = 50
//    }
}