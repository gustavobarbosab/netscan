package com.network.scanner

import androidx.lifecycle.ViewModel
import com.network.scanner.core.scanner.NetScanImpl

class MainViewModel(
    val netScan: NetScanImpl
) : ViewModel() {

    fun findDevices() {

    }
}