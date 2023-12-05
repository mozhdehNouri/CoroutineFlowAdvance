package com.example.coroutineflowadvance.flow.terminal_operator

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull

private suspend fun main() {
    val flowOne = flow<String> {
        emit("apple")
        emit("orange")
        emit("banana")
    }
    // first function is a suspend function and internally use collect
    // if the item be null we get and exception
    // for prevent this simply we can use firstOrNull
    println(flowOne.firstOrNull())
    println(flowOne.first())
    println(flowOne.first { it == "apple" })
    println(flowOne.firstOrNull { it == "apple" })

    // like first but return the last item
    println(flowOne.last())
    println(flowOne.lastOrNull())


}

