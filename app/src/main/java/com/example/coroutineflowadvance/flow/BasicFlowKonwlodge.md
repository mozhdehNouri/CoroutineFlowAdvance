### What is the flow ? asynchronous data stream

Flow is a new Kotlin asynchronous stream library that is built on top of Kotlin coroutines.

An asynchronous data stream that sequentially emits values and completes normally or with an
exception.

A flow can emit multiple values instead of a single value and over a period of time.

The `Flow` interface does not carry information whether a flow is a _cold_ stream that can be
collected repeatedly and triggers execution of the same code every time it is collected

Kotlin Flow is ideal to use when you need to return multiple values asynchronously, such as
automatic updates from your data source.

Flows are ideal for the parts of your application that involve live data updates.

Or we can say Flow is a stream of Values that are computed asynchronously.

Data Stream alaways return multiple value not single value.

Maybe you ask why we can
notuse [Sequence ]([Sequences | Kotlin Documentation](https://kotlinlang.org/docs/sequences.html))
for produce async value there is an reason Sequence like collections but the collection return
multiple value at the same time. Sequence return when it produce like flow but sequence is
synchronously data stream it means it blocks main thread untile the produce velues were finished.
So it is not safe and we do not want to block the main thread.

You might wonder why we opt not to
use [Sequence ]([Sequences | Kotlin Documentation](https://kotlinlang.org/docs/sequences.html)) to
generate asynchronous values. a Sequence, returns multiple values simultaneously. In contrast, a
Sequence behaves synchronously as a data stream, implying it obstructs the main thread until the
production of values is complete. This synchronous nature makes it unsafe, as we strive to avoid
blocking the main thread.
So what is solution ? Flow 
