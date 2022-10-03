package com.network.scanner.core.scanner

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.tools.ping.PingDevice
import com.network.scanner.core.scanner.tools.ping.PingDeviceImpl

@RequiresApi(Build.VERSION_CODES.M)
interface NetScan {
    val pingDevice: PingDevice

    fun isWifiConnected(): Boolean

    fun isBluetoothConnected(): Boolean

    fun isPhoneNetworkConnected(): Boolean

    class Factory {
        fun create(context: Context?, lifecycleOwner: LifecycleOwner): NetScan =
            NetScanImpl(context, lifecycleOwner)
    }
}