package com.network.scanner.core.domain.entities.observable

class SubscribeResultDisposable {

    private val observables = mutableListOf<SubscribeResult<*>>()

    fun add(netScanObservable: SubscribeResult<*>) {
        observables.add(netScanObservable)
    }

    fun dispose() {
        observables.forEach { it.dispose() }
        observables.clear()
    }
}