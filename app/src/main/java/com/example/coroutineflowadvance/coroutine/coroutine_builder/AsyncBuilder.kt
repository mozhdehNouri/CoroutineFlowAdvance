package com.example.coroutineflowadvance.coroutine.coroutine_builder

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val a = async {
            execute1()
        }
        val b = async {
            execute2()
        }
        println(a.await())
        println(b.await())

    }
}

private suspend fun execute1(): Int {
    println("execute1 before delay")
    delay(2000)
    println("execute1 after delay")
    return 4
}

private suspend fun execute2(): Int {
    println("execute2 before delay")
    delay(1000)
    println("execute2 after delay")
    return 5
}

private suspend fun add(a: Int, b: Int): Int = a + b