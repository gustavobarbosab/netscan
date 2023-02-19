package com.network.devicescanner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.network.devicescanner.domain.DeviceItem
import com.network.devicescanner.domain.VulnerablePortsMirai
import com.network.scanner.common.SingleLiveEvent
import com.network.scanner.core.scanner.domain.NetScan
import com.network.scanner.core.scanner.domain.entities.DeviceInfo
import com.network.scanner.core.scanner.domain.entities.NetScanObservableUnbind
import com.network.scanner.core.scanner.domain.entities.NetScanScheduler
import com.network.scanner.core.scanner.domain.entities.PortScanResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.UnsupportedOperationException

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
            .onScheduler(NetScanScheduler.Main)
            .onResult {
                findEnabledPorts(it)
            }.onFailure {
                if (it is UnsupportedOperationException) {
                    screenState.value = DeviceScannerState.WifiDisconnected
                }
            }.onComplete {
                screenState.value = DeviceScannerState.DeviceSearchFinished
            }
            .also(netScanObservableUnbind::add)

    }

    private fun findEnabledPorts(device: DeviceInfo) {
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
        netScanObservableUnbind.cancel()
    }

    companion object {
        const val TIMEOUT = 5000
    }
}