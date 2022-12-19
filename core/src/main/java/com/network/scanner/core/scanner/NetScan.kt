package com.network.scanner.core.scanner

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.model.NetScanObservable
import com.network.scanner.core.scanner.tools.ping.PingResult
import com.network.scanner.core.scanner.tools.portscan.PortScanResult

interface NetScan {

    @RequiresApi(Build.VERSION_CODES.M)
    fun pingByIcmp(host: String): NetScanObservable<PingResult>

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasWifiConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasCellularConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasEthernetConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasSomeConnection(): Boolean

    fun hasInternetConnection(): Boolean

    fun pingBySystem(host: String): NetScanObservable<PingResult>

    fun portScan(ipAddress: String, port: Int, timeout: Int): NetScanObservable<PortScanResult>

    companion object {
        var instance: NetScan? = null

        fun init(application: Application): NetScan {
            instance = NetScanImpl(application)
            return instance!!
        }

        fun requireInstance(): NetScan = try {
            instance!!
        } catch (exception: TypeCastException) {
            throw TypeCastException("Please, initialize the library using the method NetScan.init() in your Aplication")
        }
    }
}