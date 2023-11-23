package com.example.coroutineflowadvance.coroutine.coroutine_builder

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        // each launch perform independently
        // if you have 2 suspend function in a launch block suspend2 start to perform when
        // suspend1 finished
        launch {
            execute1()
        }
        launch {
            execute2()
        }
    }

}

private suspend fun execute1() {
    println("execute1 before delay")
    delay(1000)
    println("execute1 after delay")
}

private suspend fun execute2() {
    println("execute2 before delay")
    delay(3000)
    // execute2 wait until execute3 finished
    execute3()
    println("execute2 after delay")
}

private suspend fun execute3() {
    println("execute3 before delay")
    delay(2000)
    println("execute3 after delay")
}