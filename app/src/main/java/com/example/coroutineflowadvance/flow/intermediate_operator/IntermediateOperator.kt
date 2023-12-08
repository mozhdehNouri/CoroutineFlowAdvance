package com.example.coroutineflowadvance.flow.intermediate_operator

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

suspend fun main() {
    // Intermediate operator return new flow on downstream
    // we can have multiple Intermediate operator

    val flowOne = flowOf(1, 2, 3, 4, 5)
    flowOne.onStart {
        emit(0)
        println("flow is start1")
    }.onCompletion {
        println("flow is completed1")
    }.transform {
//        emit(it)
        emit(it * 2)
    }.map {
        it * 6
    }.onCompletion {
        emit(0)
        println("flow is completed2")
    }.onStart {
        println("flow is start 2")
    }.collect {
        println(it)
    }
}