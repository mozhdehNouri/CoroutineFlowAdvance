#### Terminal operator

To call a flow and get data we need to use the terminal operator

for example

```kt
val flowOne = flow {
    emit(1)
    emit(2)
    emit(3)
}
```

above example when we run application nothing happend, we need to call a
terminal operator on flow
like, collect to start emitting value.
Flow is a coldStream it means start to send data when you call it.

as you konw for call a flow we need a terminal operator called collect

0- collect function emit all value with out any condition

now we have some terminal operators they internally called collect like:

1- first()  return first element if the element was null we get an execption to pervent the
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


