package com.example.coroutineflowadvance.coroutine.coroutine_builder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.util.Calendar

fun main() {
    runBlocking(context = Dispatchers.IO) {
        println("inside run blocking thread name is ${Thread.currentThread().name}")
        execute1()
        execute2()
        execute3()
    }
}

private suspend fun execute1() {
    println("execute1 before delay")
    println(Calendar.getInstance().time)
    delay(1000)
    println(Calendar.getInstance().time)
    println("execute1 after delay")
    println("------------------------")
}

private suspend fun execute2() {
    println("execute2 before delay")
    delay(3000)
    println("execute2 after delay")
    println("------------------------")
}

private suspend fun execute3() {
    println("execute3 before delay")
    delay(2000)
    println("execute3 after delay")
    println("------------------------")
}
