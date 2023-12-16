package com.example.coroutineflowadvance.flow.intermediate_operator

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

//use flow on for change context of flow operator
suspend fun main() {
    val flowTwo = flow<Int> {
        emit(1)
        emit(2)
        emit(4)
        emit(6)
    }.filter {
        it % 2 == 0
    }.map {
        println("value is $it")
    }.flowOn(Dispatchers.IO)
    flowTwo.collect {
        println(log(it.toString()))
    }
}

