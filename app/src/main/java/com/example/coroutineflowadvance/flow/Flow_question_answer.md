All questions related to Flow (coldFlow, hotFlow)

##### 1 - what are upStream and downStream in Kotlin flow?

Intermediate operators are applied to an upstream flow and return a
downstream flow. These operators are cold, just like flows are. A call to
such an operator is not a suspending function itself. It works quickly,
returning the definition of a new transformed flow.

Each emitted value is processed by all the intermediate operators from
upstream to downstream and is then delivered to the terminal operator.

```kt
fun main() = runBlocking<Unit> {
    simple()
        .catch { e -> println("Caught $e") } // does not catch downstream exceptions
        .collect { value ->
            check(value <= 1) { "Collected $value" }
            println(value)
        }
}  
```

The**catch**intermediate operator, honoring exception transparency,
catches only upstream exceptions (that is an exception from all the
operators above`catch`, but not below it). If the block
in`collect { ... }`(placed below`catch`) throws an exception then it
escapes

Another thing to observe here is that the flowOn operator has changed the
default sequential nature of the flow. Now collection happens in one
coroutine ("coroutine#1") and emission happens in another coroutine ("
coroutine#2") that is running in another thread concurrently with the
collecting coroutine. The flowOn operator creates another coroutine for an
upstream flow when it has to change the CoroutineDispatcher in its
context.

---

##### 2 - which operator effect on upStream and which operator effect on downstream

### Operators that Affect Upstream:

These are intermediate operators that transform data as it flows upstream
and are applied before the terminal operation.

- **`map`**: Transforms each emitted value according to the provided
  function.
- **`filter`**: Emits only values that satisfy the given predicate.
- **`take`**: Takes the specified number of values from the start of the
  flow and then cancels the upstream flow.
- **`drop`**: Drops the first n values and emits the rest.
- **`transform`**: More general than`map`or`filter`, it allows applying a
  body of code to each value and can emit as many values as needed for
  each input value.
- **`onStart`**: Invoked when the flow collection starts and before any
  values are emitted. Can be used to emit an initial value.
- **`flatMapConcat`**: Transforms each value into another Flow and
  flattens the resulting flows into a single flow sequentially.
- **`flatMapMerge`**: Similar to`flatMapConcat`but collects the resulting
  Flows concurrently.
- **`flatMapLatest`**: Similar to`flatMapMerge`but when a new Flow is
  emitted, the previous one is canceled.

### Operators that Affect Downstream:

These operators are usually applied after upstream transformations and
right before or as part of the terminal operation (like`collect`).

- **`collect`**: Terminal operation to collect the values emitted by the
  flow.
- **`toList`**or**`toSet`**: Terminal operations that collect the flow’s
  data into a list or set, respectively.
- **`onEach`**: Performs an action for each value emitted by the flow
  before passing it downstream.
- **`onCompletion`**: Invoked when the flow completes, allowing for
  cleanup or additional actions, can also emit a final value.
- **`catch`**: Catches any exception that occurs in the upstream flow and
  allows the downstream to handle it.
- **`buffer`**: Collects the values emitted by the flow into a buffer to
  be emitted downstream by another coroutine, enabling faster production
  than collection.
- **`conflate`**: When the collector is slower than the producer,
  conflating skips intermediate values, delivering only the most recent
  value to the downstream when ready.
- **`collectLatest`**: When the collector is slow, this operator cancels
  the slow collector whenever a new value is emitted.

---

##### 3 - which operator we can call emit function :

onStart {}

onCompletion {}

transform{}

---

4- diffrenet between map{}, transform{}, onEach{} operator and when use
which one.








