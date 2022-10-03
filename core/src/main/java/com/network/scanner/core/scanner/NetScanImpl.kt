package com.network.scanner.core.scanner

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.factory.NetScanFactory
import com.network.scanner.core.scanner.tools.ping.PingDevice

class NetScanImpl internal constructor(
    private var context: Context?,
    private var lifecycleOwner: LifecycleOwner?
) : NetScan, LifecycleEventObserver {

    init {
        lifecycleOwner?.lifecycle?.addObserver(this)
    }

    private val facade: NetScanFacade by lazy {
        NetScanFactory.provideFacade(context)
    }

    override fun pingDevice() = PingDevice.Builder(facade)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            facade.unbind()
            lifecycleOwner?.lifecycle?.removeObserver(this)
            this.lifecycleOwner = null
            this.context = null
        }
    }
}