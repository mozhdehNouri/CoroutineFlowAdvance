package com.example.coroutineflowadvance.flow.terminal_operator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private fun main() {
    val flowOne = flow<String> {
        emit("first emit of flow One")
        delay(1000)
        emit("second emit of flow One")
    }
    val flowTwo = flow<String> {
        emit("first emit of flow Two")
        delay(100)
        emit("second emit of flow Two")
    }
    runBlocking {
        launch {
            // collect is a suspend function and suspend until flow one finished
            // we can say because we have in a same scope
            flowOne.collect {
                println(it)
            }
            flowTwo.collect {
                println(it)
            }
        }

    }

    // with launchIn runs parallel
    // because launchIn is not suspend function
    // and we define new scope
    // it is like call flow in 2 launch sperate function
    flowOne.onEach {
        println(it)
    }.launchIn(CoroutineScope(EmptyCoroutineContext))

    flowTwo.onEach {
        println(it)
    }.launchIn(CoroutineScope(EmptyCoroutineContext))
}


