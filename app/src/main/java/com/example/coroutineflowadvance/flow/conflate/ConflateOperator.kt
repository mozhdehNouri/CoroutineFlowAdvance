package com.example.coroutineflowadvance.flow.conflate

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() {
    flow<Int> {
        emit(1)
        emit(2)
        emit(3)
        delay(2000)
    }.conflate().collect {
        println(it)
    }


}