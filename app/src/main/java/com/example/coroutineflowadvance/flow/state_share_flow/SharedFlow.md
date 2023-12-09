#### SharedFlow

To convert a coldFlow into a hotFlow(SharedFlow) we can use ShareIn()
extension functions.

```kt
  val flowOne = flow<Int> {
        emit(1)
        emit(2)
        emit(3)
        emit(5)
    }.map {
        delay(1000)
        it*2
    }.onEach {
        println("item is $it time ${logWithTimestamp()}")
    }
  flowOne.sharedIn()
```

coroutineScope = sharedIn internally launch new coroutine to collect from
origin flow

// about all paramter we use in sharedIn any why we need them.

// check second item 


