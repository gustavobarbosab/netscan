package com.network.scanner.core.scanner.data.facade

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.net.InetAddress
import java.net.NetworkInterface

class NetScanFacadeImpl(context: WeakReference<Context>) : NetScanFacade {

    override val connectivityManager: ConnectivityManager =
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

    companion object {
        private const val IP_V4_REGEX = "\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}[.]\\d{1,3}"
        private const val NUMBER_OR_DOT = "\\d|[.]"
    }
}