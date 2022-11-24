package com.network.scanner.core.scanner.facade

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.net.InetAddress
import java.net.NetworkInterface

class NetScanFacadeImpl(private var context: WeakReference<Context>) : NetScanFacade {

    private val connectivityManager: ConnectivityManager =
        context.get()
            ?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

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

    override fun getMyMacAddress(): String {
        TODO("Not yet implemented")
    }

    override fun getNetworkInterface(ipAddress: String): NetworkInterface =
        NetworkInterface.getByInetAddress(
            InetAddress.getByName(ipAddress)
        )

    override fun getInetAddress(ipAddress: String): InetAddress = InetAddress.getByName(ipAddress)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isWifiConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_WIFI)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isPhoneNetworkConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_CELLULAR)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun isBluetoothConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_BLUETOOTH)

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNetworkCapabilities(netType: Int): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasTransport(netType) ?: false
    }

    override fun unbind() {
        this.context.clear()
    }

    companion object {
        private const val IP_V4_REGEX = "\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}"
        private const val NUMBER_OR_DOT = "\\d|[.]"
    }
}