package com.example.coroutineflowadvance.flow.terminal_operator

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.coroutineflowadvance.ui.logWithTimestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.toSet

@RequiresApi(Build.VERSION_CODES.O)
private suspend fun main() {
    val flowOne = flow<String> {
        emit("apple")
        delay(2000)
        emit("orange")
        delay(2000)
        emit("banana")
        emit("banana")
    }

    // toList terminal operator
    // return a list and internally use collect
    // when all item emit after that can show the list
    println("toList terminal operator ${flowOne.toList()} \\ ${logWithTimestamp()}")


    //toSet operator use internally collect function
    // return a set
    println("toSet terminal operator ${flowOne.toSet()} \\ ${logWithTimestamp()}")

}