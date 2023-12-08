StateFlow and ShareFlow

Actully StateFlow and ShareFlow use instead of LiveData.

Advantages: Exposing Flows instead of LiveData in ViewModels.

- A single type of observable data holder throughout your architecture

- ViewModels are decoupled from Android Dependencies

- Simplified testing.

- More flow operators.

Why we need Use SharedFlow and StateFlow ? why we can not exposing regular flow?

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

when application we to background the collecing not stoped
