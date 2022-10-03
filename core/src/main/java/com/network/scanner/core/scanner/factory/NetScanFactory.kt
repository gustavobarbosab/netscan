package com.network.scanner.core.scanner.factory

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.network.scanner.core.scanner.facade.NetScanFacade
import com.network.scanner.core.scanner.facade.NetScanFacadeImpl

@RequiresApi(Build.VERSION_CODES.M)
object NetScanFactory {
    fun provideFacade(context: Context?): NetScanFacade = NetScanFacadeImpl(context)
}