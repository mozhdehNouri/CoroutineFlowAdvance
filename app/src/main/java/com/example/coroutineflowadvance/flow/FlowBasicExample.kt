package com.example.coroutineflowadvance.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {
    //print single vale
    println(addNumber(listOf(1, 2, 3, 4, 5)))


    // print multiple value sync with sequence
    val numWithSeq = addNumberWithSequence(listOf(1, 2, 3, 4, 5))
    println(numWithSeq.forEach {
        println(it)
    })


// print multiple value sync with flow
    val numWithFlow = addNumberWithFlow(listOf(1, 2, 3, 4, 5))
    runBlocking {
        numWithFlow.collect {
            println(it)
        }
    }
}

fun addNumber(listNumber: List<Int>): Int {
    // this function return just one result
    var newValue = 0
    listNumber.forEach {
        newValue += it
    }
    return newValue
}

fun addNumberWithSequence(listNumber: List<Int>): Sequence<Int> = sequence {
    // return data whenever it produce so we can have multiple value at different time
    // return data synchronously
    var newValue = 0
    for (i in 1..listNumber.size) {
        newValue += i
        yield(newValue)
    }
}

fun addNumberWithFlow(listNumber: List<Int>): Flow<Int> = flow {
    // return data whenever it produce so we can have multiple value at different time
    // return data asynchronously
    var newValue = 0
    for (i in 1..listNumber.size) {
        newValue += i
        emit(newValue)
    }
}