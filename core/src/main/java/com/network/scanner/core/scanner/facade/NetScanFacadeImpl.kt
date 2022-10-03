package com.network.scanner.core.scanner.facade

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.net.InetAddress
import java.net.NetworkInterface

@RequiresApi(Build.VERSION_CODES.M)
class NetScanFacadeImpl(private var context: Context?) : NetScanFacade {

    private val connectivityManager: ConnectivityManager =
        context?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun getMyIpAddress(): String {
        val linkProperties =
            connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
        return linkProperties?.linkAddresses
            ?.get(1)
            ?.address
            .toString()
            .filter(this::filterIpAddress)
    }

    private fun filterIpAddress(ipChar: Char): Boolean = ipChar.isDigit() || '.' == ipChar

    override fun getMyMacAddress(): String {
        TODO("Not yet implemented")
    }

    override fun getNetworkInterface(ipAddress: String): NetworkInterface =
        NetworkInterface.getByInetAddress(
            InetAddress.getByName(ipAddress)
        )

    override fun getInetAddress(ipAddress: String): InetAddress = InetAddress.getByName(ipAddress)

    override fun isWifiConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_WIFI)

    override fun isPhoneNetworkConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_CELLULAR)

    override fun isBluetoothConnected(): Boolean =
        checkNetworkCapabilities(NetworkCapabilities.TRANSPORT_BLUETOOTH)

    private fun checkNetworkCapabilities(netType: Int): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return capabilities?.hasTransport(netType) ?: false
    }

    override fun unbind() {
        this.context = null
    }
}