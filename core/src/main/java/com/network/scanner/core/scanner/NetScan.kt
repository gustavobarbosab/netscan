package com.network.scanner.core.scanner

import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.model.NetScanObservable
import com.network.scanner.core.scanner.tools.devicescanner.DeviceScanner
import com.network.scanner.core.scanner.tools.ping.PingOption
import com.network.scanner.core.scanner.tools.portscan.PortScanResult
import java.lang.ref.WeakReference

interface NetScan {

    @RequiresApi(Build.VERSION_CODES.M)
    fun pingByIcmp(): PingOption

    fun pingBySystem(): PingOption

    fun findNetworkDevices(): DeviceScanner

    @RequiresApi(Build.VERSION_CODES.M)
    fun isWifiConnected(): Boolean

    @RequiresApi(Build.VERSION_CODES.M)
    fun isBluetoothConnected(): Boolean

    @RequiresApi(Build.VERSION_CODES.M)
    fun isPhoneNetworkConnected(): Boolean

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