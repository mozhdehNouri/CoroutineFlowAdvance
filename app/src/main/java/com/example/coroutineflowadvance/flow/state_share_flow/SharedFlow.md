#### SharedFlow

What is sharedFlow: shares emitted values among all its collectors in a
broadcast fashion.

A shared flow is called _hot_ because its active instance exists
independently of the presence of collectors. This is opposed to a regular
Flow, such as defined by the `flow { ... }`, which is _cold_ and is
started separately for each collector.

**Shared flow never completes**. A call to [Flow.collect] on a shared flow
never completes normally, and neither does a coroutine started by
the [Flow.launchIn] function. An active collector of a shared flow is
called a _subscriber_. or we can say  "Shared flow never completes" means
that when you collect or subscribe to a shared flow using `Flow.collect`
or `Flow.launchIn`, the execution of the collector or coroutine never
naturally completes. It doesn't reach the end in a typical way, and it
will keep running as long as the shared flow produces values.

In other words, the shared flow remains active indefinitely, and its
collector (subscriber) stays in a listening state, continuously receiving
emitted values. This behavior is different from regular flows where the
collection might complete when the flow finishes emitting elements.

A subscriber of a shared flow can be cancelled. This usually happens when
the scope in which the coroutine is running is cancelled. A subscriber to
a shared flow is always [cancellable][Flow.cancellable], and checks for
cancellation before each emission.

A subscriber of a shared flow can be cancelled. This usually happens when
the scope in which the coroutine is running is cancelled. A subscriber to
a shared flow is always [cancellable][Flow.cancellable], and checks for
cancellation before each emission. Note that most terminal operators
like [Flow.toList] would also not complete, when applied to a shared flow,
but flow-truncating operators like [Flow.take] and [Flow.takeWhile] can be
used on a shared flow to turn it into a completing one. or we can say

1. **Note that most terminal operators like [Flow.toList] would also not
   complete when applied to a shared flow:** Terminal operators in
   Kotlin's Flow API are operations that consume the flow and produce a
   result. The statement is saying that certain terminal operators, such
   as `toList`, which collects all the emitted elements and returns them
   as a list, would not complete as expected when applied to a shared
   flow. This is because the shared flow, by its nature, does not complete
   on its own.

2. **Flow-truncating operators like [Flow.take] and [Flow.takeWhile] can
   be used on a shared flow to turn it into a completing one:** Unlike
   most terminal operators, flow-truncating operators like `take`
   and `takeWhile` can be applied to a shared flow to limit the number of
   elements emitted. By doing this, you can effectively turn a shared flow
   into a completing one because the flow will complete after a certain
   number of elements or when a specific condition is met.

A [mutable shared flow][MutableSharedFlow] is created using
the [MutableSharedFlow(...)] constructor function. Its state can be
updated by [emitting][MutableSharedFlow.emit] values to it and performing
other operations. [SharedFlow] is useful for broadcasting events that
happen inside an application to subscribers that can come and go.

for instance :

```kt
 class EventBus {
      private val _events = MutableSharedFlow<Event>() 
      val events = _events.asSharedFlow()

      suspend fun produceEvent(event: Event) {
          _events.emit(event)
      }
  }
```

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

###### Replay cache and buffer

A shared flow keeps a specific number of the most recent values in its
_replay cache_. Every new subscriber first gets the values from the replay
cache and then gets new emitted values. The maximum size of the replay
cache is specified when the shared flow is created by the `replay`
parameter. A snapshot of the current replay cache is available via
the [replayCache] property and it can be reset with
the [MutableSharedFlow.resetReplayCache] function.

// study about buffer is sharedFlow.

//study about SharedFlow vs BroadcastChannel
