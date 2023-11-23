## suspend function

suspen function is a function that is marked with`suspend`modifier. It may*suspend*execution of the
code without blocking the current thread of execution by invoking other suspending functions. A
suspending function cannot be invoked from a regular code, but only from other suspending functions
and from suspending lambdas

you can imagin suspend function like a computre game, when you play a game after a while maybe you
need to do some work and you pause your game after a while when you coma back to your game you
wouldn't start it from beging you continue it from pause point and suspend function do same for you.

these are specifal function in kotlin that can pause and resume their execution.

Suspending functions may invoke any regular functions, but to actually suspend execution they must
invoke some other suspending function.

for instance :

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

actully pause(suspend) a coroutine, a suspend function must call another suspend function. this is
like saying "hi i am taking a break , and
I will resume when you tell me"

there is a special suspend function called **suspendCoroutine** it captures the current state of the
coroutine.

when suspendCoroutine is called inside a coroutine, it takes a snapshot of the coroutine state .

actully when a suspend pause some paramter called Continuation keep your execution state and when
your function resume pass the last state and execution can continue.
suspend function dosen't block the thread just makes it suspend and you thread when a function got
suspend thread can be free and do another task and when it resume it can take another thread and
conutine its work.

we just need add suspend keywork to function to make it suspend.

```kt
suspend fun computeName()
```
