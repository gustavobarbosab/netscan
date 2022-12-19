package com.network.scanner.core.scanner.tools.connection

import android.os.Build
import androidx.annotation.RequiresApi

interface DeviceConnection {
    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasWifiConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasCellularConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasEthernetConnection(): Boolean

    @RequiresApi(value = Build.VERSION_CODES.M)
    fun hasSomeConnection(): Boolean

    fun hasInternetConnection(): Boolean
}