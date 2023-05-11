package com.network.scanner.core.data.facade

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.net.InetAddress
import java.net.NetworkInterface

class NetScanFacadeImpl(private val context: WeakReference<Context>) : NetScanFacade {

    private val connectivityManager: ConnectivityManager
        get() = context.get()
            ?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun hasConnection(networkType: NetworkType): Boolean = runCatching {
        return@runCatching if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getNetworkCapabilities().hasTransport(networkType.transportByCapability)
        } else {
            connectivityManager.getNetworkInfo(networkType.transportByConnectivityManager)?.isConnected
        }
    }.getOrNull() ?: false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getMyIpAddress(): String {
        val linkProperties =
            connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
        return linkProperties?.linkAddresses
            ?.map { it.address.toString() }
            ?.first { it.contains(Regex(IP_V4_REGEX)) }
            ?.filter { Regex(NUMBER_OR_DOT).matches(it.toString()) }
            .orEmpty()
    }

    override fun getInetAddress(ipAddress: String): InetAddress = InetAddress.getByName(ipAddress)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getNetworkCapabilities(): NetworkCapabilities =
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?: throw UnsupportedOperationException()

    companion object {
        private const val IP_V4_REGEX = "\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}"
        private const val NUMBER_OR_DOT = "\\d|[.]"
    }
}