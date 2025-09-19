package com.palmoutsourcing.task.utils

import androidx.lifecycle.Observer

class TestObserver<T>() : Observer<T> {
    var onChangeCalled: Boolean = false
    override fun onChanged(value: T) {
        onChangeCalled = true
    }
}