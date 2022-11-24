package com.network.scanner.core.scanner.factory

import android.content.Context
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.facade.NetScanFacadeImpl
import java.lang.ref.WeakReference

object NetScanFactory {
    fun provideFacade(context: WeakReference<Context>): NetScanFacade = NetScanFacadeImpl(context)
}