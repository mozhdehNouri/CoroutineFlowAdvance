#### Terminal operator

Terminal operators are functions to consume values emitted by the flow.

or we can say for start giving value form flow we should use a terminal
operator and after that flow start emit value for us.

for example :

```kt
val flowOne = flow {
    emit(1)
    emit(2)
    emit(3)
}
```

above example when we run application nothing happens, we need to call a
a terminal operator on flow, like **collect**. Because Flow is a
Coldstream when calling terminal function flow starts to emit data.

All terminal operators are suspend it means you must call it in coroutine
scope except **launchIn()** and **asLiveData()**.

###### 1- Collect  :

Collecting plays the main role of starting to emit data from the flow.
all terminal functions use collect function internally like toSet,toList

The return type of collect operator is unit.

We have different types of collect functions:

##### a. collect {} :

This form allows you to provide a block of code inside the `collect`
function.

The block of code is executed for each emitted value, allowing you to
perform specific actions or operations for each value. It's a more concise
way of expressing what to do with each emitted value. **collect{}** is
often preferred when you want to perform specific actions or operations
for each emitted value because it results in cleaner and more readable
code.

for instance :

```kt
flowOne.collect{
     // Do something with the value
}
```

#### b. collect():

Terminal flow operator that collects the given flow but ignores all
emitted values.

It's used when you just want to consume the values emitted by the flow
without doing anything special for each value.
This operator is usually used with [onEach], [onCompletion] and [catch]
operators to process all emitted values and handle an exception that might
occur in the upstream flow or during processing.

collect function is suspend function and if you call several collect
operator that perform sequential.

for instance :

```kt
private suspend fun main() {
    val fruitsList =
        listOf<String>("apple", "banana", "Mango", "Orange").asFlow()
    val flowOne = flow {
        emit("start collecting")
        kotlinx.coroutines.delay(2000)
        emitAll(fruitsList)
        kotlinx.coroutines.delay(2000)
        emit("end of collecting")
    }.onEach {
        println("$it \\ ${logWithTimestamp()}")
    }
    flowOne.collect()

    fruitsList.onEach {
        delay(2000)
        println("$it \\ ${logWithTimestamp()}")
    }.collect()
}
```

output is :

```kt
start collecting \ 12:51:14
apple \ 12:51:16
banana \ 12:51:16
Mango \ 12:51:16
Orange \ 12:51:16
end of collecting \ 12:51:18
apple \ 12:51:20
banana \ 12:51:22
Mango \ 12:51:24
Orange \ 12:51:26
```

First flowOne started to collect and after that when flowOne got completed
fruitsList started to collect.

#### c. collectLatest{} :

now we have some terminal operators they internally called collect like:

1- first()  return first element if the element was null we get an
execption to pervent the
execption we need call firstOrNull()  also have first{} that return the
first element which match
with condition

2 - last return last parameter if the last element was null we get
exception so for prevent we need
to use lastOrNull

3 - toList() that transform a flow to list and internally use collection
function

4 - toSet() that transform a flow to set and internally use collection
function

5- launchIn() this function not suspend function like toList or toSet

```kt
public fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job = scope.launch {
    collect() // tail-call
}
```

actually launchIn is a shortcut , instead Of using

```kt
scope.launch{
flowOne.collect{
}    
}
```

you can use

```kt
flowOne.launchIn(scope)
```

what is different between launchIn() and collect()?

launchIn is a regular function not suspend function and it is not suspend
the flow untli is come

now you can take look at LaunchAndCollect.kt to have better understanding
of different

all terminal operators are suspend function except one and this **is launchIn()**

```kt
fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job
```

as you can see launchIn can not provide a lambda to perform your operation and you can pass a scope
to it .

launcIn is not suspend function it means it can suspend the operation
but collect function is a suspend function and can suspend the operation without blocking. but it is
true?? i have research about it.
