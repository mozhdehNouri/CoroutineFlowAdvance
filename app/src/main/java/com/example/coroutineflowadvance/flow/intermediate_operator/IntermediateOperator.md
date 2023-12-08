Intermidate operator:

Intermidate operator unlike terminal operator perform instructions and
produces a new flow.

*Intermediate operators* on the flow such as map, filter, take, zip, etc
are functions that are applied to the *upstream* flow or flows and return a
*downstream*flow where further.

Intermediate operations do not execute any code in the flow and are not suspending functions
themselves.

we can say with terminal operator we can apply our emision on flow and
return new and transformed flow.

look at this example :

```kt
val flowOne = flowOf(1, 2, 3)
flowOne.map {
 it * 2       // new flow is 2,4,6
}.filter {
 it < 5     // new flow is 2, 4
}
```

in above example map produce new flow and delivers to filter operator. filter condition no longer
apply of 1,2,3 value it is apply on map output.

Instead of terminal operator except launchIn and asLiveData() are suspend
function the intermidate operator not suspend function.

the intermidate operator aren't suspend function but most of them get a lambda which perform
suspending operation.

for example :

```kt
public inline fun <T, R> Flow<T>.map(crossinline transform: suspend (value: T) -> R)
```

```kt
flow.map{
  // delay is suspend function
  delay(200)    

}
```

map :

mapNotNull :

filter null result

filter{}: we can pass predicate

filterNot{} :

FilterNotNull():

filterIsInstance<T> = filter by a type

take : we can pass a count for example 3 and flow only return 3 first flow .

takeWhile {} : we can pass a perdicate. something is very important here

for instance:

```kt
val flowOne = flowOf(1,2,3,4,5)
flowOne.takeWhile{ it>3}
```

in above example we got nothing.
because flow visit first one and when see the first number is not mach with condition do not
continue and get cancell.

diffrent between takeWhile and filter:

as you can see in last example takewhile get cancell when the first item dosen't match with
perdicate. but filter visite all item

drop :  we can pass a count. it opposit of take for more information checking code.

dropWhile {} : ///it can apply all items instead of take but you can check it check all ot not
transorm like map but has big diffrent :

map return single value but transform retrun multiple value
for instance:

```kt
val flowOne = flowOf(1,2,3,4)
flowOne.map{
    it*2     // the output is 1,4,6,8
}

flowOne.transform {
    emit(it)  // 1,2,3,4
  emit(it*2) //  1,4,6,8 
}
```

withIndext :  we can get value with index

distinctUntilChanged()   :  for example of we have number like this
1,1,2,5,8,1 the output is
1,2,5,8,1 it can filter the flow next each other // but why ? why it can't
filter all 1 for
example ? we need to use set ?

we can call suspend function in intermediate block of code.
