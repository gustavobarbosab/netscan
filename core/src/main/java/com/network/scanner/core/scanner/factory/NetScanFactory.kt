package com.network.scanner.core.scanner.factory

import android.content.Context
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.facade.NetScanFacadeImpl

object NetScanFactory {

    fun provideFacade(context: Context?): NetScanFacade = NetScanFacadeImpl(context)
}