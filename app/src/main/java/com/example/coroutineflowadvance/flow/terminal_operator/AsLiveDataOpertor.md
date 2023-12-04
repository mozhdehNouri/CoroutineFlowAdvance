##### asLiveData() is a terimanl operator:

look at this example :

```kt
flowOne.onStart {

}.onCompletion {
    emit(emptyList)
}.onEach {
// do something
}.launchIn(viewModelScope)
```

or

```kt
vieModelScope.launch {
    flowOne.onStart {}.onCompletion {}.collect {
// do something   
    }
}
```

now above code has big problem .

the problem is when our app go to the background the flow still updating data.
this is a big problem because this is use unnecsessory resources.

we have 2 senario for manage this :

one :

use job

// write code about it put it into a function start and cancell and after that we should override
onStart() and onStop() function and start and cancell the job. do not forget to use collect in
viewmodel.

the problem when we use job is onCompletion is calle when app puting in background and flow get
cancell or if we change the theme to dark or light or orintation change. we should avoide from
restarting flow we have to continuse run tin situntion like orintation change or theme not putting
app in background.

// check if the flow get cancell after back from background start from first or continuse and check
it with launhcIn and Collect.

// i think it get restart but check it

second solution is using asLiveData().

```kt
flowOne.map {}.onStart {}.asLiveData()
```

no longer we use collect or launchIn() because this flow become a livedata.
So if you call onCompletion block code and the your app puting in background you can see
onComplition call .

asLiveData() defult use supervisor and Dispatcher main to its context.
asLiveData use onActive() and onInactive() to start and cancell automaticlly and when the appliction
go the packground blockRunner call cancell function.
actully scope create internally. but we can change the context .for example we can change the
dispatcher.

timeoutInMs is second paramater asLiveData() function. this paramter is very important when
configuration change or theme . because of do not cancell immediatly and it is good for
configuration change and theme.

// .asLivedata() need observe or not and change the time out use put app in background and change
the theme and see when onCompeltion is called.
