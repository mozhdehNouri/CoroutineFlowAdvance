package com.example.coroutineflowadvance.flow.flowBuilder

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.coroutineflowadvance.ui.logWithTimestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow

@RequiresApi(Build.VERSION_CODES.O)
suspend fun main() {
    // use .asFlow Extensions function for create a flow
    // .asFlow apply Iterable, sequence, normal function , suspendFunction
    // internally use emit function
    // in real scenario we must call collect in suspend function or a coroutine Scope

    // apply .asFlow on Iterable
    sampleList().asFlow()
        .collect {
            println("iterable: $it :time:${logWithTimestamp()}")
        }

    // apply .asFlow on sequence
    sequenceSample().asFlow()
        .collect {
            println("sequence: $it :time:${logWithTimestamp()}")
        }

    // apply .asFlow on suspend Function
    // if your return type is list you can see we have list of Flow
    ::suspendSample.asFlow()
        .collect {
            println("suspend fun : $it :time:${logWithTimestamp()}")
        }

    // apply .asFlow on regular Function
    ::regularFunctionSample.asFlow()
        .collect {
            println("regular fun : $it :time:${logWithTimestamp()}")
        }

}

private fun sampleList(): List<String> {
    return listOf("1", "2", "3", "4", "5")
}

private fun sequenceSample(): Sequence<Int> {
    val numberList = listOf(1, 2, 3, 4)
    return sequence {
        var newValue = 0
        for (i in 1..numberList.size) {
            newValue += i
            yield(newValue)
        }
    }
}

private suspend fun suspendSample(): List<Int> {
    delay(2000)
    return listOf(1, 2, 3, 4, 5)
}

private fun regularFunctionSample(): String {
    return "hello flow"
}