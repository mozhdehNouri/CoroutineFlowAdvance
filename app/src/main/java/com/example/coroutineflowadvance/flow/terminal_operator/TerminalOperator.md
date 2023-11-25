#### Terminal operator

for call a flow and get data we need to use terminal operator

for example

```kt
val flowOne = flow {
    emit(1)
    emit(2)
    emit(3)
}
```

above example when we run application nothing happend, we need to call a terminal operator on flow
like collect to start emiting value.
Flow is a coldStream it means start to send data when you call it.

as you konw for call a flow we need a terminal operator called collect

0- collect function emit all value with out any condition

now we have some terminal operators they internally called collect like:

1- first()  return first element if the element was null we get an execption to pervent the
execption we need call firstOrNull()  also have first{} that return the first element which match
with condition

2 - last return last parameter if the last element was null we get exception so for prevent we need
to use lastOrNull

3 - toList() that transform a flow to list and internally use collection function

4 - toSet() that transform a flow to set and internally use collection function
