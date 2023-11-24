#### Flow Builder

The flow builder function creates a new Flow from a suspendable lambda block. Inside the block, you
can send values using the emit function.

we have 3 type of builder

##### 1 - flow {} = builder function to construct arbitrary flows from sequential calles to the emit function

##### 2 - flowOf()  = create a flow from a fixed set of value     flowOf(1,2,3)

##### 3 - asFlow() = extension function on various type to convert them into flows   listOf(1,2,3).asFlow()

flowOf and asFlow use emit function internally and don't need to use emit() for emit data but when
use flow{} builder you need to emit() function to emit data

the flow{} builder take a suspandable block it means you can call suspend function or regular
function into flow{} block

flow{} is much flexible.

##### flow{} builder :

as you konw we must use emit function in flow builder for emit next value
now we have diffrent type of emit function and you have konw use which one in you use case

1- emit()    to emit value

2- emitAll()to emit all the values from`Channel`or`Flow`(`emitAll(flow)`  or we can say emits an
entire flow instead of doing it one by one.`emitAll(s)`is the same as`s.collect { emit(it) }`
