package com.network.wifiscanner.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.observable.SubscribeResultDisposable
import com.network.scanner.core.domain.entities.observable.SubscribeScheduler
import com.network.wifiscanner.domain.WifiItemModel

class WifiScannerViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<WifiScannerState>()
    private val netScanObservableUnbind = SubscribeResultDisposable()

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
            .onScheduler(SubscribeScheduler.Main)
            .onResult { list ->
                screenState.value = WifiScannerState.LoadWifiList(
                    list.map {
                        WifiItemModel(it.ssid, it.bssid, it.capabilities)
                    }
                )
            }.onFailure {
                screenState.value = WifiScannerState.WifiDisconnected
            }.onComplete {
                screenState.value = WifiScannerState.DeviceSearchFinished
            }
            .also(netScanObservableUnbind::add)
    }

    override fun onCleared() {
        super.onCleared()
        netScanObservableUnbind.dispose()
    }

    companion object {
        const val TIMEOUT = 5000
    }
}
