package com.network.scanner.core.scanner.domain.entities

sealed class NetScanScheduler {
    /**
     * With this scheduler, the listener will notify in the Main Thread.
     */
    object Main : NetScanScheduler()

    /**
     * With this scheduler, the listener will notify in the same thread that
     * the work was done.
     */
    object Default : NetScanScheduler()
}