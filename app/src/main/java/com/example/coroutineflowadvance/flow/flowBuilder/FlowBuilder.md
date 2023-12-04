#### Flow Builder or ColdFlow builder

The flow builder function creates a new Flow. this function are a normal function not a suspend
function but we can Creates a _cold_ flow from the given suspendable [block].

The flow being _cold_ means that the [block] is called every time a terminal operator is applied to
the resulting flow.

We have 3 type of builder

##### 1 - flow {} = builder function to construct arbitrary flows from sequential calles to the emit function

##### 2 - flowOf()  = functions to create a flow from a fixed set of values. flowOf(1,2,3)

##### 3 - asFlow() = extension functions on various types to convert them into flows. listOf(1,2,3).asFlow()

4- channelFlow { ... } builder function to construct arbitrary flows from potentially concurrent
calls to the [send][kotlinx.coroutines.channels.SendChannel.send] function.

**FlowOf()** and **.asFlow()** use emit function internally and don't need to use **emit()** for
emit data but when use **flow{}** builder you need to **emit()** function to emit data.

The **flow{}** builder take a suspandable block it means you can call suspend function or regular
function into flow{} block.

`Flow{} is much flexible.`

##### flow{} builder :

as you konw we must use emit function in flow builder for emit next value
now we have diffrent type of emit function and you have konw use which one
in you use case

1- emit()    **to emit value**

2- emitAll()  to emit all the values from`Channel`or`Flow`(`emitAll(flow)`
or we can say emits an
entire flow instead of doing it one by one. `emitAll(s)` is the same
as `flow.collect { emit(it) }`

For instance, consider this example:

```kt
fruitsList = listOf("apple", "banana", "Mango", "Orange")
flow {
emit(fruitsList)
emitAll(fruitsList)
}.onEach {}.collect()
```

Output :

```kt
kotlinx.coroutines.flow.FlowKt__BuildersKt$asFlow$$inlined$unsafeFlow$3@383c57de \ 12:36:04
apple \ 12:36:04
banana \ 12:36:04
Mango \ 12:36:04
Orange \ 12:36:04
```

as you can see the first emit() can not emit a flow but the second one is
designed for emit flow.
