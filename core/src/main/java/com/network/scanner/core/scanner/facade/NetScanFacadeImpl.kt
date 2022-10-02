package com.network.scanner.core.scanner.facade

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.net.InetAddress
import java.net.NetworkInterface

class NetScanFacadeImpl(private var context: Context?) : NetScanFacade {

    private val connectivityManager: ConnectivityManager =
        context?.getSystemService(AppCompatActivity.CONNECTIVITY_SERVICE) as ConnectivityManager

    @RequiresApi(Build.VERSION_CODES.M)
    override fun getMyIpAddress(): String {
        val linkProperties =
            connectivityManager.getLinkProperties(connectivityManager.activeNetwork)
        return linkProperties?.linkAddresses
            ?.first()
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

    override fun unbind() {
        this.context = null
    }
}