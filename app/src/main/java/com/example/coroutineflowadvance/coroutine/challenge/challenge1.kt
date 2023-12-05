package com.example.coroutineflowadvance.coroutine.challenge

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        launch {
            println("child1")
            delay(200)
            println("child1 working")
            throw RuntimeException()
        }
        launch {
            println("child2")
            delay(300)
            println("child2 finish")
        }
        launch {
            delay(100)
            throw CancellationException()
        }
    }
}
