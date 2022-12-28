package com.network.scanner.core.scanner.data

interface Worker<RETURN> {
    fun execute(): RETURN
}
