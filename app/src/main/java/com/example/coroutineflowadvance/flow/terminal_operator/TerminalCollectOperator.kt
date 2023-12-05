package com.example.coroutineflowadvance.flow.terminal_operator

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.coroutineflowadvance.ui.logWithTimestamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.EmptyCoroutineContext

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun main() {
    val fruitsList =
        listOf<String>("apple", "banana", "Mango", "Orange").asFlow()
    val flowOne = flow {
        emit("start collecting")
        kotlinx.coroutines.delay(2000)
        // emitAll use collect internally but if you don't call collect on flowOne it doesn't work.
        emitAll(fruitsList)
        kotlinx.coroutines.delay(2000)
        emit("end of  collecting")
    }
    flowOne.onEach {
        println("collecting with collect()")
        println("$it \\ ${logWithTimestamp()}")
    }.collect()

    fruitsList.onEach {
        delay(2000)
        println("$it \\ ${logWithTimestamp()}")
        // we can not use emit inside onEach block
        // emit("start collecting with collect() operator")
    }.collect()

    fruitsList.collect {
        println("collecting value is $it in time ${logWithTimestamp()} collect")
        delay(1000)
        println("$it is collected in time ${logWithTimestamp()} collect")
    }

    fruitsList.collectLatest {
        delay(10)
        println("collecting value is $it in time ${logWithTimestamp()}")
        println("$it is collected in time ${logWithTimestamp()}")
    }
    fruitsList.collectLatest {
        println("collecting value is $it in time ${logWithTimestamp()}")
        delay(1)
        println("$it is collected in time ${logWithTimestamp()}")
    }
    fruitsList.collectLatest {
        println("collecting value is $it in time ${logWithTimestamp()}")
        delay(10)
        println("$it is collected in time ${logWithTimestamp()}")
    }


//    // use launchIn to change coroutineScope of your flow
//    // for example you can use viewModelScope is you are in viewModel
//    // internally launchIn function use collection function it means you no longer need to call collect function
    val scope = CoroutineScope(EmptyCoroutineContext)
    flowOne.launchIn(scope)

}