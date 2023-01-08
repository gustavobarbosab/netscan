package com.network.scanner.core.scanner.domain.entities

class NetScanObservableUnbind {

    private val observables = mutableListOf<NetScanObservable<*>>()

    fun add(netScanObservable: NetScanObservable<*>) {
        observables.add(netScanObservable)
    }

    fun cancel() {
        observables.forEach { it.cancel() }
        observables.clear()
    }
}