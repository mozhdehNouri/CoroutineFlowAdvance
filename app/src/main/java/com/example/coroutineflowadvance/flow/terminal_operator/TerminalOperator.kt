package com.example.coroutineflowadvance.flow.terminal_operator

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

private fun main() {
    val flowOne = flow<String> {
        emit("apple")
        emit("orange")
        emit("banana")
    }
    runBlocking {
        // first function is a suspend function and internally use collect
        // if the item be null we get and exception
        // for prevent this simply we can use firstOrNull
        println(flowOne.firstOrNull())
        println(flowOne.first())
        println(flowOne.first { it == "apple" })
        println(flowOne.firstOrNull { it == "apple" })
    }
}

fun main(args: Array<String>) {
    val flowOne = flow<String> {
        emit("apple")
        emit("orange")
        emit("banana")
    }

    runBlocking {
        // like first but return the last item
        println(flowOne.last())
        println(flowOne.lastOrNull())
    }

    // toList operator
    runBlocking {
        // return a list and internally use collect
        val list = flowOne.toSet()
    }

    //toSet operator use internally collect function
    runBlocking {
        flowOne.toSet()


        flowOne.collect {

        }
        flowOne.collect()

        flowOne.collectLatest {

        }
    }

    // use launchIn to change coroutineScope of your flow
    // for example you can use viewModelScope is you are in viewModel
    // internally launchIn function use collection function it means you no longer need to call collect function
    val scope = CoroutineScope(EmptyCoroutineContext)
    flowOne.launchIn(scope)
}