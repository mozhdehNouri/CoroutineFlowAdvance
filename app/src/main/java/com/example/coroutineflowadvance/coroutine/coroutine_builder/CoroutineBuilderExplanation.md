coroutine builders are functions that you can use to create a coroutine. for create a coroutine you
can use the following coroutine builders
**launch , async , runBlocking**

or we can say coroutine builder is a function that takes some suspending lambda as an argument,
create a coroutine and optinoally give acces to its result in some form.

actully suspending funcation cannot be invoked from regular function, so the standard library
provides function to start coroutine exexution from a regular non-suspending scope.

async and launch are extention function are Coroune scope it means you can call it in a coroutine
scope
but runBlocking not, it block the current thread until excutation finish. runBlocking is a not
recommand approch for start coroutine you can use theme in unit test.

**Lunch**: Fire and forget

the launch keyword create a coroutine and doesn't return a value. instead return a Job

here is the implementation of lunch function in under the hood.

```kt
public fun CoroutineScope.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> Unit
): Job
```

This function has three parameters. We'll explain the "context" and "block" parameters in connection
with their related concepts. However, the "start" parameter is more interesting. It comes from the
CoroutineStart Enum class, which is a public enumeration.

This Enum class defines start options for coroutine builders and is used in the "start" parameter of
functions like `BuildersKt.launch` and `DeferredKt.async`. Here's a summary of the coroutine start
options:

- **DEFAULT:** Immediately schedules the coroutine for execution based on its context.

- **LAZY:** Starts the coroutine lazily, only when it is needed.

- **ATOMIC:** Atomically (in a non-cancellable way) schedules the coroutine for execution based on
  its context.

- **UNDISPATCHED:** Immediately executes the coroutine until its first suspension point, in the
  current thread.

but how launch work in the background?

```kt
fun launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit) =
    block.startCoroutine(Continuation(context) { result ->
        result.onFailure { exception ->
            val currentThread = Thread.currentThread()
            currentThread.uncaughtExceptionHandler.uncaughtException(currentThread, exception)
        }
    })
```

- `block.startCoroutine(...)` initiates the coroutine execution.

- The `Continuation` object is created, which acts as a callback for coroutine completion.

- If an exception occurs during the coroutine (`result.onFailure`), it retrieves the current thread
  and uses its uncaught exception handler to handle the exception.

you can see this code
from [here](https://github.com/Kotlin/coroutines-examples/blob/master/examples/run/launch.kt)
for more information you can
see [here](https://github.com/Kotlin/KEEP/blob/master/proposals/coroutines.md#coroutine-builders)

#### Async :

it is simlar to launch but it return a value, a Deferred object and you can get the value with the
await function. await is a suspend function.

async builder should be used when you want to execute a task and want to get the output. or
sometimes you need to send 2 requestion parallel and when get result both of them start to sending
request with 2 last result.

```kt
public fun <T> CoroutineScope.async(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: suspend CoroutineScope.() -> T
): Deferred<T>
```

strucutre
of [await ](https://github.com/Kotlin/coroutines-examples/blob/master/examples/future/await.kt)
function:

```kt
suspend fun <T> CompletableFuture<T>.await(): T =
    suspendCoroutine<T> { cont: Continuation<T> ->
        whenComplete { result, exception ->
            if (exception == null) // the future has been completed normally
                cont.resume(result)
            else // the future has completed with an exception
                cont.resumeWithException(exception)
        }
    }
```

for konw more about the suspend sturcure i covered in another section.
some mistake we do when we use async builder :

for instance:

```kt
coroutineScope {

launch{
// suspending opertation   
}  

launch{
    // suspending opertation 
}    
}
```

above code snipped the two launch function start to excute parallel
but now look at the async function

```kt
coroutineScope {

    async {
        execute1()
    }.await()

    async {
        execute2()
    }.await()

}
```

last code not perform parallel first async finish andter that second async start to perform

for fix this issue you have to use like this

```kt
```kt
coroutineScope {

    val a = async {
        execute1()
    }

    val b = async {
        execute2()
    }

    a.await()
    b.await()

}
```

above code works fine :)

RunBlocking:

runBlocking starts a new coroutine and blocks the current thread until the task has been executed.

```kt
import java.util.concurrent.locks.*
import kotlin.coroutines.* 

fun <T> runBlocking(context: CoroutineContext, block: suspend () -> T): T =
 BlockingCoroutine<T>(context).also { block.startCoroutine(it) }.getValue() 
private class BlockingCoroutine<T>(override val context: CoroutineContext) : Continuation<T> {
 private val lock = ReentrantLock()
 private val done = lock.newCondition()
 private var result: Result<T>? = null 
private inline fun <T> locked(block: () -> T): T {
 lock.lock()
 return try {
 block()
 } finally {
 lock.unlock()
 }
 } 
private inline fun loop(block: () -> Unit): Nothing {
 while (true) {
 block()
 }
 } 
override fun resumeWith(result: Result<T>) = locked {
 this.result = result
 done.signal()
 } 
fun getValue(): T = locked<T> {
 loop {
 val result = this.result
 if (result == null) {
 done.awaitUninterruptibly()
 } else {
 return@locked result.getOrThrow()
 }
 }
 }
}
```