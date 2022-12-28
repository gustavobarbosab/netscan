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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun hasWifiConnection(): Boolean =
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun hasCellularConnection() =
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun hasEthernetConnection() =
        networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ?: false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun hasSomeConnection() = hasCellularConnection()
            || hasWifiConnection()
            || hasEthernetConnection()

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasActiveNetwork(): Boolean = connectivityManager.activeNetwork != null

    override fun hasInternetConnection(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return hasActiveNetwork() && hasSomeConnection()
        }
        return connectivityManager.activeNetworkInfo?.isConnected ?: false
    }
}