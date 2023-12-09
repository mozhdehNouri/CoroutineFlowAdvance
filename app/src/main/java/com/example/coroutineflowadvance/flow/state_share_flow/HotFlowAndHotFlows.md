StateFlow and ShareFlow

Actully StateFlow and ShareFlow use instead of LiveData.

Advantages: Exposing Flows instead of LiveData in ViewModels.

- A single type of observable data holder throughout your architecture

- ViewModels are decoupled from Android Dependencies

- Simplified testing.

- More flow operators.

Why we need Use SharedFlow and StateFlow ? why we can not exposing regular
flow?

1 - Flow producer continues to run when the app is in background.

For instance:

```kt
// ViewModel class
val currentStockFlow: Flow<Stock>
```

```kt
// Activity class
lifeCycleScope.launch {
  viewModel.currentStockFlow.collect {
// do something on ui thread
  }
}
```

when application to background the collecing not stope and keep emiting
items.

We can handle this problem with start and stop Job in onStop and onStart
override function.

```kt
var job: Job? = null

override onStart (){
  job = lifeCycleScope.launch {
    viewModel.currentStockFlow.collect {
    }
  }
}
override onStop (){
  job.cancel()
}
```

In above code, we need a lot of boilerplate.

repeatOnLifecycle() helps us to avoid boilerplate code, and this comes
with androidx-lifecycle-runtime-ktx library.

```kt
// activity class
override onCreate (){
  lifecycleScope.launch {
    repeatOnLifecycle(Lifecycle.State.STARTED) {
      ViewModel.currentStockFlow.collect {

      }
    }
  }
}
```

In fragment we can use like this:

```kt
viewLifecycleOwner.lifecycleScope.launch {
  viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
    viewmodel.currentStockFlow.collect {

    }
  }
}
```

or we can use like this:

```kt
lifecycleScope.launch {
  viewModel.currentStockFlow.flowWithLifecycle(
    lifecycle,
    Lifecycle.State.STARETED
  ).collect {

  }
}
```

With above code we can fix the first and second flow issue.

2 - Activity receives emissions and renders UI when it is in the
background.

3 - Multiple collectors create multiple flows.

For instance:

```kt
// activity class 
lifeCycleScope.launch {
  viewModel.currentStockFlow.collect {
  }
}
lifeCycleScope.launch {
  viewModel.currentStockFlow.collect {

  }
}
```

above code create multiple flows.

4 - Configuration change re-starts the flow

When configuration change happen onCompletion call and flow get cancel and
restart.

Issue number 3 and 4 we can not fix it.

How we can fix this :

the flows create with flow builder are cold flow by default and for fix
for issue number 3 and 4 for we need to convert cold Flow to holtFlow.

What is different between ColdFlow and HotFlow:

ColdFlow:

- become active on collection : It means only if somebody starts to
  collect then flow starts to emit values.

- become inactive on cancellation of the collecting coroutine.

  For instance:

  ```kt
  val flowOne =flow{
      println("emit 1")
      emit(1)
      delay(1000)
      println("emit 2")
      emit(2)
      delay(1000)
  
    println("emit 3") 
    emit(3)
  }
  
  val job  = coroutineScope.launch{
   flowOne.onCompletion{
  
      println("onCompletion is call")
  
  }   .collect{
  
      println("item collect $it")
  
  }
  
  }
  delay(1500)
  job.joinAndCancel()
  ```

  Output is:

```kt
emit 1
item collect 1
emit 2
item collect 2
onCompletion is call
```

- emit individual emissions to every collector:

```kt
suspend fun flow() = coroutineScope {
  val flowOne = flow {
    println("emit 1")
    emit(1)
    kotlinx.coroutines.delay(1000)

    println("emit 2")
    emit(2)
    kotlinx.coroutines.delay(1000)

    println("emit 3")
    emit(3)
    kotlinx.coroutines.delay(100)
  }

  launch {
    flowOne.collect {
      println("startItem emit $it collect 1")
    }
  }

  launch {
    flowOne.collect {
      println("startItem emit $it collect 2")
    }
  }

}
```

Output is :

```kt
emit 1
emit 1
startItem emit 1 collect 2
startItem emit 1 collect 1
emit 2
startItem emit 2 collect 2
emit 2
startItem emit 2 collect 1
emit 3
startItem emit 3 collect 2
emit 3
startItem emit 3 collect 1
```

code in the flow builder run for every collector.
every collector gets its own stream of values and each collector is
independ of another collector.

HotFlows:

Unlike ColdFlows , HotFlows start emiting value without calling any
collector. hot flows can get lost if no current active collector.

in hot flows we can lost data.

- Are active regardless of whether there are collectors.

- Stay active even when there is no more collector.

- Emissions are shared between all collectors.

**Two important reasons we use hotFlows is disability coldFlow or regular
flow to do this two things.**

**1 - Coniguration changes in cold flow get canceled during configuration
change and restart**

**2 - Multiple collectors create multiple flows.** or we can say
independent of each other.
