package com.network.wifiscanner.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.network.wifiscanner.domain.WifiItemModel
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.NetScanObservableUnbind
import com.network.scanner.core.domain.entities.NetScanScheduler
import java.lang.UnsupportedOperationException

class WifiScannerViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<WifiScannerState>()
    private val netScanObservableUnbind = NetScanObservableUnbind()

    fun wifiScan() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            screenState.value = WifiScannerState.UnsupportedDevice
            return
        }
        startScan()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun startScan() {
        screenState.value = WifiScannerState.SearchingDeviceList
        netScan.wifiScanner()
            .onScheduler(NetScanScheduler.Main)
            .onResult { list ->
//                screenState.value = WifiScannerState.LoadWifiList(
//                    list.map {
//                        WifiItemModel(it.ssid, it.bssid, it.capabilities)
//                    }
//                )
            }.onFailure {
                if (it is UnsupportedOperationException) {
                    screenState.value = WifiScannerState.WifiDisconnected
                }
            }.onComplete {
                screenState.value = WifiScannerState.DeviceSearchFinished
            }
            .also(netScanObservableUnbind::add)
    }

    override fun onCleared() {
        super.onCleared()
        netScanObservableUnbind.cancel()
    }

    companion object {
        const val TIMEOUT = 5000
    }
}
