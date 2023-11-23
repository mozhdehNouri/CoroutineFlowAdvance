#### Coroutines follow the principle of structured concurrency

**Concurrency** is like having many toys and playing with them one by one, but quickly switching
between them. You play a bit with toy A, then switch to toy B, then to C, and so on. It seems like
you're playing with all the toys at the same time, but you're really just giving a little bit of
attention to each one in turns.

**Parallelism**, on the other hand, is like having a bunch of friends who each have their own set of
toys. Everyone is playing with their toys at the same time, and you all get the job done faster
because you're doing things together.

when we talk about concurrency, we mean doing different tasks but switching between them quickly.
And when we talk about parallelism, we mean doing different tasks at the exact same time, like
having different friends play with their toys simultaneously. **we can say Concurrency is
multitasking on a single-core machine Parallelism is multitasking on a multicore processor.**

**A suspension point**is a point during coroutine execution where the execution of the coroutine*may
be suspended*.

A**continuation** is a state of the suspended coroutine at suspension point. It conceptually
represents the rest of its execution after the suspension point.

#### structured and unstructured in coroutine kotlin

**Unstructured Concurrency:**

Imagine you have a bunch of chores to do. With unstructured concurrency, it's like doing each chore
randomly whenever you feel like it. You might start washing dishes, then halfway through switch to
vacuuming, and then suddenly go back to dishes. It's a bit chaotic, and it's not clear when
everything will be finished.

**Structured Concurrency:**

Now, picture having a to-do list. With structured concurrency, you go through your list one task at
a time. You start with washing dishes, finish that, then move on to vacuuming, and so on. It's
organized, and you know exactly when all your chores will be done.

In Kotlin coroutines, unstructured concurrency is like doing things randomly, and structured
concurrency is like following a to-do list, doing one thing at a time in an organized way. It helps
keep things neat and predictable!

**Unstructured Concurrency:**

```kt
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    // Unstructured concurrency
    GlobalScope.launch {
        washDishes()
    }

    GlobalScope.launch {
        // Randomly switch to vacuuming in the middle
        delay(500)
        vacuum()
    }

    // The main function finishes before the coroutines complete
    println("Main function is done, coroutines are still running...")
    // In a real program, you might want to delay or wait for coroutines to finish.
    Thread.sleep(2000)
}

suspend fun washDishes() {
    delay(1000)
    println("Dishes are washed")
}

suspend fun vacuum() {
    println("Vacuuming is done")
}

```

In this example, `washDishes` and `vacuum` are two tasks launched in the global scope, meaning they
are running independently. The main function finishes before the coroutines complete, resulting in a
bit of chaos.

```kt
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope

fun main() = runBlocking {
    // Structured concurrency using coroutineScope
    coroutineScope {
        launch {
            washDishes()
        }

        launch {
            // Structured: Finish washing dishes before starting to vacuum
            washDishes()
            vacuum()
        }
    }

    // The main function waits for all coroutines to complete
    println("Main function is done, all coroutines completed.")
}

suspend fun washDishes() {
    delay(1000)
    println("Dishes are washed")
}

suspend fun vacuum() {
    println("Vacuuming is done")
}

```

In the structured concurrency example, we use `coroutineScope` to create a scope for our coroutines.
The second coroutine starts only after the first one (washing dishes) is completed. This ensures a
more organized and predictable flow of tasks. The `runBlocking` in the `main` function is used to
wait for all coroutines to complete before moving on.
