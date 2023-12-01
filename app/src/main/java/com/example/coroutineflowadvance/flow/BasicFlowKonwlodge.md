### What is the flow ? asynchronous data stream

Flow is a new Kotlin asynchronous stream library that is built on top of Kotlin coroutines.

A flow can emit multiple values instead of a single value and over a period of time.

Kotlin Flow is ideal to use when you need to return multiple values asynchronously, such as
automatic updates from your data source.

Flows are ideal for the parts of your application that involve live data updates.

Or we can say
Flow is a stream of Values that are computed asynchronously.

Data Stream alaways return multiple value not single value.

Maybe you ask why we can not
use [Sequence ]([Sequences | Kotlin Documentation](https://kotlinlang.org/docs/sequences.html))for
produce async value

there is an reson Sequence like collections but the collection return multiple value at the same
time.

Sequence return when it produce. sequence is synchronously data stream it means it blocks main
thread untile the produce velues were finished.
so it is not safe and we do not want to block the main thread.
