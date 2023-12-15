FlowOn intermidate operator:

## Executing in a different CoroutineContext

By default, the producer of a`flow`builder executes in the`CoroutineContext`of the coroutine that
collects from it, and as previously mentioned, it cannot`emit`values from a
different`CoroutineContext`. This behavior might be undesirable in some cases. For instance, in the
examples used throughout this topic, the repository layer shouldn't be performing operations
on`Dispatchers.Main`that is used by`viewModelScope`.

To change the`CoroutineContext`of a flow, use the intermediate
operator[`flowOn`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/flow-on.html).`flowOn`
changes the`CoroutineContext`of the*upstream flow*, meaning the producer and any intermediate
operators applied*before (or above)*`flowOn`. The*downstream flow*(the intermediate operators
*after*`flowOn`along with the consumer) is not affected and executes on the`CoroutineContext`used
to`collect`from the flow. If there are multiple`flowOn`operators, each one changes the upstream from
its current location.

```kt
class NewsRepository(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val userData: UserData,
    private val defaultDispatcher: CoroutineDispatcher
) {
    val favoriteLatestNews: Flow<List<ArticleHeadline>> =
        newsRemoteDataSource.latestNews
            .map { news -> // Executes on the default dispatcher
                news.filter { userData.isFavoriteTopic(it) }
            }
            .onEach { news -> // Executes on the default dispatcher
                saveInCache(news)
            }
            // flowOn affects the upstream flow ↑
            .flowOn(defaultDispatcher)
            // the downstream flow ↓ is not affected
            .catch { exception -> // Executes in the consumer's context
                emit(lastCachedNews())
            }
}
```

With this code, the`onEach`and`map`operators use the`defaultDispatcher`, whereas the`catch`operator
and the consumer are executed on`Dispatchers.Main`used by`viewModelScope`.

As the data source layer is doing I/O work, you should use a dispatcher that is optimized for I/O
operations:

```kt
class NewsRemoteDataSource(
    ...,
    private val ioDispatcher: CoroutineDispatcher
) {
    val latestNews: Flow<List<ArticleHeadline>> = flow {
        // Executes on the IO dispatcher
        ...
    }
        .flowOn(ioDispatcher)
}
```






