package com.network.scanner.core.scanner.data.tools.connection

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.data.facade.NetScanFacade
import com.network.scanner.core.scanner.domain.tools.DeviceConnection

class DeviceConnectionImpl(
    private val facade: NetScanFacade
) : DeviceConnection {

    private val connectivityManager
        get() = facade.connectivityManager

    private val networkCapabilities
        @RequiresApi(Build.VERSION_CODES.M)
        get() = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    override fun hasWifiConnection(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false
        } else {
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.isConnected
        } ?: false

    override fun hasCellularConnection() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
        } else {
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.isConnected
        } ?: false

    override fun hasEthernetConnection() =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ?: false
        } else {
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET)?.isConnected
        } ?: false

    override fun hasInternetConnection() = hasCellularConnection()
            || hasWifiConnection()
            || hasEthernetConnection()
}