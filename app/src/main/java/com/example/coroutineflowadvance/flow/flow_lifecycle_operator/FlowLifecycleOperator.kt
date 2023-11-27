package com.example.coroutineflowadvance.flow.flow_lifecycle_operator

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun main() {
    val flowOne = flow<Int> {
        delay(2000)
        emit(1)
        delay(1000)
        emit(3)
        emit(2)
        delay(2500)
    }

    flowOne.onStart {
        // when your flow start to collect you can
        // show a progress bar or every thing you want to show when your flow start to emit
        println("The flow starts to collect")
    }.onCompletion { exception ->
        // when your all item emit this block start to perform
        // a flow can complete or get an exception
        // if your exception is null it means your flow completed successful
        println("flow has completed")
    }
}