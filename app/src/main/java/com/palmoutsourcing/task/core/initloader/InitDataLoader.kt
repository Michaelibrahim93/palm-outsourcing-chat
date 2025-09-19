package com.palmoutsourcing.task.core.initloader

interface InitDataLoader {
    fun loadInitData(block: () -> Unit)
}

class InitDataLoaderImpl : InitDataLoader {
    var isDataLoaded: Boolean = false
    override fun loadInitData(block: () -> Unit) {
        if (!isDataLoaded) {
            block()
            isDataLoaded = true
        }
    }
}