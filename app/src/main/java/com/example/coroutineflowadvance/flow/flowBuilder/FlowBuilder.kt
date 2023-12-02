package com.example.coroutineflowadvance.flow.flowBuilder

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.coroutineflowadvance.ui.logWithTimestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    // use flow{} this builder is much more flexible
    // it dosn't use emit function internally and you can use emit() function and emit each value
    // you want
    // you have a suspendable block and you can call coroutine function inside this block
    flow {
        emit("first value")
        emitAll(flowOf("hi", "bye"))
        delay(1000)
        emit("second value ")
        emitAll(listOf<Int>(1, 2, 3, 7).asFlow())
        emit(15.5)
    }.collect {
        println("flow{}: $it :time:${logWithTimestamp()}")
    }
}


