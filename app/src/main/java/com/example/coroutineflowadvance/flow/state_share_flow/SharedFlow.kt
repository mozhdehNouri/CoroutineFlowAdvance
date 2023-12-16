//package com.example.coroutineflowadvance.flow.state_share_flow
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import com.example.coroutineflowadvance.ui.logWithTimestamp
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.flow.MutableSharedFlow
//import kotlinx.coroutines.flow.flow
//import kotlinx.coroutines.flow.map
//import kotlinx.coroutines.flow.onCompletion
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.shareIn
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun main() {
//
//    val shared = MutableSharedFlow<>()
//    val flowOne = flow<Int> {
//        emit(1)
//        emit(2)
//        emit(3)
//        emit(5)
//    }.map {
//        delay(1000)
//        it * 2
//    }.onCompletion {
//        // check it later
//    }
//        .onEach {
//            println("item is $it time ${logWithTimestamp()}")
//        }.shareIn()
//
//
////    val scope = CoroutineScope(Dispatchers.Default)
////    val sharedFlow = MutableSharedFlow<Int>()
////
////    scope.launch {
////        repeat(5) {
////            println("sharedFlow emit $it time ${logWithTimestamp()}")
////            sharedFlow.emit(it)
////            delay(200)
////        }
////    }
////
////    scope.launch {
////        sharedFlow.collect {
////            println("collect number#1   $it")
////        }
////    }
////    scope.launch {
////        sharedFlow.collect {
////            println("collect number#2  $it")
////        }
////    }
////    Thread.sleep(1500)
//
//
//}