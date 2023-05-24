package com.network.devicescanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.network.devicescanner.domain.DeviceItem
import com.network.devicescanner.domain.VulnerablePortsMirai
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.DeviceInfoResult
import com.network.scanner.core.domain.entities.PortScanResult
import com.network.scanner.core.domain.entities.observable.SubscribeResultDisposable
import com.network.scanner.core.domain.entities.observable.SubscribeScheduler
import com.network.scanner.core.domain.exceptions.InternetConnectionNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeviceScannerViewModel(private val netScan: NetScan) : ViewModel() {

    val screenState = SingleLiveEvent<DeviceScannerState>()
    private val netScanObservableUnbind = SubscribeResultDisposable()

    fun scanDevices(hasNotPermission: Boolean) {
        if (hasNotPermission) {
            screenState.value = DeviceScannerState.RequestPermission
            return
        }

        screenState.value = DeviceScannerState.SearchingDeviceList
        netScan.domesticDeviceListScanner()
            .onScheduler(SubscribeScheduler.Main)
            .onResult {
                findEnabledPorts(it)
            }.onFailure {
                if (it is InternetConnectionNotFoundException) {
                    screenState.value = DeviceScannerState.WifiDisconnected
                }
            }.onComplete {
                screenState.value = DeviceScannerState.DeviceSearchFinished
            }
            .also(netScanObservableUnbind::add)

    }

    private fun findEnabledPorts(device: DeviceInfoResult) {
        viewModelScope.launch {
            val hashMap = mutableMapOf<VulnerablePortsMirai, PortScanResult?>()
            withContext(Dispatchers.IO) {
                VulnerablePortsMirai.values().forEach {
                    val response = runCatching {
                        return@runCatching netScan.portScan(
                            device.address,
                            it.portNumber,
                            TIMEOUT
                        )
                    }
                    hashMap[it] = response.getOrNull()
                }
            }

            screenState.value = DeviceScannerState.AddDevice(
                DeviceItem(
                    device.address,
                    hashMap[VulnerablePortsMirai.PORT_23] != null,
                    hashMap[VulnerablePortsMirai.PORT_2323] != null,
                    hashMap[VulnerablePortsMirai.PORT_48101] != null
                )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        netScanObservableUnbind.dispose()
    }

    companion object {
        const val TIMEOUT = 5000

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>
            ): T {
                return DeviceScannerViewModel(NetScan.requireInstance()) as T
            }
        }
    }
}