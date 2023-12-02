package com.example.coroutineflowadvance.flow.flowBuilder

import kotlinx.coroutines.flow.flowOf

suspend fun main() {
    // use flowOf functions to create a flow from a fixed set of values
    // internally use emit function
    // you can not access emit function
    // it takes vararge as input or a single value
    // in real scenario we must call collect in suspend function or a coroutine Scope
    val flowOne = flowOf<String>("a", "b", "c", "d", "")
    flowOne.collect {
        println("flowOf multiple value:  $it")
    }

    // single value
    val flowTwo = flowOf<String>("kotlin flow")
    flowTwo.collect {
        println("flowOf single Value $it")
    }
}
