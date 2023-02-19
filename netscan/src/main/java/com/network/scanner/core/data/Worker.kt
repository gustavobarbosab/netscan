package com.network.scanner.core.data

interface Worker<RETURN> {
    fun execute(): RETURN
}
