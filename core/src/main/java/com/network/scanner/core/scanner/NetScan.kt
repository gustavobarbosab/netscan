package com.network.scanner.core.scanner

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.tools.ping.PingDevice

interface NetScan {
    fun pingDevice(): PingDevice.Builder

    class Factory {
        fun create(context: Context?, lifecycleOwner: LifecycleOwner): NetScan =
            NetScanImpl(context, lifecycleOwner)
    }
}