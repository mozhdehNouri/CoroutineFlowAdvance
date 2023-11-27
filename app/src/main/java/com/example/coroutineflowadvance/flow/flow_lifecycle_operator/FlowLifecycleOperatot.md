## Flow lifeCycle operator

we have 2 extention function on Flow operaor

##### onStart

##### onCompletion

when your flow starts to emit you can use onStart function to perform an
action
like show a progress bar

Now we have another function called onCompletion we use this function when
we want to konw when our flow emitting value is finish or completed  
it can completed with data or an exception or even cancell

```kt
.onCompletion {exception ->
        println("flow has completed")
    }
```

when our exception is null it means our flow got completed without
exception



  




