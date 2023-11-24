package com.example.coroutineflowadvance.flow.flowBuilder

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

suspend fun main() {

    // use flowOf for create a flow
    val flowOne = flowOf<String>("a", "b", "c", "d", "")

//       flowOne.collect {
//        println("flowOf:  $it")
//    }

    // use flowOf for create a flow
    listOf("1", "2", "3", "4", "5").asFlow().collect {
//        println("asFlow:  $it")
    }

    // use flow{} builder do not forget use emit function to emit value
    flow {
        kotlinx.coroutines.delay(1000)
        emit("first value")   //
        emitAll(flowOne)
        emit("second value ")
        emitAll(listOf<Int>(1, 2, 3, 7).asFlow())
    }.collect {
        println(it)
    }
}