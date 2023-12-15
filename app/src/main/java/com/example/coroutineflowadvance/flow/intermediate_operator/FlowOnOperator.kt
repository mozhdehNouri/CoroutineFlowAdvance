package com.example.coroutineflowadvance.flow.intermediate_operator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

//use flow on for change context of flow operator
suspend fun main() {

    val flowOne = flowOf("a", "b", "c", "d").flowOn(Dispatchers.Default)
    val flowTwo = flow<Int> {
        emit(1)
        emit(2)
    }.flowOn(Dispatchers.IO)
    flowTwo.collect {
        println(log(it.toString()))
    }
    flowOne.collect {
        // collect happening in main thread
        println(log(it))
    }

}

