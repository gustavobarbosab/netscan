package com.network.scanner.core.domain.entities.observable

sealed class SubscribeScheduler {
    /**
     * With this scheduler, the listener will notify in the Main Thread.
     */
    object Main : SubscribeScheduler()

    /**
     * With this scheduler, the listener will notify in the same thread that
     * the job was done.
     */
    object Default : SubscribeScheduler()
}