#### Buffer operator

Let's start with an example:

Imagine you are at a fast-food restaurant, where there’s a person preparing food (producer) and a
person at the counter serving it to customers (consumer). If the person preparing the food is
working faster than the server can manage, you could end up with food piling up — you need a counter
or space where you keep the food until it can be served. That space is essentially a “buffer.”

In Kotlin Flows, buffering serves a similar purpose. When you’re dealing with a flow of data, items
can be produced at a different rate than they are consumed. When the producer is faster, you can run
into backpressure, where there’s too much data for the consumer to handle at once.

When a flow has a buffer, it can hold onto a certain number of values that have been emitted but not
yet collected. The producer can keep producing upto the limit of the buffer, even if the consumer is
slower at collecting.

Without buffering, the producer would have to wait for the consumer to be ready for each and every
item (this is called backpressure). With a buffer in place, the producer can work ahead, which can
lead to a more efficient use of resources, as you’re not idly waiting.

**Asynchronous**: Buffering allows your program to do more work concurrently. While the consumer is
busy with previous items, the producer can prepare and place additional items in the buffer.

**Limited Size**: Buffers are usually of a limited size to prevent memory issues. If a buffer fills
up, it will apply backpressure to the producer, signaling that it needs to slow down.

**Flow Extension Function**: In Kotlin Flows, you have`.buffer()`which you can append to a flow to
add buffering behavior. You can specify the size of the buffer or use a default.

Normally, flows are*sequential*. It means that the code of all operators is executed in the same
coroutine

So if the operator's code takes considerable time to execute, then the total execution time is going
to be the sum of execution times for all operators.

The`buffer`operator creates a separate coroutine during execution for the flow it applies to.

It will use two coroutines for execution of the code. A coroutine`Q`that calls this code is going to
execute`collect`, and the code before`buffer`will be executed in a separate new coroutine`P`
concurrently with`Q`.

```kt
flowOf("A", "B", "C")
    .onEach  { println("1$it") }
    .buffer()  // <--------------- buffer between onEach and collect
    .collect { println("2$it") }
```

buffer operator has 2 input vale

1 - capacity : it has default value -> BUFFERED

2- onBufferOverflow : it has default value ->  BufferOverflow.SUSPEND

Operator fusion is like having a set of small baskets to carry apples. If you line up all these
baskets in a row and connect them, they act just like one big basket. This is what happens with some
operations in Kotlin Flow. When you use`flowOn`,`buffer`, and similar operators right after one
another on a flow, Kotlin is smart enough to combine them seamlessly into one operation, using just
one “basket” or channel beneath it all. This makes sure they work together efficiently, without
creating unnecessary extra baskets.

### Buffering Details Explained:

1. **Specified Buffer Capacity**: It’s like you’re giving instructions on how many apples each
   basket should hold. If you say a basket should hold 10 apples, then the basket will be made to
   hold exactly 10 apples, even if typically a basket holds more or less by default.

2. **Cumulative Buffer Sizes**: If you put one basket that holds 10 apples next to another basket
   that holds 5 apples, you now effectively have space for 15 apples. In Kotlin Flow, if you set up
   multiple buffers one after the other with specific sizes, you end up with a total buffer size
   that’s the sum of the two.

3. **Non-Default onBufferOverflow**: This is what happens if too many apples come in and your basket
   is full. The`onBufferOverflow`parameter is like saying, “if the basket gets full, here’s the
   plan.” If you don’t use the default behavior (which might be to just stop accepting more apples),
   but instead you choose a special plan, like “let some apples fall to the ground,” that special
   plan takes priority over everything else. In terms of buffers, if you set`onBufferOverflow`to
   something other than the default, it tells the flow how to behave when the buffer is full,
   ignoring previous buffer settings, because this new setting changes the flow’s behavior when
   there’s too much data coming in.

**Buffer Overflow:**

Imagine you have a toy box (the “buffer”) where you’re putting in your toy cars (the “emitted
values”). The box can only hold a certain number of cars (the “buffer capacity”). Now, you’re
putting the cars into the box much faster than you’re taking them out to play with.

1. **Default Behavior (Emitter Suspended)**: Normally, if the box gets full (buffer overflow), you
   just stop putting in more cars (the emitter is suspended). This way, you give yourself time to
   take some cars out (let the collector catch up) before adding more.

2. **Custom Behavior (onBufferOverflow)**: However, you can decide on a different plan for when the
   box gets full, instead of just stopping:

    - **DROP_OLDEST Strategy**: You tell yourself, if the toy box fills up, I’ll take out the oldest
      car (the first one you put in) to make space for the new one I have in my hand (latest emitted
      value added to the buffer). This way, the box never gets too full, but you might lose track of
      playing with some of the oldest cars.

    - **DROP_LATEST Strategy**: Or you might decide, if the box gets full, I’ll just let go of the
      new car in my hand (the latest value being emitted is dropped) so that I don’t have to bother
      rearranging the box. The cars already in the box (the buffer) stay put, and I skip adding the
      new one.

3. **Buffer Size**:

    - Regardless of which custom strategy you pick, your toy box must be able to hold at least one
      car (buffer of at least one element). This means that some sort of buffer is always necessary
      to decide which car to keep or remove when a new one comes along.

Now, translating that back to Kotlin Flow terms:

- **“Emitter”**: This is the part of your code that’s sending out data.
- **“Suspended”**: It temporarily stops sending data to the buffer.
- **“Collector”**: This is the part of your code that’s receiving and processing the data.
- **“Buffer”**: A temporary storage for data in between the emitter and collector.
- **“onBufferOverflow”**: A setting you can change to decide what happens when the buffer gets too
  full.

##### second definition :

buffer() operator is concurrent flows. flow works sequentially but when we apply buffer() operator
emit() function and collect() function work on independent coroutine and use channel to communicate
each other.

note: we use channel for communicate coroutine to gether.

for instance:

```kt
fun main() = coroutineScope {
    val flowOne = flow {
        repeat(5) {
            println("start emitting")
            delay(100)
            println("data is ready")
            emit(it)
        }
    }

    flow.collect {
        println("start to collect $it")
        delay(300)
        println("end collect $it")
    }
}


```

without using buffer this operation works sequentiallybut if we apply .buffer() operator emit works
independtly and start to emit item to collector .
after 300 collector get second item immidatly and don't stop 100 for emit item.

```kt
```kt
fun main() = coroutineScope {
    val flowOne = flow {
        repeat(5) {
            println("start emitting")
            delay(100)
            println("data is ready")
            emit(it)
        }
    }

    flow.buffer().collect {
        println("start to collect $it")
        delay(300)
        println("end collect $it")
    }
}
```

```

never forget buffer works concurrency and emit function perfom another coroutine and collect perform another coroutine and finally they comminucate each other with channel internally.

buffer use channel internally for comminucate emit function and collect function.because when we use buffer() wmit function and collect function run diffrent  coroutine.

the **buffer()** operator has 2 parameter and they have defult value but it is improtant to know about each one.

```kt
buffer(capacity: Int = BUFFERED, onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND)
```

Firs paramater is capacity : consider capacity like a palte which can keep a particular apple or
cooke.

the default paramater is BUFFERED its mean we can save 64 item untils collector get free from
perviouse item .

now what happend if our buffer get full ? now second paramter comes.

the default value for onBufferOverflow is SUSPEND it means when buffer get full emit function get
suspended until get free.

we have diffrent paramter for onBufferOverflow like

DROP_OLDEST : in this situation when buffer get full it drops oldest value and put new item in
buffer.

DROP_LATEST: in this situation when buffer get full it drops new value which emitted.
