package com.network.scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import com.network.scanner.core.domain.NetScan
import com.network.scanner.core.domain.entities.NetScanObservableUnbind

class MainViewModel(private val netScan: NetScan) : ViewModel() {

    private val mainState = MainState()
    private val observableUnbind = NetScanObservableUnbind()

    val viewAction
        get() = mainState.action

    fun pingDevice() {
        observableUnbind.cancel()
        netScan.wifiScanner()
            .onResult {
                Log.e("TESTE", it.toString())
            }
            .onFailure {
                Log.e("ERRO", it.toString())
            }.also(observableUnbind::add)
    }

    override fun onCleared() {
        super.onCleared()
        observableUnbind.cancel()
    }
}