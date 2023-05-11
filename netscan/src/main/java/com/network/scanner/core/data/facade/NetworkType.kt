package com.network.scanner.core.data.facade

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

sealed class NetworkType(
    val transportByCapability: Int,
    val transportByConnectivityManager: Int
) {
    object Wifi : NetworkType(NetworkCapabilities.TRANSPORT_WIFI, ConnectivityManager.TYPE_WIFI)
    object Mobile : NetworkType(NetworkCapabilities.TRANSPORT_CELLULAR, ConnectivityManager.TYPE_MOBILE)
    object Ethernet : NetworkType(NetworkCapabilities.TRANSPORT_ETHERNET, ConnectivityManager.TYPE_ETHERNET)
}