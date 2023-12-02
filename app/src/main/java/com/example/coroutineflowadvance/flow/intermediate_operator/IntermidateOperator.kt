package com.example.coroutineflowadvance.flow.intermediate_operator

import kotlinx.coroutines.flow.flowOf

fun main() {
    val flowOne = flowOf(1, 2, 3, 4, 5)
    // this code is not working because map can not  apply to down stream
//    flowOne.map {
//        it * 2
//    }
//    runBlocking {
//        flowOne.collect{
//            println(it)
//        }

//    }


}