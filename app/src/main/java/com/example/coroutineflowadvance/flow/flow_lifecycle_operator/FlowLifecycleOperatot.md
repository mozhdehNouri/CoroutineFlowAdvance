## Flow lifecycle operator

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
.onCompletion { exception ->
    println("flow has completed")
}
```

when our exception is null it means our flow got completed without
exception.

in Flow we have kind of pipleLine each operator excuted one after another for example

```kt
flowOne.onStart { 
        emit()
    }.onCompletion { 
emit(emptyList())
    }.onEach { 

    }.launchIn(viewModelScope)
```

the first of all onStart start to call and after that onCompletio and after that onEach and the end
launchIn

in the onStart{} we can emit another value

check onStart at the end of pipleline and make 2 or 3 of onStart operator in code.

check about flow processing pipeline

if out flow never complete or cancell oncompletion never called

what is upStream and downStream in kotlin flow :

upStream and downStream is important concept in flow

for example look at this code:

```kt
flowOne.onStart { 

    }

    .onCompletion { 

    }

    .onEach { 

    }

    .launchIn(viewModelScope)
```

consider onCompletion block. every operator execute before onCompletion is upstream and every
operator after onCompletion is downStream
it can happend for every operator.

now consider this example :

```kt
flowOne.onStart{

}.onCompletion{
    emit(emptyList)
}.onEach{

}.filter{

}.map {

}
```

most operator in flow effact on downStream oprator.

for example emit(emptyList) effact on onEach block and onEach block effect on filter block.


