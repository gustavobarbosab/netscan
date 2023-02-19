package com.network.scanner.core.scanner.domain.tools

import android.os.Build
import androidx.annotation.RequiresApi

interface DeviceConnection {
    fun hasWifiConnection(): Boolean

    fun hasCellularConnection(): Boolean

    fun hasEthernetConnection(): Boolean

    fun hasInternetConnection(): Boolean
}