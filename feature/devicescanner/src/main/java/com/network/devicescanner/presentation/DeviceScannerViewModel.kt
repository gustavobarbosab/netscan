package com.network.devicescanner.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.NetScanObservableUnbind

class DeviceScannerViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<DeviceScannerState>()
    private val netScanObservableUnbind = NetScanObservableUnbind()

    fun scanDevices(hasNotPermission: Boolean) {
        if (hasNotPermission) {
            screenState.value = DeviceScannerState.RequestPermission
            return
        }

        screenState.value = DeviceScannerState.SearchingDeviceList
        netScan.domesticDeviceListScanner()
            .onResult {
                Log.i("Chegooou!", it.hostname)
                screenState.value = DeviceScannerState.AddDevice(it)
            }.onComplete {
                Log.e("Teste222","Passou aqui")
                screenState.value = DeviceScannerState.DeviceSearchFinished
            }
            .also(netScanObservableUnbind::add)

    }

    override fun onCleared() {
        super.onCleared()
        netScanObservableUnbind.cancel()
    }
}