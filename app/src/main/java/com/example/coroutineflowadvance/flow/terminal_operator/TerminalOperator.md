#### Terminal operator

note: all operators we use in this article are terminal operators. but I
divide it into different categories for better understanding.

Terminal operators are functions to consume values emitted by the flow.

or we can say to start giving value from flow we should use a terminal
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
flowOne.collect {
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
operator that performs sequentially.

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

collect the latest value. if a value is processing and new value is coming
the previous value get cancel and new value get emit.

##### compare all collect function:

##### collect() : It is a shorthand for collect {}

- **Description:** The `collect` function is a terminal operator that
  starts the collection of values from the Flow. It is a suspending
  function, meaning it can only be called from within a coroutine.
- **Behavior:**
    - The `collect` function collects all emitted values from the Flow
      sequentially, one by one.
    - It suspends the coroutine until a new value is emitted.

This operator is usually used with **onEach**, **onCompletion **and *
*catch** operators to process all emitted values and handle an exception
that might occur in the upstream flow or during processing, for example:

```kt
flow.onEach { value -> process(value) }
.catch { e -> handleException(e) }
.collect() // trigger collection of the flow
```

For instance :

```kt
 val fruitsList = listOf<String>("apple", "banana", "Mango", "Orange").asFlow()
 
  fruitsList.onEach {
        delay(2000)
        println("value is $it and time is ${logWithTimestamp()}")
    }.collect()

```

Output:

```kt
value is apple and time is 13:49:21
value is banana and time is 13:49:23
value is Mango and time is 13:49:25
value is Orange and time is 13:49:27
```

As you can see we wait two second to print each item.

##### collect {} :

- **Description:** The `collect{}` block is an alternative syntax for
  the `collect` function. It allows you to define a block of code to
  handle each emitted value.
- **Behavior:**
    - Similar to `collect`, it collects values one by one.
    - The block of code inside `collect{}` is executed for each emitted
      value.
    - It suspends the coroutine until a new value is emitted.

Or we can say collect{} is short form of flow.onEach{}.catch{}.collect()

For instance

```kt
val fruitsList = listOf<String>("apple", "banana", "Mango", "Orange").asFlow()
 
  fruitsList.collect{
        delay(2000)
        println("value is $it and time is ${logWithTimestamp()}")
    }

```

output :

```kt
value is apple and time is 13:56:11
value is banana and time is 13:56:13
value is Mango and time is 13:56:15
value is Orange and time is 13:56:17
```

#### collectLatest{}:

- **Description:** The `collectLatest` function is also a terminal
  operator, but it has a different behavior compared to `collect`.
- **Behavior:**
    - It cancels the previous collection job when a new value is emitted
      before the previous one is processed.
    - This means that if a new value is emitted while the coroutine is
      still processing a previous value, the processing of the previous
      value is canceled, and the coroutine switches to handle the new
      value.

for instance :

```kt
    val fruitsList =
        listOf<String>("apple", "banana", "Mango", "Orange").asFlow()  
    
    fruitsList.collectLatest{
      println("collecting value is $it in time ${logWithTimestamp()}")
 
        println("$it is collected in time ${logWithTimestamp()}")
    } 
    
    
    
```

output :

```kt
collecting value is apple in time 14:02:56
apple is collected in time 14:02:56
collecting value is banana in time 14:02:56
banana is collected in time 14:02:56
collecting value is Mango in time 14:02:56
Mango is collected in time 14:02:56
collecting value is Orange in time 14:02:56
Orange is collected in time 14:02:56
```

But if I add delay we can see some instersting :

example1 :

```kt
 fruitsList.collectLatest {
        delay(10)
        println("collecting value is $it in time ${logWithTimestamp()}")
//        delay(10)
        println("$it is collected in time ${logWithTimestamp()}")
    }
```

output :

```kt
collecting value is Orange in time 14:04:41
Orange is collected in time 14:04:41
```

Example2 :

```kt
    fruitsList.collectLatest {
        println("collecting value is $it in time ${logWithTimestamp()}")
        delay(1)
        println("$it is collected in time ${logWithTimestamp()}")
    }
```

output:

```kt
collecting value is apple in time 14:06:47
apple is collected in time 14:06:47
collecting value is banana in time 14:06:47
collecting value is Mango in time 14:06:47
collecting value is Orange in time 14:06:47
Orange is collected in time 14:06:47
```

Example 3 :

```kt
    fruitsList.collectLatest {
        println("collecting value is $it in time ${logWithTimestamp()}")
        delay(1000)
        println("$it is collected in time ${logWithTimestamp()}")
    }
```

output :

```kt
collecting value is apple in time 14:07:44
collecting value is banana in time 14:07:44
collecting value is Mango in time 14:07:44
collecting value is Orange in time 14:07:44
Orange is collected in time 14:07:45
```

###### 2- Collection:

The second category is collection. we have some flow terminal operator
that use the collect function internally which means you no longer need to
call collection function and convert flow to list or set ...

a. toSet()         // convert a flow into a set and use collection
function internally

b. toList()       // convert a flow into a list

// what happend to toList() or toSet() can observe the changes? so why we
use collect function?

##### 3- Reduce

All this operator use collect operator internally

a. **first()**  return the first element if the element was null we get an
exception to prevent the exception we need call **firstOrNull()**  also we
have **first{}** operator which return the first element that match with
condition.

or we can say first() that returns the first element emitted by the flow
and then cancels flow's collection. Throws [NoSuchElementException] if the
flow was empty.

**first{}** :The terminal operator that returns the first element emitted
by the flow matching the given [predicate] and then cancels flow's
collection. Throws [NoSuchElementException] if the flow has not contained
elements matching the [predicate].

**firstOrNull()** :  The terminal operator that returns the first element
emitted by the flow and then cancels flow's collection. Returns `null` if
the flow was empty.

**firstOrNull{}** :  The terminal operator that returns the first element
emitted by the flow matching the given [predicate] and then cancels flow's
collection. Returns `null` if the flow did not contain an element matching
the [predicate].

2 - **last()** return last parameter if the last element was null we get
exception so for prevent we need to use **lastOrNull()**

5- launchIn() this function not suspend function like toList or toSet

```kt
public fun <T> Flow<T>.launchIn(scope: CoroutineScope): Job = scope.launch {
    collect() // tail-call
}
```

actually launchIn is a shortcut, instead Of Using

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
