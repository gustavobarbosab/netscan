package com.network.scanner.core.scanner

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

    class Factory {
        fun create(context: Context?, lifecycleOwner: LifecycleOwner): NetScan =
            NetScanImpl(WeakReference(context), lifecycleOwner)
    }
}